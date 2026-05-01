package io.github.haburashi76.display.internal

import io.github.haburashi76.display.DisplayHelper
import io.github.haburashi76.display.SpecialDisplay
import io.github.haburashi76.display.displays.*
import io.github.haburashi76.display.internal.displays.DisplayGroupImpl
import io.github.haburashi76.display.internal.displays.SpecialBlockDisplayImpl
import io.github.haburashi76.display.internal.displays.SpecialItemDisplayImpl
import io.github.haburashi76.display.internal.displays.SpecialTextDisplayImpl
import net.kyori.adventure.text.TextComponent
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.BlockDisplay
import org.bukkit.entity.EntityType
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.TextDisplay
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Transformation
import org.bukkit.util.Vector
import org.joml.Vector3f

class DisplayHelperImpl: DisplayHelper {
    override fun newBlockDisplay(
        material: Material,
        location: Location,
        init: (SpecialBlockDisplay) -> Unit
    ): SpecialBlockDisplay {
        val world = location.world
        val spawned = world.spawnEntity(location, EntityType.BLOCK_DISPLAY, CreatureSpawnEvent.SpawnReason.CUSTOM) { display ->
            if (display is BlockDisplay) {
                display.transformation = Transformation(
                    Vector3f(-0.5f, 0.0f, -0.5f),
                    display.transformation.leftRotation,
                    display.transformation.scale,
                    display.transformation.rightRotation
                )
                display.block = material.createBlockData()
            }
        }
        val special = SpecialBlockDisplayImpl(spawned as BlockDisplay)
        init(special)
        return special
    }

    override fun newItemDisplay(
        material: Material,
        location: Location,
        init: (SpecialItemDisplay) -> Unit
    ): SpecialItemDisplay {
        val world = location.world
        val spawned = world.spawnEntity(location, EntityType.ITEM_DISPLAY, CreatureSpawnEvent.SpawnReason.CUSTOM) { display ->
            if (display is ItemDisplay) {
                display.itemStack = ItemStack(material)
            }
        }
        val special = SpecialItemDisplayImpl(spawned as ItemDisplay)
        init(special)
        return special
    }

    override fun newTextDisplay(
        text: TextComponent,
        location: Location,
        init: (SpecialTextDisplay) -> Unit
    ): SpecialTextDisplay {
        val world = location.world
        val spawned = world.spawnEntity(location, EntityType.TEXT_DISPLAY, CreatureSpawnEvent.SpawnReason.CUSTOM) { display ->
            if (display is TextDisplay) {
                display.text(text)
            }
        }
        val special = SpecialTextDisplayImpl(spawned as TextDisplay)
        init(special)
        return special
    }

    override fun grouping(center: Location, displays: Array<out Pair<SpecialDisplay, Vector>>): DisplayGroup {
        val group = DisplayGroupImpl(center, displays)
        group.positionSetting()
        return group
    }

    override fun emptyGroup(center: Location): DisplayGroup {
        return DisplayGroupImpl(center, arrayOf())
    }
}