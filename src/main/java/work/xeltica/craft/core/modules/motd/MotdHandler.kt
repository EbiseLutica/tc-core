package work.xeltica.craft.core.modules.motd

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class MotdHandler : Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val name = PlainTextComponentSerializer.plainText().serialize(e.player.displayName())

        e.joinMessage(Component.text("${ChatColor.GREEN}${name}${ChatColor.AQUA}さんがやってきました"))
        if (!e.player.hasPlayedBefore()) {
            e.joinMessage(Component.text("${ChatColor.GREEN}${name}${ChatColor.AQUA}さんが${ChatColor.GOLD}${ChatColor.BOLD}初参加${ChatColor.RESET}です"))
        }
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        val name = PlainTextComponentSerializer.plainText().serialize(e.player.displayName())
        e.quitMessage(Component.text("${ChatColor.GREEN}${name}${ChatColor.AQUA}さんがかえりました"))
    }
}