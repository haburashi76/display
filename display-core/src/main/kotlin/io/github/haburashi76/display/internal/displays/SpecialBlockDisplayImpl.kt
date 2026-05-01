package io.github.haburashi76.display.internal.displays

import io.github.haburashi76.display.displays.SpecialBlockDisplay
import io.github.haburashi76.display.internal.SpecialDisplayImpl
import org.bukkit.entity.BlockDisplay

class SpecialBlockDisplayImpl(override val entity: BlockDisplay): SpecialBlockDisplay, SpecialDisplayImpl(entity) {
}