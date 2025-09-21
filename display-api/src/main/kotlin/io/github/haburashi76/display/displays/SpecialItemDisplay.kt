package io.github.haburashi76.display.displays

import io.github.haburashi76.display.SpecialDisplay
import org.bukkit.entity.ItemDisplay

interface SpecialItemDisplay: SpecialDisplay {
    override val entity: ItemDisplay
}