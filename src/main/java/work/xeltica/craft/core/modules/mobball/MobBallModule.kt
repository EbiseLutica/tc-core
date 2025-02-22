package work.xeltica.craft.core.modules.mobball

import de.tr7zw.nbtapi.NBTItem
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Axolotl
import org.bukkit.entity.Mob
import org.bukkit.inventory.ItemStack
import work.xeltica.craft.core.TCCorePlugin
import work.xeltica.craft.core.api.Config
import work.xeltica.craft.core.api.ModuleBase
import java.util.Objects

/**
 * モブボール機能を提供するモジュールです。
 */
object MobBallModule : ModuleBase() {
    const val PS_KEY_DEX = "mob_dex"

    lateinit var tierMap: MutableMap<String, Int>
    lateinit var difficulties: Array<Double>

    override fun onEnable() {
        TCCorePlugin.instance.saveResource("mobCaptureDifficulty.yml", false)
        reload()
        registerCommand("givemobball", CommandGiveMobBall())
        registerHandler(MobBallHandler())
    }

    fun reload() {
        val conf = Config("mobCaptureDifficulty").conf
        difficulties = conf.getDoubleList("difficulty").toTypedArray()
        tierMap = mutableMapOf()

        for (i in 1..5) {
            conf.getStringList("tier$i").forEach {
                tierMap[it] = i
            }
        }
    }

    /**
     * モブボールを [amount] つだけ作成します。
     */
    fun createMobBall(amount: Int): ItemStack {
        val item = ItemStack(Material.EGG, amount)
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1)
        item.editMeta {
            it.displayName(Component.text("モブボール™"))
            val lore = listOf(Component.text("野生のモブに投げて捕まえる為に使うボール。"))
            it.lore(lore)
        }
        return item
    }

    /**
     * [item] がモブボールかどうかを取得します。
     */
    fun isMobBall(item: ItemStack): Boolean {
        return isBall(item, createMobBall(1))
    }

    /**
     * [spawnEgg] がモブケースとして機能するように、メタ情報を付与します。
     */
    fun setMobCaseMeta(spawnEgg: ItemStack) {
        val eggNBT = NBTItem(spawnEgg)
        val caseTitle = eggNBT.getString("mobCase") ?: return
        val isActive = eggNBT.getBoolean("isActive") ?: return

        spawnEgg.editMeta {
            it.displayName(Component.text("${caseTitle}のモブケース ${if (isActive) "" else "（空）"}"))
            it.lore(listOf(Component.text(if (isActive) "右クリック/タップで中のモブを取り出せます" else "対応するモブを右クリック/長押しでしまえます")))
        }
        if (isActive) {
            spawnEgg.addUnsafeEnchantment(Enchantment.DURABILITY, 1)
        } else {
            spawnEgg.removeEnchantment(Enchantment.DURABILITY)
        }
    }

    /**
     * [mob] の捕獲率を算出します。
     */
    fun calculate(mob: Mob): Int {
        var mobType = mob.type.toString().lowercase()
        if (mob is Axolotl && mob.variant == Axolotl.Variant.BLUE) mobType = "_blue_axolotl"
        val hp = mob.health
        val hpMax = mob.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: 20.0
        val tierDifficulty = difficulties[(tierMap[mobType] ?: 5) - 1]
        return (100 * ((1 - hp / (hpMax * 1.6)) * tierDifficulty)).toInt()
    }

    /**
     * [item] が [ball] と等しいかどうかを取得します。
     */
    private fun isBall(item: ItemStack, ball: ItemStack): Boolean {
        if (item.type != Material.EGG) return false
        val lore = item.lore() ?: return false
        val ballLore = ball.lore() ?: return false
        if (!Objects.equals(lore[0], ballLore[0])) return false
        return true
    }
}