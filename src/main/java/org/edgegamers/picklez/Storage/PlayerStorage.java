package org.edgegamers.picklez.Storage;

import org.mineacademy.fo.settings.YamlConfig;

public class PlayerStorage extends YamlConfig {

    public PlayerStorage(String uuid) {
        loadConfiguration("player.yml", "Players/" + uuid + ".yml");
    }

    public int getWins() {
        return getInteger("wins");
    }

    public int getLoses() {
        return getInteger("losses");
    }

    public int getTies() {
        return getInteger("ties");
    }

    public int getGamesPlayed() {
        return getInteger("games-played");
    }

    public void setWins(int wins) {
        save("wins", wins);
    }

    public void setLoses(int loses) {
        save("losses", loses);
    }

    public void setTies(int ties) {
        save("ties", ties);
    }

    public void setGamesPlayed(int gamesPlayed) {
        save("games-played", gamesPlayed);
    }

    public void setCurrentArena(String arenaName) {
        save("currentArena", arenaName);
    }

    public String getCurrentArena() {
        return getString("currentArena");
    }
}
