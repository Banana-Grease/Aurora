package me.oscarcusick.aurora.Commands.Executors;

import me.oscarcusick.aurora.Commands.HiddenCommandEvent;
import me.oscarcusick.aurora.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class DisableCommandsCommandExecutor implements Listener {
    Plugin PluginInstance;
    public DisableCommandsCommandExecutor (Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }
    boolean CommandsDisabled = false; // reset everytime the plugin restarts

    @EventHandler
    public void DisableCommandsCommandExecutor(HiddenCommandEvent event) {
        if (!event.command.equalsIgnoreCase("DisableCommands")) {
            return;
        }
        GeneralUtility GU = new GeneralUtility(PluginInstance);
        String Content = (GU.LightBlue + "" + ChatColor.BOLD + "'" + GU.Gold + "" + ChatColor.BOLD + "Disable Commands" + GU.LightBlue + "" + ChatColor.BOLD + "' " + GU.Grey + "Is currently set to: ");

        // if arguments are provided, set correct variable state
        if (event.strings.length > 0) {
            CommandsDisabled = GU.StringContainsTrue(event.strings[0].toString());
        }

        // send what the current state if after making changes if required
        if (CommandsDisabled) {
            Content += (ChatColor.GREEN + "" + ChatColor.BOLD + "TRUE");
        } else {
            Content += (ChatColor.RED + "" + ChatColor.BOLD + "FALSE");
        }
        GU.TellPlayer(event.commandSender, Content);
    }

    @EventHandler // disable commands sent by players
    public void DisableSentCommands(PlayerCommandPreprocessEvent event) {
        if (!CommandsDisabled) {
            return;
        }
        event.setCancelled(true);
    }


    @EventHandler (priority = EventPriority.LOWEST) // disable commands sent by console
    public void DisableSentCommands(ServerCommandEvent event) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        // ANCC = Allow Next Console Command
        // if any players have the NBT 'ANCC', set it to false and DON'T cancel the event.
        // this is needed due to sudo runs a console command.
        if (!GU.AnyPlayersHaveNBT("ANCC").isEmpty()) {
            ArrayList<Player> PlayersWithANCC = GU.AnyPlayersHaveNBT("ANCC");
            for (Player P : PlayersWithANCC) {
                P.getPersistentDataContainer().remove(new NamespacedKey(PluginInstance, "ANCC"));
            } // now no players on the server have the nbt, we continue
        }

        if (CommandsDisabled) { // has to be checked after as sudo will always set ANCC to true, so we always have to remove it
            event.setCancelled(true);
        }
    }
}
