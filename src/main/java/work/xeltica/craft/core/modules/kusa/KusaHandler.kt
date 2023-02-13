package work.xeltica.craft.core.modules.kusa

import io.papermc.paper.event.player.ChatEvent
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class KusaHandler : Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    fun onPlayerKusa(e: ChatEvent) {
        val message = PlainTextComponentSerializer.plainText().serialize(e.message())
        KusaModule.handleKusa(message, e.player)
    }
}