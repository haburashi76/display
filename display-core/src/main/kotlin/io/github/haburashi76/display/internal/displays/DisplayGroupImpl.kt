package io.github.haburashi76.display.internal.displays

import io.github.haburashi76.display.SpecialDisplay
import io.github.haburashi76.display.displays.DisplayGroup
import org.bukkit.Location
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.PluginClassLoader
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Transformation
import org.bukkit.util.Vector
import org.joml.Quaternionf
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class DisplayGroupImpl(override val center: Location, displays: Array<out Pair<SpecialDisplay, Vector>>): DisplayGroup {

    override val list: MutableMap<SpecialDisplay, Vector> = mutableMapOf()

    override var isRemoved: Boolean = false

    init {
        displays.forEach { display ->
            display.first.setGroup(this@DisplayGroupImpl, display.second)
        }
    }

    override fun attach(display: SpecialDisplay, vec: Vector) {
        list[display] = vec
        positionSetting()
    }

    override fun detach(display: SpecialDisplay) {
        list.remove(display)
    }

    private var pitch = 0.0
    private var yaw = 0.0

    private fun Vector.rotate(pitch: Double, yaw: Double): Vector {
        val r = this.length()

        if (r == 0.0) return Vector(0.0, 0.0, 0.0)

        val currentPitch = Math.toDegrees(asin(this.y / r))
        val currentYaw = Math.toDegrees(atan2(this.z, this.x))

        val newPitch = currentPitch + pitch
        val newYaw = currentYaw + yaw

        val radPitch = Math.toRadians(newPitch)
        val radYaw = Math.toRadians(newYaw)

        val newX = r * cos(radPitch) * cos(radYaw)
        val newY = r * sin(radPitch)
        val newZ = r * cos(radPitch) * sin(radYaw)

        return Vector(newX, newY, newZ)
    }
    private fun Quaternionf.rotate(pitch: Double, yaw: Double): Quaternionf {
        return (this.clone() as Quaternionf)
            .rotateX(Math.toRadians(pitch).toFloat())
            .rotateY(Math.toRadians(yaw).toFloat())
    }


    internal fun positionSetting() {
        list.forEach { pair ->
            pair.key.entity.teleport(center.clone().add(pair.value).setDirection(
                pair.key.entity.location.direction
            ))
        }
    }


    override fun move(x: Double, y: Double, z: Double) {
        center.add(x, y, z)
        positionSetting()
    }

    override fun move(vec: Vector) {
        center.add(vec)
        positionSetting()
    }

    override fun teleport(x: Double, y: Double, z: Double) {
        center.set(x, y, z)
        positionSetting()
    }

    override fun teleport(location: Location) {
        center.set(location.x, location.y, location.z)
        positionSetting()
    }

    override fun rotate(pitch: Double, yaw: Double) {
        list.forEach { pair ->
            val newVector = pair.value.rotate(pitch, yaw)
            pair.value.setX(newVector.x)
            pair.value.setY(newVector.y)
            pair.value.setZ(newVector.z)

            pair.key.initEntity { entity ->
                entity.transformation = Transformation(
                    entity.transformation.translation,
                    entity.transformation.leftRotation.rotate(pitch, yaw),
                    entity.transformation.scale,
                    entity.transformation.rightRotation
                )
            }
        }
        this.pitch += pitch
        this.yaw += yaw

        positionSetting()
    }
    override fun setRotation(pitch: Double, yaw: Double) {
        rotate(-this.pitch, -this.yaw)
        rotate(pitch, yaw)
        this.pitch = pitch
        this.yaw = yaw
    }

    private var stopCondition: ((DisplayGroup) -> Boolean)? = null
    private var velocity: Vector = Vector(0, 0, 0)
    private lateinit var plugin: Plugin

    private val runnable = object : BukkitRunnable() {
        override fun run() {
            move(velocity.multiply(1.0/20.0))
            if (stopCondition?.invoke(this@DisplayGroupImpl) == true) {
                cancel()
            }
        }
    }

    private fun loadingPlugin(v: Any) {
        val loader = v.javaClass.classLoader as? PluginClassLoader?: return
        loader.plugin?: return
        plugin = loader.plugin!!
    }

    override fun setVelocity(vec: Vector, stopCondition: (DisplayGroup) -> Boolean) {
        this.stopCondition = stopCondition
        runnable.cancel()

        loadingPlugin(stopCondition)

        velocity = vec
        runnable.runTaskTimer(plugin, 0L, 1L)
    }

    override fun setVelocity(x: Double, y: Double, z: Double, stopCondition: (DisplayGroup) -> Boolean) {
        this.stopCondition = stopCondition
        runnable.cancel()

        loadingPlugin(stopCondition)

        velocity = Vector(x, y, z)
        runnable.runTaskTimer(plugin, 0L, 1L)
    }

    override fun getVelocity(): Vector = velocity.clone()

    private val customData = mutableMapOf<String, Any?>()

    override fun getCustomData(): Map<String, Any?> = customData

    override fun addCustomData(k: String, v: Any?) {
        customData[k] = v
    }

    override fun addCustomData(v: Pair<String, Any?>) {
        customData[v.first] = v.second
    }

    override fun remove() {
        list.forEach { pair ->
            pair.key.remove()
            list.remove(pair.key)
        }
        isRemoved = true
    }

    override fun isComplete(): Boolean = list.any { !it.key.entity.isValid }
}