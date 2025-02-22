package work.xeltica.craft.core.api

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.scheduler.BukkitRunnable
import work.xeltica.craft.core.TCCorePlugin
import work.xeltica.craft.core.utils.Ticks
import java.io.File
import java.io.IOException
import java.util.function.Consumer

/**
 * Spigot の設定ファイル機能を扱いやすいように、面倒な部分をラッピングしています。
 * @author Lutica
 */
class Config constructor(val configName: String, private val onReloaded: Consumer<Config>? = null) {
    private var autoSaveWorker: BukkitRunnable? = null

    lateinit var conf: YamlConfiguration
        private set

    /**
     * trueにすると、5秒に1度自動保存します。
     */
    var useAutoSave = false
        set(value) {
            if (field == value) return
            field = value
            if (field) {
                object : BukkitRunnable() {
                    override fun run() {
                        save()
                    }
                }.run {
                    runTaskTimer(TCCorePlugin.instance, 0L, Ticks.from(5.0).toLong())
                    autoSaveWorker = this
                }
            } else {
                autoSaveWorker?.cancel()
                autoSaveWorker = null
            }
        }

    fun reload() {
        conf = YamlConfiguration.loadConfiguration(openFile())
        onReloaded?.accept(this)
    }

    @Throws(IOException::class)
    fun save() {
        conf.save(openFile())
        reload()
    }

    private fun openFile(): File {
        return openFile(configName)
    }

    companion object {
        fun exists(configName: String): Boolean {
            return openFile(configName).exists()
        }

        fun delete(configName: String): Boolean {
            return openFile(configName).delete()
        }

        private fun openFile(configName: String): File {
            val folder = TCCorePlugin.instance.dataFolder
            return File(folder, "$configName.yml")
        }
    }

    init {
        reload()
    }
}