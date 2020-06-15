package org.edgegamers.picklez.Listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.edgegamers.picklez.Main.EdgeSpleef;
import org.edgegamers.picklez.Storage.ArenaStorage;
import org.edgegamers.picklez.Storage.PlayerStorage;
import org.edgegamers.picklez.Storage.SpleefConfig;
import org.edgegamers.picklez.Utils.SpleefHelper;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;

import java.util.List;

public class onSignClick implements Listener {

    //[Spleef]
    //JOIN
    //[ArenaName]

    @EventHandler
    public void onSignClick(PlayerInteractEvent event) {
        if(event.getClickedBlock() != null) {
            BlockState sign = event.getClickedBlock().getState();
            if(sign instanceof Sign) {
                Sign signActual = (Sign) sign;

                String[] lines = signActual.getLines();
                lines[0] = ChatColor.stripColor(lines[0]);
                lines[1] = ChatColor.stripColor(lines[1]);

                if(lines[0].equalsIgnoreCase("[Spleef]") && lines[1].equalsIgnoreCase("JOIN")) {
                    //IS a spleef sign
                    SpleefConfig config = new SpleefConfig();

                    List<String> arenas = config.getArenaList();

                    if(arenas.contains(lines[2].toUpperCase())) {
                        ArenaStorage storage = new ArenaStorage(lines[2].toUpperCase());

                        List<String> playerNames = storage.getAlivePlayers();

                        if(playerNames.contains(event.getPlayer().getName().toUpperCase())) {
                            Common.tell(event.getPlayer(), "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bYou are already in that arena!");
                            return;
                        }
                        else {
                            playerNames.add(event.getPlayer().getName().toUpperCase());
                            Common.tell(event.getPlayer(), "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bYou have joined arena: &f" + lines[2].toUpperCase());
                            storage.setAlivePlayers(playerNames);

                            SpleefHelper.sendMessage("&b&lSPLEEF &8\u00BB &f" + event.getPlayer().getName() + "&b has joined the arena!", lines[2].toUpperCase());


                            SerializedMap teleportMap = storage.getLobbyMap();
                            World world = Bukkit.getWorld(storage.getWorld());
                            Location loc = new Location(world, teleportMap.getDouble("x"), teleportMap.getDouble("y"), teleportMap.getDouble("z"));

                            event.getPlayer().teleport(loc);

                            PlayerStorage data = new PlayerStorage(event.getPlayer().getUniqueId().toString());
                            data.setCurrentArena(lines[2].toUpperCase());

                            if(storage.getAlivePlayers().size() >= 3) {
                                //Start timer if not in countdown
                                if(!(storage.isCountingDown())) {
                                    storage.setIsCountingDown(true);

                                    new BukkitRunnable() {
                                        int timer = 30;

                                        public void run() {

                                            ArenaStorage storage = new ArenaStorage(lines[2].toUpperCase());

                                            if(storage.getAlivePlayers().size() == 1 || storage.getAlivePlayers().size() == 0 | storage.getAlivePlayers().size() == 2) {
                                                Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bCountdown has been cancelled due to lack of players!");
                                                storage.setIsCountingDown(false);
                                                cancel();
                                                return;
                                            }

                                            if(timer == 30) {
                                                Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game will start in &f" + timer + " sec(s)&b!");
                                            }
                                            else if(timer == 15) {
                                                Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game will start in &f" + timer + " sec(s)&b!");
                                            }
                                            else if(timer == 10) {
                                                Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game will start in &f" + timer + " sec(s)&b!");
                                            }
                                            else if(timer == 5) {
                                                Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game will start in &f" + timer + " sec(s)&b!");
                                            }
                                            else if(timer == 3) {
                                                Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game will start in &f" + timer + " sec(s)&b!");
                                            }
                                            else if(timer == 2) {
                                                Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game will start in &f" + timer + " sec(s)&b!");
                                            }
                                            else if(timer == 1) {
                                                Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game will start in &f" + timer + " sec(s)&b!");
                                            }
                                            else if(timer == 0) {
                                                Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game is starting &fnow&b!");

                                                SpleefHelper.normalStart(lines[2].toUpperCase());

                                                cancel();
                                                return;
                                            }
                                            timer--;
                                        }
                                    }.runTaskTimer(EdgeSpleef.getInstance(), 0, 20);
                                }
                            }

                        }


                    }

                }


            }

        }
    }
}
