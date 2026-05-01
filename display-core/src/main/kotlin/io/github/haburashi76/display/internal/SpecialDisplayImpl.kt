package io.github.haburashi76.display.internal

import io.github.haburashi76.display.SpecialDisplay
import io.github.haburashi76.display.displays.DisplayGroup
import org.bukkit.Bukkit
import org.bukkit.entity.Display
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.util.Vector

open class SpecialDisplayImpl(initialEntity: Display): SpecialDisplay {

    private val uuid = initialEntity.uniqueId

    override var isRemoved: Boolean = true

    override val entity: Display
        get() = Bukkit.getEntity(uuid) as Display

    override fun initEntity(init: (Display) -> Unit) {
        init(entity)
    }

    private var group: DisplayGroup? = null

    override fun visibleFrom(): List<Player> = visibleFrom

    private var isVisible = true

    override fun isVisible(): Boolean = isVisible

    override fun setVisible(b: Boolean) {
        isVisible = b
        entity.isVisibleByDefault = isVisible
    }

    private val visibleFrom = mutableListOf<Player>()

    private fun updateVisibleFrom(plugin: Plugin) {
        if (!isVisible) {
            Bukkit.getOnlinePlayers().forEach { player ->
                player.hideEntity(plugin, entity)
            }
            visibleFrom.forEach { player ->
                player.showEntity(plugin, entity)
            }
        }
    }

    override fun addVisibleFrom(vararg p: Player, plugin: Plugin) {
        p.forEach { player ->
            if (!visibleFrom.contains(player)) {
                visibleFrom.add(player)
            }
        }
        updateVisibleFrom(plugin)
    }

    override fun removeVisibleFrom(vararg p: Player, plugin: Plugin) {
        p.forEach { player ->
            if (visibleFrom.contains(player)) {
                visibleFrom.remove(player)
            }
        }
        updateVisibleFrom(plugin)
    }

    override fun getVisibleFrom(): List<Player> = visibleFrom

    override fun group(): DisplayGroup? = group

    override fun setGroup(group: DisplayGroup, v: Vector) {
        this.group?.detach(this)
        this.group = group
        group.attach(this, v)
    }

    override fun remove() {
        entity.remove()
        isRemoved = true
    }

}