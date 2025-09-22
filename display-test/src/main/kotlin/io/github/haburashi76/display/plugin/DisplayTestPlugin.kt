package io.github.haburashi76.display.plugin


import io.github.haburashi76.display.DisplayManager
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Display
import org.bukkit.entity.Display.Brightness
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class DisplayTestPlugin: JavaPlugin(), Listener {
    private fun Player.shooting() {
        val player = this
        val block = DisplayManager.blockDisplay(Material.DIAMOND_BLOCK, player.eyeLocation.setDirection(Vector(0, 0, 1))) {
            it.entity.teleport(it.entity.location.apply {
                direction = player.location.direction.setY(0)
            })
            it.entity.brightness = Brightness(15, 15)
        }
        val projectile = DisplayManager.grouping(player.location,
            block to Vector(0.0, 0.0, 0.0)
        )
        object : BukkitRunnable() {
            val direction = projectile.center.direction.multiply(1.6)
            var falling = 0.0
            override fun run() {
                if (projectile.center.block.isCollidable ||
                    projectile.center.world.livingEntities.any {
                        it != player &&
                                (it.location.distance(projectile.center) < 1.8
                                || it.eyeLocation.distance(projectile.center) < 1.8)
                    }) {
                    projectile.list.forEach {
                        it.first.entity.remove()
                    }
                    projectile.center.world.livingEntities.forEach { entity ->
                        if (entity != player) {
                            if (entity.location.distance(projectile.center) < 1.8 ||
                                entity.eyeLocation.distance(projectile.center) < 1.8
                            ) {
                                entity.noDamageTicks = 0
                                entity.damage(2.0)
                            }
                        }
                    }
                    cancel()
                }
                projectile.move(direction)
                projectile.move(Vector(0.0, -falling, 0.0))

                falling += 0.025
            }
        }.runTaskTimer(this@DisplayTestPlugin, 0L, 1L)
    }
    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        getCommand("summon-displays")!!.setExecutor { commandSender, command, s, strings ->
            if (commandSender !is Player) return@setExecutor false
            val block = DisplayManager.blockDisplay(Material.DIAMOND_BLOCK, commandSender.location.setDirection(Vector(0, 0, 1))) {
                it.entity.brightness = Brightness(15, 15)
            }
            val item = DisplayManager.itemDisplay(Material.DIAMOND, commandSender.location.setDirection(Vector(0, 0, 1))) {
                it.entity.brightness = Brightness(15, 15)
            }
            val text = DisplayManager.textDisplay(Component.text("테스트"), commandSender.location.setDirection(Vector(0, 0, 1))) {
                it.entity.brightness = Brightness(15, 15)
                it.entity.billboard = Display.Billboard.CENTER
            }
            val group = DisplayManager.grouping(commandSender.location.setDirection(Vector(0, 0, 1)),
                block to Vector(1.0, 1.0, 0.0),
                item to Vector(2.0, 3.6, 0.0),
                text to Vector(2.0, -2.0, 0.0)
            )
            object : BukkitRunnable() {
                override fun run() {
                    group.move(Vector(0.0, 0.05, 0.0))
                    group.rotate(0.0, 6.0)
                }
            }.runTaskTimer(this, 0L, 2L)
            return@setExecutor true
        }
        getCommand("shoot-projectile")!!.setExecutor { commandSender, command, s, strings ->
            if (commandSender !is Player) return@setExecutor false
            commandSender.shooting()
            return@setExecutor true
        }
    }
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.action == Action.RIGHT_CLICK_AIR && event.item?.type == Material.BLAZE_ROD) {
            event.player.shooting()
        }
    }
}