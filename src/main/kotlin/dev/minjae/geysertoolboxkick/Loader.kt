package dev.minjae.geysertoolboxkick

import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.geysermc.floodgate.util.DeviceOs
import org.geysermc.geyser.GeyserImpl

class Loader : JavaPlugin(), Listener {

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (GeyserImpl.getInstance().sessionManager.sessions[player.uniqueId] != null) {
            val session = GeyserImpl.getInstance().sessionManager.sessions[player.uniqueId]!!
            val deviceOS = session.clientData.deviceOs
            if (deviceOS != DeviceOs.GOOGLE) {
                return
            }
            val deviceModel = session.clientData.deviceModel
            deviceModel?.let {
                val name = it.split(" ")
                if (name.isEmpty()) {
                    return
                }
                val model = name[0]
                val check = model.uppercase()
                if (model != check) {
//                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Detected you are using Toolbox. Please turn off Toolbox to join server.")
                    server.scheduler.scheduleSyncDelayedTask(this) {
                        player.kick(Component.text("Detected you are using Toolbox. Please turn off Toolbox to join server."))
                    }
                }
            }
        }
    }
}
