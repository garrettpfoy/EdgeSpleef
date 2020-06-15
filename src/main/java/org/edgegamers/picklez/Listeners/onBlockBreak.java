package org.edgegamers.picklez.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.edgegamers.picklez.Storage.ArenaStorage;
import org.edgegamers.picklez.Storage.SpleefConfig;

import java.util.List;
import java.util.Objects;

public class onBlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getBlock().getType().toString().equalsIgnoreCase("SNOW_BLOCK")) {
            SpleefConfig config = new SpleefConfig();
            if(config.isRunning()) {
                event.setDropItems(false);
                event.getPlayer().getInventory().addItem(new ItemStack(Material.SNOWBALL, 1));
            }
        }
    }
}
