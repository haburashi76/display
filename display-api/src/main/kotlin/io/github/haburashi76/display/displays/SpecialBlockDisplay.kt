package io.github.haburashi76.display.displays

import io.github.haburashi76.display.SpecialDisplay
import org.bukkit.entity.BlockDisplay

interface SpecialBlockDisplay: SpecialDisplay {
    override val entity: BlockDisplay
}