package org.edgegamers.picklez.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.edgegamers.picklez.Storage.PlayerStorage;
import org.mineacademy.fo.Common;

public class onCommand implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {

        PlayerStorage storage = new PlayerStorage(event.getPlayer().getUniqueId().toString());

        if (!(storage.getCurrentArena().equalsIgnoreCase("EMPTY"))) {
            //Player is in an arena

            if (!(event.getMessage().equalsIgnoreCase("/spleef-leave") || event.getMessage().equalsIgnoreCase("spleef-leave"))) {
                //Command is not to leave
                event.setCancelled(true);

                Common.tell(event.getPlayer(), "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bYou can not use commands in-game! Use &f/spleef-leave &bto leave all arenas!");
            }
        }
    }
}
