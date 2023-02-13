package work.xeltica.craft.core.modules.onegiKitchen

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import work.xeltica.craft.core.modules.onegiKitchen.OnegiKitchenModule.isOnegi

class OnegiKitchenHandler : Listener {
    @EventHandler
    fun onUseFurnace(e: PlayerInteractEvent) {
        if (e.hand != EquipmentSlot.HAND) return
        if (e.action != Action.RIGHT_CLICK_BLOCK) return
        if (!e.player.isOnegi()) return
        val block = e.clickedBlock ?: return
        if (block.type != Material.FURNACE && block.type != Material.BLAST_FURNACE && block.type != Material.SMOKER) return

        block.location.add(0.5, 0.5, 0.5).createExplosion(0f, false, false)
    }
}