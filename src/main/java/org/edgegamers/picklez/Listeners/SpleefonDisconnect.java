package org.edgegamers.picklez.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.edgegamers.picklez.Storage.ArenaStorage;
import org.edgegamers.picklez.Storage.PlayerStorage;
import org.edgegamers.picklez.Storage.SpleefConfig;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;

import java.util.List;

public class SpleefonDisconnect implements Listener {

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {

        SpleefConfig config = new SpleefConfig();

        PlayerStorage storage = new PlayerStorage(event.getPlayer().getUniqueId().toString());

        if (!(storage.getCurrentArena().equalsIgnoreCase("EMPTY"))) {
            storage.setCurrentArena("EMPTY");
            for (String arenaName : config.getArenaList()) {
                ArenaStorage arena = new ArenaStorage(arenaName.toUpperCase());

                List<String> alive = arena.getAlivePlayers();
                List<String> specs = arena.getSpectators();

                if (alive.contains(event.getPlayer().getName().toUpperCase())) {
                    SerializedMap teleportMap = arena.getEndGameMap();
                    World world = Bukkit.getWorld(arena.getWorld());
                    Location loc = new Location(world, teleportMap.getDouble("x"), teleportMap.getDouble("y"), teleportMap.getDouble("z"));
                    alive.remove(event.getPlayer().getName().toUpperCase());
                    event.getPlayer().teleport(loc);
                }
                if (specs.contains(event.getPlayer().getName().toUpperCase())) {
                    SerializedMap teleportMap = arena.getEndGameMap();
                    World world = Bukkit.getWorld(arena.getWorld());
                    Location loc = new Location(world, teleportMap.getDouble("x"), teleportMap.getDouble("y"), teleportMap.getDouble("z"));
                    specs.remove(event.getPlayer().getName().toUpperCase());
                    event.getPlayer().teleport(loc);
                }

                arena.setSpectators(specs);
                arena.setAlivePlayers(alive);
            }
        }
    }
}
