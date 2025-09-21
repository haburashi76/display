package io.github.haburashi76.display.displays

import io.github.haburashi76.display.SpecialDisplay
import org.apache.commons.lang3.tuple.MutableTriple
import org.bukkit.Location
import org.bukkit.util.Vector

interface DisplayGroup {
    val list: List<Pair<SpecialDisplay, Vector>>
    val center: Location
    fun move(x: Double, y: Double, z: Double)
    fun move(vec: Vector)
    fun teleport(location: Location)
    fun teleport(x: Double, y: Double, z: Double)
    fun rotate(pitch: Double, yaw: Double)
}