package io.github.haburashi76.display.displays

import io.github.haburashi76.display.SpecialDisplay
import org.bukkit.Location
import org.bukkit.util.Vector

interface DisplayGroup {
    val list: Map<SpecialDisplay, Vector>
    val center: Location
    val isRemoved: Boolean
    fun attach(display: SpecialDisplay, vec: Vector)
    fun detach(display: SpecialDisplay)
    fun move(x: Double, y: Double, z: Double)
    fun move(vec: Vector)
    fun teleport(location: Location)
    fun teleport(x: Double, y: Double, z: Double)
    fun rotate(pitch: Double, yaw: Double)
    fun setRotation(pitch: Double, yaw: Double)
    fun setVelocity(vec: Vector, stopCondition: (DisplayGroup) -> Boolean)
    fun setVelocity(x: Double, y: Double, z: Double, stopCondition: (DisplayGroup) -> Boolean)
    fun getVelocity(): Vector
    fun getCustomData(): Map<String, Any?>
    fun addCustomData(k: String, v: Any?)
    fun addCustomData(v: Pair<String, Any?>)
    fun remove()
    fun isComplete(): Boolean
}