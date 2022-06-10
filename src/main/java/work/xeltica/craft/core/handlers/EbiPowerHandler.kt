package work.xeltica.craft.core.handlers

import com.destroystokyo.paper.MaterialTags
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.Tag
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityBreedEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import work.xeltica.craft.core.events.RealTimeNewDayEvent
import work.xeltica.craft.core.models.Hint
import work.xeltica.craft.core.models.PlayerDataKey
import work.xeltica.craft.core.models.PlayerRecord
import work.xeltica.craft.core.stores.EbiPowerStore
import work.xeltica.craft.core.stores.HintStore
import work.xeltica.craft.core.stores.MobEPStore
import work.xeltica.craft.core.stores.PlayerStore
import java.util.*
import java.util.function.Consumer
import kotlin.random.Random

/**
 * エビパワー関連のイベントハンドラをまとめています。
 * @author raink1208
 */
class EbiPowerHandler: Listener {
    companion object {
        private const val HARVEST_POWER_MULTIPLIER = 1
        private const val LOGIN_BONUS_POWER = 50
        const val BREAK_BLOCK_BONUS_LIMIT = 4000
    }

    private val epBlackList = HashSet<String>()
    private val crops = HashSet<Material>()
    private val breakBonusList = HashSet<Material>()

    init {
        // エビパワーが貯まらないワールドのリストを構築
        // TODO: 設定ファイルに移す
        epBlackList.add("hub2")
        epBlackList.add("art")
        epBlackList.add("sandbox2")
        epBlackList.add("pvp")
        epBlackList.add("hub_dev")

        crops.addAll(Tag.CROPS.values)

        breakBonusList.addAll(Tag.BASE_STONE_OVERWORLD.values)
        breakBonusList.addAll(Tag.BASE_STONE_NETHER.values)
        breakBonusList.addAll(Tag.ICE.values)
        breakBonusList.addAll(Tag.DIRT.values)
        breakBonusList.addAll(Tag.SAND.values)

        breakBonusList.addAll(Tag.COAL_ORES.values)
        breakBonusList.addAll(Tag.IRON_ORES.values)
        breakBonusList.addAll(Tag.COPPER_ORES.values)
        breakBonusList.addAll(Tag.GOLD_ORES.values)
        breakBonusList.addAll(Tag.REDSTONE_ORES.values)
        breakBonusList.addAll(Tag.EMERALD_ORES.values)
        breakBonusList.addAll(Tag.LAPIS_ORES.values)
        breakBonusList.addAll(Tag.DIAMOND_ORES.values)
        breakBonusList.addAll(Tag.LOGS.values)
        breakBonusList.addAll(MaterialTags.TERRACOTTA.values)

        breakBonusList.add(Material.NETHER_QUARTZ_ORE)
        breakBonusList.add(Material.OBSIDIAN)
        breakBonusList.add(Material.ANCIENT_DEBRIS)
        breakBonusList.add(Material.SANDSTONE)
        breakBonusList.add(Material.DEEPSLATE)
        breakBonusList.add(Material.KELP_PLANT)
        breakBonusList.add(Material.DRIPSTONE_BLOCK)
        breakBonusList.add(Material.POINTED_DRIPSTONE)
        breakBonusList.add(Material.POINTED_DRIPSTONE)
        breakBonusList.add(Material.GRAVEL)
        breakBonusList.add(Material.CLAY)
        breakBonusList.add(Material.SOUL_SAND)
        breakBonusList.add(Material.SOUL_SOIL)
        breakBonusList.add(Material.END_STONE)
        breakBonusList.add(Material.CHORUS_FLOWER)
        breakBonusList.add(Material.CHORUS_PLANT)
        breakBonusList.add(Material.PRISMARINE)
    }

    @EventHandler(priority = EventPriority.NORMAL)
    fun onPlayerDamageFrailCreatures(e: EntityDamageByEntityEvent) {
        val killer = e.damager as? Player ?: return
        val victim = e.entity as? LivingEntity ?: return

        if (victim.fromMobSpawner()) return

        val epStore = EbiPowerStore.getInstance()
        if (victim is Cat || victim is Ocelot) {
            epStore.tryTake(killer, 100)
            notification(killer, "可愛い可愛いネコちゃんを殴るなんて！100EPを失った。")
            killer.playSound(killer.location, Sound.ENTITY_ZOMBIE_VILLAGER_AMBIENT, 0.7f, 0.5f)
            HintStore.instance.achieve(killer, Hint.VIOLENCE_CAT)
        } else if (victim is Tameable && victim.isTamed && victim !is SkeletonHorse) {
            epStore.tryTake(killer, 10)
            notification(killer, "ペットを殴るなんて！10EPを失った。")
            killer.playSound(killer.location, Sound.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.PLAYERS, 0.7f, 0.5f)
            HintStore.instance.achieve(killer, Hint.VIOLENCE_PET)
        } else if (victim is Ageable && victim !is Monster && victim !is Hoglin && !victim.isAdult) {
            epStore.tryTake(killer, 10)
            notification(killer, "子供を殴るなんて！10EPを失った。")
            killer.playSound(killer.location, Sound.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.PLAYERS, 0.7f, 0.5f)
            HintStore.instance.achieve(killer, Hint.VIOLENCE_CHILD)
        }
    }

