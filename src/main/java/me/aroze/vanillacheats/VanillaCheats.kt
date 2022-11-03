package me.aroze.vanillacheats

import me.aroze.vanillacheats.cheats.Reach
import org.bukkit.plugin.java.JavaPlugin

class VanillaCheats : JavaPlugin() {
    override fun onEnable() {

        server.pluginManager.registerEvents(Reach, this)

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}