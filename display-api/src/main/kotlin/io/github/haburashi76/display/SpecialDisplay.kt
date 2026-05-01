package io.github.haburashi76.display

import io.github.haburashi76.display.displays.DisplayGroup
import org.bukkit.entity.Display
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.util.Vector

interface SpecialDisplay {
    val entity: Display
    val isRemoved: Boolean
    fun initEntity(init: (Display) -> Unit)
    fun visibleFrom(): List<Player>
    fun addVisibleFrom(vararg p: Player, plugin: Plugin)
    fun removeVisibleFrom(vararg p: Player, plugin: Plugin)
    fun isVisible(): Boolean
    fun setVisible(b: Boolean)
    fun getVisibleFrom(): List<Player>
    fun group(): DisplayGroup?
    fun setGroup(group: DisplayGroup, v: Vector)
    fun remove()
}