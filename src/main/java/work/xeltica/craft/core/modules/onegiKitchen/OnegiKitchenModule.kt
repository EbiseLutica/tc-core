package work.xeltica.craft.core.modules.onegiKitchen

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.FurnaceRecipe
import org.bukkit.inventory.ItemStack
import work.xeltica.craft.core.TCCorePlugin
import work.xeltica.craft.core.api.ModuleBase

object OnegiKitchenModule : ModuleBase() {
    private lateinit var waterMelonRecipeKey: NamespacedKey

    override fun onEnable() {
        waterMelonRecipeKey = NamespacedKey(TCCorePlugin.instance, "melon_to_charcoal")
        Bukkit.addRecipe(FurnaceRecipe(waterMelonRecipeKey, ItemStack(Material.CHARCOAL, 1), Material.MELON, 0.15f, 1))
        registerHandler(OnegiKitchenHandler())
    }

    override fun onDisable() {
        Bukkit.removeRecipe(waterMelonRecipeKey)
    }

    fun Player.isOnegi(): Boolean {
        // TODO: UUIDで判定する
        return name == ".minaseliu1227"
    }
}