package org.edgegamers.picklez.Utils;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.scheduler.BukkitRunnable;
import org.edgegamers.picklez.Main.EdgeSpleef;
import org.edgegamers.picklez.Storage.ArenaStorage;
import org.edgegamers.picklez.Storage.PlayerStorage;
import org.edgegamers.picklez.Storage.SpleefConfig;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;

import java.util.ArrayList;
import java.util.List;

public class SpleefHelper {

    public static void teleportPlayers(String arenaName) {

        SpleefConfig config = new SpleefConfig();
        ArenaStorage arena = new ArenaStorage(arenaName.toUpperCase());

        for(Player player : Bukkit.getOnlinePlayers()) {
            PlayerStorage temp = new PlayerStorage(player.getUniqueId().toString());

            if(temp.getCurrentArena().equalsIgnoreCase(arenaName)) {
                SerializedMap teleportMap = arena.getArenaSpawnMap();
                World world = Bukkit.getWorld(arena.getWorld());
                Location loc = new Location(world, teleportMap.getDouble("x"), teleportMap.getDouble("y"), teleportMap.getDouble("z"));

                String title = Common.colorize("&b&lSPLEEF");
                String subtitle = Common.colorize("&7Teleporting to arena...");

                player.teleport(loc);
                player.sendTitle(title, subtitle, 20, 40, 20);
            }
        }
    }

    public static void giveItems(String arenaName) {
        SpleefConfig config = new SpleefConfig();
        ArenaStorage arena = new ArenaStorage(arenaName.toUpperCase());

        ItemStack gShovel = new ItemStack(Material.GOLDEN_SHOVEL);

        gShovel.addUnsafeEnchantment(Enchantment.DIG_SPEED, 10);
        ItemMeta gShovelMeta = gShovel.getItemMeta();
        gShovelMeta.setUnbreakable(true);

        gShovel.setItemMeta(gShovelMeta);


        for(Player player : Bukkit.getOnlinePlayers()) {
            PlayerStorage temp = new PlayerStorage(player.getUniqueId().toString());

            if(temp.getCurrentArena().equalsIgnoreCase(arenaName)) {
                player.getInventory().clear();
                player.getInventory().addItem(gShovel);
            }

            Common.tell(player, "&b&lSPLEEF &8\u00BB &7You have been given the golden shovel!");
        }
    }

    public static void endGame(String arenaName) {
        SpleefConfig config = new SpleefConfig();
        config.setRunning(false);
        ArenaStorage storage = new ArenaStorage(arenaName.toUpperCase());
        World world = Bukkit.getWorld(storage.getWorld());
        String winner = "";

        Economy econ = EdgeSpleef.getEconomy();

        if(storage.getAlivePlayers().size() <= 0) {
            //Tie
            Common.broadcast("&1&lE&9&lG&f&lO &b&lSPLEEF &f&l\u00BB &7Game has ended in a time!");

            SerializedMap teleportMap = storage.getEndGameMap();
            Location loc = new Location(world, teleportMap.getDouble("x"), teleportMap.getDouble("y"), teleportMap.getDouble("z"));

            for(Player player : Bukkit.getOnlinePlayers()) {
                PlayerStorage data = new PlayerStorage(player.getUniqueId().toString());
                if((data.getCurrentArena().equalsIgnoreCase(arenaName))) {
                    data.setGamesPlayed(data.getGamesPlayed() + 1);
                    data.setCurrentArena("EMPTY");

                    String title = Common.colorize("&b&lSPLEEF");
                    String subtitle = Common.colorize("&7Game over! Thanks for playing!");

                    player.teleport(loc);
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendTitle(title, subtitle, 20, 40, 20);
                    player.getInventory().clear();

                    econ.depositPlayer(player, 10);
                    Common.tell(player, "&1&lE&9&lG&f&lO &b&lSPLEEF &f&l\u00BB &7You have been given &310 &7tokens for playing!");
                }
            }
        }
        else {
            winner = storage.getAlivePlayers().get(0);

            SerializedMap teleportMap = storage.getEndGameMap();
            Location loc = new Location(world, teleportMap.getDouble("x"), teleportMap.getDouble("y"), teleportMap.getDouble("z"));

            for(Player player : Bukkit.getOnlinePlayers()) {
                PlayerStorage data = new PlayerStorage(player.getUniqueId().toString());
                if((data.getCurrentArena().equalsIgnoreCase(arenaName))) {
                    if (player.getName().equalsIgnoreCase(winner)) {
                        data.setWins(data.getWins() + 1);
                    }
                    else if(winner.length() == 0) {
                        data.setTies(data.getTies() + 1);
                    }
                    else {
                        data.setLoses(data.getLoses() + 1);
                    }
                    data.setCurrentArena("EMPTY");

                    String title = Common.colorize("&b&lSPLEEF");
                    String subtitle = Common.colorize("&7Game over! Thanks for playing!");

                    player.teleport(loc);
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendTitle(title, subtitle, 20, 40, 20);
                    player.getInventory().clear();

                    econ.depositPlayer(player, 10);
                    Common.tell(player, "&1&lE&9&lG&f&lO &b&lSPLEEF &f&l\u00BB &7You have been given &310 &7tokens for playing!");
                }
            }
        }

        List<String> emptyList = new ArrayList<String>();

        storage.setIsCountingDown(false);

        storage.setAlivePlayers(emptyList);
        storage.setSpectators(emptyList);

        storage.setRunning(false);

        SerializedMap genCoords = storage.getArenaSpawnMap();
        World genWorld = Bukkit.getWorld(storage.getWorld());
        Location genLoc = new Location(genWorld, genCoords.getDouble("x"), genCoords.getDouble("y"), genCoords.getDouble("z"));

        cylinder(genLoc, Material.SNOW_BLOCK, storage.getRadius());


    }

