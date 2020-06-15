package org.edgegamers.picklez.Commands;

import org.bukkit.scheduler.BukkitRunnable;
import org.edgegamers.picklez.Main.EdgeSpleef;
import org.edgegamers.picklez.Storage.ArenaStorage;
import org.edgegamers.picklez.Storage.PlayerStorage;
import org.edgegamers.picklez.Storage.SpleefConfig;
import org.edgegamers.picklez.Utils.SpleefHelper;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommand;

import java.util.List;

public class SpleefJoinCommand extends SimpleCommand {

    public SpleefJoinCommand() {
        super("spleef-join");
        setPermission("spleef.player");
        setUsage("/spleef-join [Arena]");
    }

    @Override
    public void onCommand() {
        if(args.length != 1) {
            Common.tell(getPlayer(), "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bImproper usage: /spleef-join [Arena]");
        }
        else {
            SpleefConfig config = new SpleefConfig();
            if(config.getArenaList().contains(args[0].toUpperCase())) {
                ArenaStorage storage = new ArenaStorage(args[0].toUpperCase());

                List<String> playerNames = storage.getAlivePlayers();

                if(playerNames.contains(getPlayer().getName().toUpperCase())) {
                    Common.tell(getPlayer(), "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bYou are already in that arena!");
                    return;
                }
                else {
                    playerNames.add(getPlayer().getName().toUpperCase());
                    Common.tell(getPlayer(), "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bYou have joined arena: &f" + args[0].toUpperCase());
                    storage.setAlivePlayers(playerNames);

                    PlayerStorage data = new PlayerStorage(getPlayer().getUniqueId().toString());
                    data.setCurrentArena(args[0].toUpperCase());

                    if(storage.getAlivePlayers().size() >= 2) {
                        //Start timer if not in countdown
                        if(!(storage.isCountingDown())) {
                            storage.setIsCountingDown(true);

                            new BukkitRunnable() {
                                int timer = 30;

                                public void run() {
                                    ArenaStorage storage = new ArenaStorage(args[0].toUpperCase());

                                    if(storage.getAlivePlayers().size() <= 1) {
                                        Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bCountdown has been cancelled due to lack of players!");
                                        storage.setIsCountingDown(false);
                                        cancel();
                                    }

                                    if(timer == 30) {
                                        Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game will start in &f" + timer + " sec(s)&7!");
                                    }
                                    else if(timer == 15) {
                                        Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game will start in &f" + timer + " sec(s)&7!");
                                    }
                                    else if(timer == 10) {
                                        Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game will start in &f" + timer + " sec(s)&7!");
                                    }
                                    else if(timer == 5) {
                                        Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game will start in &f" + timer + " sec(s)&7!");
                                    }
                                    else if(timer == 3) {
                                        Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game will start in &f" + timer + " sec(s)&7!");
                                    }
                                    else if(timer == 2) {
                                        Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game will start in &f" + timer + " sec(s)&7!");
                                    }
                                    else if(timer == 1) {
                                        Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game will start in &f" + timer + " sec(s)&7!");
                                    }
                                    else if(timer == 0) {
                                        Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game is starting &fnow&7!");

                                        SpleefHelper.normalStart(args[0].toUpperCase());

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
