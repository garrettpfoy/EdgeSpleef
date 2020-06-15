package org.edgegamers.picklez.Hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.edgegamers.picklez.Main.EdgeSpleef;
import org.edgegamers.picklez.Storage.PlayerStorage;

public class SpleefPlaceholder extends PlaceholderExpansion {

    private EdgeSpleef plugin;

    public SpleefPlaceholder(EdgeSpleef plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "spleef";
    }

    @Override
    public String getAuthor() {
        return "Garrett";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if(player == null) {
            return "";
        }

        if(identifier.equalsIgnoreCase("wins")) {
            PlayerStorage storage = new PlayerStorage(player.getUniqueId().toString());
            return String.valueOf(storage.getWins());
        }
        else if(identifier.equalsIgnoreCase("losses")) {
            PlayerStorage storage = new PlayerStorage(player.getUniqueId().toString());
            return String.valueOf(storage.getLoses());
        }
        else if(identifier.equalsIgnoreCase("ties")) {
            PlayerStorage storage = new PlayerStorage(player.getUniqueId().toString());
            return String.valueOf(storage.getTies());
        }
        else if(identifier.equalsIgnoreCase("games_played")) {
            PlayerStorage storage = new PlayerStorage(player.getUniqueId().toString());
            return String.valueOf(storage.getGamesPlayed());
        }
        else {
            return null;
        }
    }
}
