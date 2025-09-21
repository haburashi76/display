package io.github.haburashi76.display

import io.github.haburashi76.display.displays.DisplayGroup
import io.github.haburashi76.display.displays.SpecialBlockDisplay
import io.github.haburashi76.display.displays.SpecialItemDisplay
import io.github.haburashi76.display.displays.SpecialTextDisplay
import net.kyori.adventure.text.TextComponent
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Vector

interface DisplayHelper {
    fun newBlockDisplay(material: Material, location: Location, init: (SpecialBlockDisplay) -> Unit): SpecialBlockDisplay
    fun newItemDisplay(material: Material, location: Location, init: (SpecialItemDisplay) -> Unit): SpecialItemDisplay
    fun newTextDisplay(text: TextComponent, location: Location, init: (SpecialTextDisplay) -> Unit): SpecialTextDisplay
    fun grouping(center: Location, displays: Array<out Pair<SpecialDisplay, Vector>>): DisplayGroup
    fun emptyGroup(center: Location): DisplayGroup
}