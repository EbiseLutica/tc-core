package work.xeltica.craft.core.stores;

import java.io.IOException;

import work.xeltica.craft.core.XCorePlugin;
import work.xeltica.craft.core.utils.Config;

/**
 * プラグインのメタ情報を管理します。
 * @author Xeltica
 */
public class MetaStore {
    public MetaStore() {
        MetaStore.instance = this;
        meta = new Config("meta");
        checkUpdate();
    }

    public static MetaStore getInstance() {
        return instance;
    }

    public String getCurrentVersion() {
        return XCorePlugin.getInstance().getDescription().getVersion();
    }

    public String getPreviousVersion() {
        return previousVersion;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public String[] getChangeLog() {
        return changeLog;
    }

    private void checkUpdate() {
        final var conf = meta.getConf();
        final var currentVersion = conf.getString("version", null);
        previousVersion = conf.getString("previousVersion", null);
        if (currentVersion == null || !currentVersion.equals(getCurrentVersion())) {
            conf.set("version", getCurrentVersion());
            conf.set("previousVersion", currentVersion);
            previousVersion = currentVersion;
            isUpdated = true;
            try {
                meta.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private final Config meta;
    private String previousVersion;
    private boolean isUpdated;

    // TODO: チェンジログをここではなく別ファイルに書いてそれを参照する。
    // やり方を調べる必要がある
    private final String[] changeLog = {
            "キルしたモブごとに入手EPを変動させる"
    };

    private static MetaStore instance;
}