    @EventHandler
    fun onPlayerKillMobs(e: EntityDeathEvent) {
        val victim = e.entity
        val killer = e.entity.killer ?: return

        if (playerIsInBlacklisted(killer)) return
        // don't kill cats
        if (victim is Cat || victim is Ocelot) return
        // don't kill tamed pets
        if (victim is Tameable && victim.isTamed && victim !is SkeletonHorse) return
        //don't kill non-monster children
        if (victim is Ageable && victim !is Monster && victim !is Hoglin && !victim.isAdult) return
        // ignore creatures from spawner
        if (victim.fromMobSpawner()) return

        var ep = if ("nightmare2" == killer.world.name) MobEPStore.getInstance().getMobDropEP(victim, e) else 6
        val buff = getMobDropBonus(killer.inventory.itemInMainHand) * 4
        ep += if (buff > 0) Random.nextInt(buff) else 0
        if (ep > 0) {
            EbiPowerStore.getInstance().tryGive(killer, ep)
            HintStore.instance.achieve(killer, Hint.KILL_MOB_AND_EARN_MONEY)
        }
    }

    @EventHandler
    fun onPlayerLoggedIn(e: PlayerJoinEvent) {
        val now = Date()
        val ps = PlayerStore.getInstance()
        val record = ps.open(e.player)
        val prev = Date(record.getLong(PlayerDataKey.LAST_JOINED, now.time))
        if (prev.year != now.year && prev.month != now.month && prev.date != now.date) {
            EbiPowerStore.getInstance().tryGive(e.player, LOGIN_BONUS_POWER)
            notification(e.player, "ログボ達成！" + LOGIN_BONUS_POWER.toString() + "EPを獲得。")
        }
        record[PlayerDataKey.LAST_JOINED] = now.time
    }

    @EventHandler
    fun onHarvestCrops(e: BlockBreakEvent) {
        val p = e.player
        if (playerIsInBlacklisted(p)) return
        val blockData = e.block.blockData
        if (blockData is org.bukkit.block.data.Ageable && blockData.age == blockData.maximumAge) {
            val tool = p.inventory.itemInMainHand
            val bonus = getBlockDropBonus(tool)
            val power = (1 + bonus) * HARVEST_POWER_MULTIPLIER
            EbiPowerStore.getInstance().tryGive(p, power)
            // もし幸運ボーナスがあれば30%の確率で耐久が減っていく
            if (bonus > 0 && Random.nextInt(100) < 30) {
                tool.editMeta { meta ->
                    if (meta is Damageable) {
                        meta.damage += 1
                    }
                }
            }
        }
    }

    @EventHandler
    fun onBreedEntities(e: EntityBreedEvent) {
        val breeder = e.breeder as? Player ?: return
        if (playerIsInBlacklisted(breeder)) return
        EbiPowerStore.getInstance().tryGive(breeder, 2)
        HintStore.instance.achieve(breeder, Hint.BREED_AND_EARN_MONEY)
    }

    @EventHandler
    fun onMineBlocks(e: BlockBreakEvent) {
        if (!breakBonusList.contains(e.block.type)) return
        if (playerIsInBlacklisted(e.player)) return
        val record = PlayerStore.getInstance().open(e.player)
        val brokenBlocksCount = record.getInt(PlayerDataKey.BROKEN_BLOCKS_COUNT)

        if (!e.isDropItems) return

        record.set(PlayerDataKey.BROKEN_BLOCKS_COUNT, brokenBlocksCount + 1)

        if (brokenBlocksCount + 1 == BREAK_BLOCK_BONUS_LIMIT) {
            HintStore.instance.achieve(e.player, Hint.MINERS_DREAM)
        }

        var ep = 1
        val tool = e.player.inventory.itemInMainHand
        val luck = tool.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)
        if (luck) {
            ep += when (e.block.type) {
                Material.COAL_ORE -> 1
                Material.DEEPSLATE_COAL_ORE -> 1
                Material.IRON_ORE -> 1
                Material.DEEPSLATE_IRON_ORE -> 1
                Material.COPPER_ORE -> 1
                Material.DEEPSLATE_COPPER_ORE -> 1
                Material.GOLD_ORE -> 4
                Material.DEEPSLATE_GOLD_ORE -> 4
                Material.REDSTONE_ORE -> 2
                Material.DEEPSLATE_REDSTONE_ORE -> 2
                Material.LAPIS_ORE -> 3
                Material.DEEPSLATE_LAPIS_ORE -> 3
                Material.DIAMOND_ORE -> 7
                Material.DEEPSLATE_DIAMOND_ORE -> 7
                Material.EMERALD_ORE -> 11
                Material.DEEPSLATE_EMERALD_ORE -> 11
                Material.DEEPSLATE -> 1
                Material.OBSIDIAN -> 3
                Material.ANCIENT_DEBRIS -> 19
                else -> 0
            }
        }

        EbiPowerStore.getInstance().tryGive(e.player, ep)
        HintStore.instance.achieve(e.player, Hint.MINERS_NEWBIE)
    }

    @EventHandler
    fun onNewDayToResetBrokenBlocksCount(e: RealTimeNewDayEvent) {
        PlayerStore.getInstance().openAll().forEach(Consumer { record: PlayerRecord ->
            record[PlayerDataKey.BROKEN_BLOCKS_COUNT] = 0
        })
    }

    private fun notification(p: Player, mes: String) {
        p.sendActionBar(Component.text(mes))
        // p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1, 2);
    }

    private fun playerIsInBlacklisted(p: Player): Boolean {
        val wName = p.world.name
        return epBlackList.contains(wName)
    }

    private fun getMobDropBonus(stack: ItemStack): Int {
        return stack.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS)
    }

    private fun getBlockDropBonus(stack: ItemStack): Int {
        return stack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)
    }
}