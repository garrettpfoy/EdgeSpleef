package org.edgegamers.picklez.Commands;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.edgegamers.picklez.Main.EdgeSpleef;
import org.edgegamers.picklez.Storage.ArenaStorage;
import org.edgegamers.picklez.Utils.SpleefHelper;
import org.bukkit.scheduler.BukkitTask;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommand;

public class SpleefStartCommand extends SimpleCommand {

    private BukkitTask timerTask;

    public SpleefStartCommand() {
        super("spleef-start");
        setPermission("spleef.admin");
        setUsage("/spleef-start [Arena]");
    }

    @Override
    public void onCommand() {
        //Teleport players, give them items, and begin counter, make sure to keep track of players
        if(args.length == 1) {
            SpleefHelper.teleportPlayers(args[0].toUpperCase());
            SpleefHelper.giveItems(args[0].toUpperCase());

            new BukkitRunnable() {
                int timer = 300;

                public void run() {
                    ArenaStorage storage = new ArenaStorage(args[0].toUpperCase());
                    Common.broadcast("&9&lDEBUG &8\u00BB &7Current alive players: " + storage.getAlivePlayers().size() + " &8| &7Timer: &c" + timer);
                    if(timer == 300) {
                        Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bThe game has begun!");
                    }
                    else if(storage.getAlivePlayers().size() <= 1) {
                        Common.broadcast("&9&lDEBUG &8\u00BB &7Timer task completed");

                        String winner = storage.getAlivePlayers().get(0);
                        Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bArena: &f" + args[0].toUpperCase() + "&7 has ended! Winner: &f" + winner + "&7!");

                        SpleefHelper.endGame(args[0].toUpperCase());


                        cancel();
                    }
                    else if(timer == 30) {
                        Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &b30 seconds remaining!");
                    }
                    else if(timer == 15) {
                        Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &b15 seconds remaining!");
                    }
                    else if(timer == 0) {
                        Common.broadcast("&9&lDEBUG &8\u00BB &7Timer task completed");

                        String winner = storage.getAlivePlayers().get(0);
                        Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bArena: &f" + args[0].toUpperCase() + "&7 has ended! Winner: &f" + winner + "&7!");

                        SpleefHelper.endGame(args[0].toUpperCase());


                        cancel();
                    }
                    else {
                        String format = Common.colorize("&b&lSPLEEF &8\u00bb &f" + timer + "&7 seconds remaining!");
                        ItemStack shovel = getPlayer().getInventory().getItemInMainHand();

                        if(shovel != null) {
                            ItemMeta shovelMeta = shovel.getItemMeta();
                            shovelMeta.setDisplayName(format);
                            shovel.setItemMeta(shovelMeta);

                            getPlayer().getInventory().setItemInMainHand(shovel);
                        }
                    }
                    timer--;
                }
            }.runTaskTimer(EdgeSpleef.getInstance(), 0, 20);





        }
    }
}
