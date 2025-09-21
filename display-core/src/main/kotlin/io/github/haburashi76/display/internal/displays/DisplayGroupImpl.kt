package io.github.haburashi76.display.internal.displays

import io.github.haburashi76.display.SpecialDisplay
import io.github.haburashi76.display.displays.DisplayGroup
import org.apache.commons.lang3.tuple.MutableTriple
import org.bukkit.Location
import org.bukkit.util.Transformation
import org.bukkit.util.Vector
import org.joml.Quaternionf
import org.joml.Vector3f
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class DisplayGroupImpl(
    override val center: Location,
    displays: Array<out Pair<SpecialDisplay, Vector>>): DisplayGroup {

    override val list: MutableList<Pair<SpecialDisplay, Vector>> =
        mutableListOf<Pair<SpecialDisplay, Vector>>().apply {
            displays.forEach { display ->
                this.add(
                    display.first to display.second
                )
            }
        }

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


    internal fun positionSetting() {
        list.forEach { pair ->
            pair.first.entity.teleport(center.clone().add(pair.second).setDirection(
                pair.first.entity.location.direction
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
            val newVector = pair.second.rotate(pitch, yaw)
            pair.second.setX(newVector.x)
            pair.second.setY(newVector.y)
            pair.second.setZ(newVector.z)

            pair.first.entity.teleport(pair.first.entity.location.apply {
                setPitch((getPitch() + pitch).toFloat())
                setYaw((getYaw() + yaw).toFloat())
            })
        }
        positionSetting()
    }
}