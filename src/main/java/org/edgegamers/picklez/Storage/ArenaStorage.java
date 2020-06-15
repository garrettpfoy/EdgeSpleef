package org.edgegamers.picklez.Storage;

import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.List;

public class ArenaStorage extends YamlConfig {

    public ArenaStorage(String name) {
        loadConfiguration("arena.yml", "Arenas/" + name + ".yml");
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        save("name", name);
    }

    public String getType() {
        return getString("type");
    }

    public void setType(String type) {
        save("type", type);
    }

    public boolean isRunning() {
        return getBoolean("running");
    }

    public void setRunning(boolean isRunning) {
        save("running", isRunning);
    }

    public SerializedMap getLobbyMap() {
        return getMap("coords.lobby");
    }

    public void setLobbyMap(SerializedMap lobbyMap) {
        save("coords.lobby", lobbyMap);
    }

    public void setArenaSpawnMap(SerializedMap arenaSpawnMap) {
        save("coords.arena-spawn", arenaSpawnMap);
    }

    public SerializedMap getArenaSpawnMap() {
        return getMap("coords.arena-spawn");
    }

    public SerializedMap getSpectatorSpawnMap() {
        return getMap("coords.spectators");
    }

    public void setSpectatorSpawnMap(SerializedMap specMap) {
        save("coords.spectators", specMap);
    }

    public List<String> getAlivePlayers() {
        return getStringList("alive-players");
    }

    public List<String> getSpectators() {
        return getStringList("spectators");
    }

    public void setAlivePlayers(List<String> alivePlayers) {
        save("alive-players", alivePlayers);
    }

    public void setSpectators(List<String> spectators) {
        save("spectators", spectators);
    }

    public void setWorld(String world) {
        save("world", world);
    }

    public String getWorld() {
        return getString("world");
    }

    public boolean isCountingDown() {
        return getBoolean("inCountdown");
    }

    public void setIsCountingDown(boolean isCountingDown) {
        save("inCountdown", isCountingDown);
    }

    public int getRadius() {
        return getInteger("radius");
    }

    public void setRadius(int radius) {
        save("radius", radius);
    }

    public void setEndGameMap(SerializedMap map) {
        save("coords.end-game", map);
    }

    public SerializedMap getEndGameMap() {
        return getMap("coords.end-game");
    }

}
