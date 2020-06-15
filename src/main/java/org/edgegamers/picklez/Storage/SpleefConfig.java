package org.edgegamers.picklez.Storage;

import org.mineacademy.fo.settings.YamlConfig;

import java.util.List;

public class SpleefConfig extends YamlConfig {

    public SpleefConfig() {
        loadConfiguration("config.yml", "config.yml");
    }

    public void setArenaList(List<String> arenas) {
        save("arenaList", arenas);
    }

    public List<String> getArenaList() {
        return getStringList("arenaList");
    }

    public void setRunning(boolean isRunning) {
        save("isRunning", isRunning);
    }

    public boolean isRunning() {
        return getBoolean("isRunning");
    }
}
