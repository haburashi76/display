package io.github.haburashi76.display.displays

import io.github.haburashi76.display.SpecialDisplay
import org.bukkit.entity.TextDisplay

interface SpecialTextDisplay: SpecialDisplay {
    override val entity: TextDisplay
}