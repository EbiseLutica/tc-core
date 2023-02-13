package work.xeltica.craft.core.modules.fly

import work.xeltica.craft.core.TCCorePlugin
import work.xeltica.craft.core.api.ModuleBase

/**
 * 飛行中にパーティクルを表示する機能を提供します。
 */
object FlyModule : ModuleBase() {
    override fun onEnable() {
        FlyingObserver().runTaskTimer(TCCorePlugin.instance, 0, 4)
    }
}