package org.edgegamers.picklez.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.edgegamers.picklez.Storage.PlayerStorage;
import org.edgegamers.picklez.Storage.ArenaStorage;
import org.edgegamers.picklez.Storage.SpleefConfig;
import org.edgegamers.picklez.Utils.SpleefHelper;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;

import java.util.List;

public class onLiquidTouch implements Listener {


    @EventHandler
    public void onLiquidTouch(PlayerMoveEvent event) {

        SpleefConfig config = new SpleefConfig();

        if(!((config.isRunning()))) {
            return;
        }

        if(event.getTo().getBlock().isLiquid()) {
            PlayerStorage data = new PlayerStorage(event.getPlayer().getUniqueId().toString());
            if(!(data.getCurrentArena().equalsIgnoreCase("EMPTY"))) {
                ArenaStorage arena = new ArenaStorage(data.getCurrentArena().toUpperCase());

                SerializedMap teleportMap = arena.getSpectatorSpawnMap();
                World world = Bukkit.getWorld(arena.getWorld());
                Location loc = new Location(world, teleportMap.getDouble("x"), teleportMap.getDouble("y"), teleportMap.getDouble("z"));

                String title = Common.colorize("&b&lSPLEEF");
                String subtitle = Common.colorize("&bYou have fallen and died!");

                event.getPlayer().teleport(loc);
                event.getPlayer().sendTitle(title, subtitle, 10, 20, 10);

                SpleefHelper.sendMessage("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &f" + event.getPlayer().getName() + "&b has fallen and died!", data.getCurrentArena().toUpperCase());

                List<String> alive = arena.getAlivePlayers();
                alive.remove(event.getPlayer().getName().toUpperCase());
                arena.setAlivePlayers(alive);

                List<String> spec = arena.getSpectators();
                spec.add(event.getPlayer().getName().toUpperCase());
                arena.setSpectators(spec);

                event.getPlayer().setGameMode(GameMode.SPECTATOR);

            }
        }
    }
}
