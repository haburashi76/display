package io.github.haburashi76.display.internal.displays

import io.github.haburashi76.display.displays.SpecialItemDisplay
import io.github.haburashi76.display.internal.SpecialDisplayImpl
import org.bukkit.entity.ItemDisplay

class SpecialItemDisplayImpl(override val entity: ItemDisplay): SpecialItemDisplay, SpecialDisplayImpl(entity) {
}