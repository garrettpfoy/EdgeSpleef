package org.edgegamers.picklez.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.edgegamers.picklez.Storage.PlayerStorage;

public class SpleefonLogin implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {

        PlayerStorage storage = new PlayerStorage(event.getPlayer().getUniqueId().toString());
    }
}
