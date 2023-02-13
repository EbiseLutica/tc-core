package work.xeltica.craft.core.modules.cat

import io.papermc.paper.event.player.ChatEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import work.xeltica.craft.core.api.playerStore.PlayerStore

class CatHandler : Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerChatForCat(e: ChatEvent) {
        if (PlayerStore.open(e.player).getBoolean(CatModule.PS_KEY_CAT)) {
            val text = e.message() as TextComponent
            e.message(Component.text(CatModule.nyaize(text.content())))
        }
    }
}