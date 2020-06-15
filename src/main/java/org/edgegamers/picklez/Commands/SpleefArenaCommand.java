package org.edgegamers.picklez.Commands;

import org.bukkit.entity.Player;
import org.edgegamers.picklez.Storage.ArenaStorage;
import org.edgegamers.picklez.Storage.SpleefConfig;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.command.SimpleCommand;

import java.util.List;

public class SpleefArenaCommand extends SimpleCommand {

    public SpleefArenaCommand() {
        super("spleef-arena");
        setPermission("spleef.admin");
        setUsage("/spleef-arena <Create/SetSpawn/SetSpec/SetLobby/setEnd/Info> [Arena]");
    }

    @Override
    public void onCommand() {

        Player player = getPlayer();

        if(args.length == 0) {
            Common.tell(player, "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &b/spleef-arena <Create/setSpawn/setSpec> [ArenaName]");
        }

        else if(args.length == 2) {
            if (args[0].equalsIgnoreCase("CREATE")) {
                ArenaStorage storage = new ArenaStorage(args[1].toUpperCase());
                storage.setName(args[1].toUpperCase());
                SpleefConfig config = new SpleefConfig();

                List<String> arenas = config.getArenaList();

                if (!(arenas.contains(args[1].toUpperCase()))) {
                    arenas.add(args[1].toUpperCase());
                    config.setArenaList(arenas);
                }

                if (storage.getName().equalsIgnoreCase("EMPTY")) {
                    Common.tell(player, "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bI have created the arena, please make sure to set all the spawn points before usage!");
                } else {
                    Common.tell(player, "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bI have created the arena, please make sure to set all the spawn points before usage!");
                }
            } else if (args[0].equalsIgnoreCase("SETSPAWN")) {

                if (!isValidArena(args[1])) {
                    Common.tell(getPlayer(), "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bIt doesn't look like that is a valid arena, make sure to create one first!");
                    return;
                }

                ArenaStorage storage = new ArenaStorage(args[1].toUpperCase());

                storage.setWorld(getPlayer().getWorld().getName());

                SerializedMap tempMap = new SerializedMap();
                tempMap.put("x", getPlayer().getLocation().getBlockX());
                tempMap.put("y", getPlayer().getLocation().getBlockY());
                tempMap.put("z", getPlayer().getLocation().getBlockZ());

                storage.setArenaSpawnMap(tempMap);

                Common.tell(player, "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bYou have set the &f" + args[1] + "&7 arena's spawn point!");
            } else if (args[0].equalsIgnoreCase("SETSPEC")) {

                if (!isValidArena(args[1])) {
                    Common.tell(getPlayer(), "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bIt doesn't look like that is a valid arena, make sure to create one first!");
                    return;
                }

                ArenaStorage storage = new ArenaStorage(args[1].toUpperCase());

                SerializedMap tempMap = new SerializedMap();
                tempMap.put("x", getPlayer().getLocation().getBlockX());
                tempMap.put("y", getPlayer().getLocation().getBlockY());
                tempMap.put("z", getPlayer().getLocation().getBlockZ());

                storage.setSpectatorSpawnMap(tempMap);

                Common.tell(player, "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bYou have set the &f" + args[1] + "&7 arena's spectator point!");
            } else if (args[0].equalsIgnoreCase("SETLOBBY")) {

                if (!isValidArena(args[1])) {
                    Common.tell(getPlayer(), "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bIt doesn't look like that is a valid arena, make sure to create one first!");
                    return;
                }

                ArenaStorage storage = new ArenaStorage(args[1].toUpperCase());

                SerializedMap tempMap = new SerializedMap();
                tempMap.put("x", getPlayer().getLocation().getBlockX());
                tempMap.put("y", getPlayer().getLocation().getBlockY());
                tempMap.put("z", getPlayer().getLocation().getBlockZ());

                storage.setLobbyMap(tempMap);

                Common.tell(player, "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bYou have set the &f" + args[1] + "&7 arena's lobby point!");
            } else if (args[0].equalsIgnoreCase("INFO")) {

                if (!isValidArena(args[1])) {
                    Common.tell(getPlayer(), "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bIt doesn't look like that is a valid arena, make sure to create one first!");
                    return;
                } else {
                    ArenaStorage storage = new ArenaStorage(args[1].toUpperCase());

                    Common.tell(getPlayer(), "&8&l&M------------[&r &b&lARENA &7INFO &8&l&m]------------");
                    Common.tell(getPlayer(), "&7");
                    Common.tell(getPlayer(), "&3Name: &7" + storage.getName());
                    Common.tell(getPlayer(), "&3Type: &7" + storage.getType());
                    Common.tell(getPlayer(), "&3Alive Players: &7" + storage.getAlivePlayers().toString());
                    Common.tell(getPlayer(), "&3Spectators: &7" + storage.getSpectators().toString());
                    Common.tell(getPlayer(), "&7");
                    Common.tell(getPlayer(), "&8&l&m------------------------------------");
                }
            }
            else if(args[0].equalsIgnoreCase("SETEND")) {
                //Set endgame

                if (!isValidArena(args[1])) {
                    Common.tell(getPlayer(), "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bIt doesn't look like that is a valid arena, make sure to create one first!");
                    return;
                }

                ArenaStorage storage = new ArenaStorage(args[1].toUpperCase());

                SerializedMap tempMap = new SerializedMap();
                tempMap.put("x", getPlayer().getLocation().getBlockX());
                tempMap.put("y", getPlayer().getLocation().getBlockY());
                tempMap.put("z", getPlayer().getLocation().getBlockZ());

                storage.setEndGameMap(tempMap);

                Common.tell(player, "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bYou have set the &f" + args[1] + "&7 arena's end point!");
            }
            else {
                Common.tell(player, "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &b/spleef-arena <Create/setSpawn/setSpec> [ArenaName]");
            }
        }
    }

    private static boolean isValidArena(String arenaName) {
        SpleefConfig config = new SpleefConfig();

        if(config.getArenaList().contains(arenaName.toUpperCase())) {
            return true;
        }
        else {
            return false;
        }
    }
}
