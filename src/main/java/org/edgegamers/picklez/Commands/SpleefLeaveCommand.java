package org.edgegamers.picklez.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.edgegamers.picklez.Storage.ArenaStorage;
import org.edgegamers.picklez.Storage.PlayerStorage;
import org.edgegamers.picklez.Storage.SpleefConfig;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.command.SimpleCommand;

import java.util.List;

public class SpleefLeaveCommand extends SimpleCommand {

    public SpleefLeaveCommand() {
        super("spleef-leave");
    }

    @Override
    protected void onCommand() {

        PlayerStorage storage = new PlayerStorage(getPlayer().getUniqueId().toString());

        if(storage.getCurrentArena().equalsIgnoreCase("EMPTY")) {
            Common.tell(getPlayer(), "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bYou aren't in any arenas!");
        }
        else {
            SpleefConfig config = new SpleefConfig();
            storage.setCurrentArena("EMPTY");
            for(String arenaName : config.getArenaList()) {
                ArenaStorage arena = new ArenaStorage(arenaName.toUpperCase());

                List<String> alive = arena.getAlivePlayers();
                List<String> specs = arena.getSpectators();

                if(alive.contains(getPlayer().getName().toUpperCase())) {
                    SerializedMap teleportMap = arena.getEndGameMap();
                    World world = Bukkit.getWorld(arena.getWorld());
                    Location loc = new Location(world, teleportMap.getDouble("x"), teleportMap.getDouble("y"), teleportMap.getDouble("z"));
                    alive.remove(getPlayer().getName().toUpperCase());
                    getPlayer().teleport(loc);
                }
                if(specs.contains(getPlayer().getName().toUpperCase())) {
                    SerializedMap teleportMap = arena.getEndGameMap();
                    World world = Bukkit.getWorld(arena.getWorld());
                    Location loc = new Location(world, teleportMap.getDouble("x"), teleportMap.getDouble("y"), teleportMap.getDouble("z"));
                    specs.remove(getPlayer().getName().toUpperCase());
                    getPlayer().teleport(loc);
                }

                arena.setSpectators(specs);
                arena.setAlivePlayers(alive);
            }

            Common.tell(getPlayer(), "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bYou have left the arena!");
        }

    }
}
