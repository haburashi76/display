package io.github.haburashi76.display.internal.displays

import io.github.haburashi76.display.displays.SpecialTextDisplay
import io.github.haburashi76.display.internal.SpecialDisplayImpl
import org.bukkit.entity.TextDisplay

class SpecialTextDisplayImpl(override val entity: TextDisplay): SpecialTextDisplay, SpecialDisplayImpl(entity) {
}