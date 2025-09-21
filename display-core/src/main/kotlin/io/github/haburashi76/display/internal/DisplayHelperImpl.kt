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
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.TextDisplay
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Transformation
import org.bukkit.util.Vector
import org.joml.Vector3f

class DisplayHelperImpl: DisplayHelper {
    override fun newBlockDisplay(
        material: Material,
        location: Location,
        init: (SpecialBlockDisplay) -> Unit): SpecialBlockDisplay {
        val world = location.world
        val spawned = world.spawn(location, BlockDisplay::class.java) { display ->
            display.transformation = Transformation(
                Vector3f(-0.5f, 0.0f, -0.5f),
                display.transformation.leftRotation,
                display.transformation.scale,
                display.transformation.rightRotation
            )
            display.block = material.createBlockData()
        }
        val special = SpecialBlockDisplayImpl(spawned)
        init(special)
        return special
    }

    override fun newItemDisplay(
        material: Material,
        location: Location,
        init: (SpecialItemDisplay) -> Unit
    ): SpecialItemDisplay {
        val world = location.world
        val spawned = world.spawn(location, ItemDisplay::class.java) { display ->
            display.itemStack = ItemStack(material)
        }
        val special = SpecialItemDisplayImpl(spawned)
        init(special)
        return special
    }

    override fun newTextDisplay(
        text: TextComponent,
        location: Location,
        init: (SpecialTextDisplay) -> Unit
    ): SpecialTextDisplay {
        val world = location.world
        val spawned = world.spawn(location, TextDisplay::class.java) { display ->
            display.text(text)
        }
        val special = SpecialTextDisplayImpl(spawned)
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