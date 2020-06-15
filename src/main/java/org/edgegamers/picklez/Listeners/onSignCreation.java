package org.edgegamers.picklez.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.edgegamers.picklez.Storage.ArenaStorage;
import org.mineacademy.fo.Common;

public class onSignCreation implements Listener {

    @EventHandler
    public void onSignCreation(SignChangeEvent event) {
        String[] lines = event.getLines();

        if(lines[0].equalsIgnoreCase("[Spleef]") && lines[1].equalsIgnoreCase("JOIN")) {
            String prefix = Common.colorize("&1[&1&lSPLEEF&r&1]");
            String type = Common.colorize("&2Join");

            event.setLine(0, prefix);
            event.setLine(1, type);

            Common.tell(event.getPlayer(), "&1&lE&9&lG&f&lO &b&lSPLEEF &f\u00BB &bSign has been updated successfully!");

        }

    }
}
