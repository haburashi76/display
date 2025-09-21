package io.github.haburashi76.display

import io.github.haburashi76.display.displays.DisplayGroup
import io.github.haburashi76.display.displays.SpecialBlockDisplay
import io.github.haburashi76.display.displays.SpecialItemDisplay
import io.github.haburashi76.display.displays.SpecialTextDisplay
import io.github.haburashi76.display.util.ImplLoader
import net.kyori.adventure.text.TextComponent
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Vector

object DisplayManager {
    private val helper = ImplLoader.load(DisplayHelper::class.java)
    fun blockDisplay(material: Material, location: Location, init: (SpecialBlockDisplay) -> Unit): SpecialBlockDisplay {
        return helper.newBlockDisplay(material, location, init)
    }
    fun itemDisplay(material: Material, location: Location, init: (SpecialItemDisplay) -> Unit): SpecialItemDisplay {
        return helper.newItemDisplay(material, location, init)
    }
    fun textDisplay(text: TextComponent, location: Location, init: (SpecialTextDisplay) -> Unit): SpecialTextDisplay {
        return helper.newTextDisplay(text, location, init)
    }
    fun grouping(center: Location, vararg display: Pair<SpecialDisplay, Vector>): DisplayGroup {
        return helper.grouping(center, display)
    }
    fun emptyGroup(center: Location): DisplayGroup {
        return helper.emptyGroup(center)
    }
}
