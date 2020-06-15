package org.edgegamers.picklez.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.Objects;

public class onItemHit implements Listener {

    @EventHandler
    public void onItemHit(ProjectileHitEvent event) {
        if(event.getEntity() instanceof Snowball && event.getHitBlock() != null) {
            if (Objects.requireNonNull(event.getHitBlock()).getBlockData().getMaterial().toString().equalsIgnoreCase("SNOW_BLOCK")) {
                event.getHitBlock().setType(Material.AIR);
            }
        }
    }
}