    public static void normalStart(String arenaName) {
        SpleefConfig config = new SpleefConfig();
        config.setRunning(true);
        SpleefHelper.teleportPlayers(arenaName.toUpperCase());
        SpleefHelper.giveItems(arenaName.toUpperCase());

        new BukkitRunnable() {
            int timer = 300;

            public void run() {
                ArenaStorage storage = new ArenaStorage(arenaName.toUpperCase());
                if (timer == 300) {
                    Common.broadcast("&b&lSPLEEF &8\u00BB &7The game has begun!");
                } else if (storage.getAlivePlayers().size() <= 1) {

                    String winner = storage.getAlivePlayers().get(0);
                    Common.broadcast("&b&lSPLEEF &8\u00BB &7Arena: &3" + arenaName.toUpperCase() + "&7 has ended! Winner: &3" + winner + "&7!");

                    SpleefHelper.endGame(arenaName.toUpperCase());


                    cancel();
                } else if (timer == 30) {
                    Common.broadcast("&b&lSPLEEF &8\u00BB &730 seconds remaining!");
                } else if (timer == 15) {
                    Common.broadcast("&b&lSPLEEF &8\u00BB &715 seconds remaining!");
                } else if (timer == 0) {

                    String winner = storage.getAlivePlayers().get(0);
                    Common.broadcast("&b&lSPLEEF &8\u00BB &7Arena: &3" + arenaName.toUpperCase() + "&7 has ended! Winner: &3" + winner + "&7!");

                    cancel();

                    SpleefHelper.endGame(arenaName.toUpperCase());

                } else {
                    for (Player player : Bukkit.getOnlinePlayers()) {

                        String format = Common.colorize("&b&lSPLEEF &8\u00bb &3" + timer + "&7 seconds remaining!");
                        ItemStack shovel = player.getInventory().getItemInMainHand();

                        if (shovel != null) {
                            if(shovel.hasItemMeta()) {
                                ItemMeta shovelMeta = shovel.getItemMeta();
                                shovelMeta.setDisplayName(format);
                                shovel.setItemMeta(shovelMeta);

                                player.getInventory().setItemInMainHand(shovel);
                            }
                        }
                    }
                }
                timer--;
            }
        }.runTaskTimer(EdgeSpleef.getInstance(), 0, 20);
    }

    public static void sendMessage(String message, String arenaName) {
        ArenaStorage storage = new ArenaStorage(arenaName.toUpperCase());
        List<String> players = storage.getAlivePlayers();
        List<String> spectators = storage.getSpectators();

        for(String playerName : players) {
            Player player = Bukkit.getPlayer(playerName);
            Common.tell(player, message);
        }
        for(String spectatorName : spectators) {
            Player player = Bukkit.getPlayer(spectatorName);
            Common.tell(player, message);
        }

    }
     private static void cylinder(Location loc, Material mat, int r) {
        int cx = loc.getBlockX();
        int cy = loc.getBlockY() - 1;
        int cz = loc.getBlockZ();
        World w = loc.getWorld();
        int rSquared = r * r;
        for (int x = cx - r; x <= cx +r; x++) {
            for (int z = cz - r; z <= cz +r; z++) {
                if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared) {
                    w.getBlockAt(x, cy, z).setType(mat);
                }
            }
        }
    }
}
