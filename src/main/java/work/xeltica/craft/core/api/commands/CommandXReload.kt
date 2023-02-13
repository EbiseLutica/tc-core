package work.xeltica.craft.core.api.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import work.xeltica.craft.core.modules.mobball.MobBallModule

/**
 * X-Core の設定ファイルなどの再読み込みコマンド。
 */
class CommandXReload : CommandBase() {
    override fun execute(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false
        if (args[0] == "all" || args[0] == "mobball") MobBallModule.reload()
        return true
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<String>): MutableList<String> {
        return if (args.size == 1) mutableListOf("all", "mobball", "notification", "firework") else mutableListOf()
    }
}