package org.edgegamers.picklez.Commands;

import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommand;

public class SpleefHelpCommand extends SimpleCommand {

    public SpleefHelpCommand() {
        super("spleef");
    }

    @Override
    public void onCommand() {
        Player player = getPlayer();

        Common.tell(player, "&8&l&m---------------[&r &d&lSPLEEF &8&l&m]---------------");
        Common.tell(player, "&8");
        Common.tell(player, "&5/spleef &8- &7Shows this help menu");
        Common.tell(player, "&5/spleef-arena &8- &7Brings up arena menu");
        Common.tell(player, "&5/spleef-join &8- &7Joins a game of spleef");
        Common.tell(player, "&8");
        Common.tell(player, "&8&l&m-----------------------------------------");
    }
}
