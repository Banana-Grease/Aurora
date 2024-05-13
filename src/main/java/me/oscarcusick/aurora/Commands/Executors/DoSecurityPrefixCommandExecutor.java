package me.oscarcusick.aurora.Commands.Executors;

import me.oscarcusick.aurora.Commands.HiddenCommandEvent;
import me.oscarcusick.aurora.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class DoSecurityPrefixCommandExecutor implements Listener {
    Plugin PluginInstance;
    public DoSecurityPrefixCommandExecutor(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }


    @EventHandler
    public void DoSecurityPrefixCommand(HiddenCommandEvent event) {
        if (!event.command.equalsIgnoreCase("DoSecurityPrefix")) {
            return;
        }
        GeneralUtility GU = new GeneralUtility(PluginInstance);
        String Content = (GU.LightBlue + "" + ChatColor.BOLD + "'" + GU.Gold + "" + ChatColor.BOLD + "Do Security Prefix" + GU.LightBlue + "" + ChatColor.BOLD + "' " + GU.Grey + "Is currently set to: ");

        // if arguments are provided, add / remove variable
        if (event.strings.length > 0) {
            boolean DoPrefixTrue = false; // set to false, correct later if needed

            // for loop checking if it matches any flags for true
            for (int i = 0; i < GU.ArgumentTrueFlags.length; i++) {
                if (event.strings[0].toString().equals(GU.ArgumentTrueFlags[i])) { // if strings[0] matches current argument flag
                    DoPrefixTrue = true; // override to true
                    break;
                }
            }

            if (GU.DoSecurityPrefix( event.commandSender) && !DoPrefixTrue) { // if Player has un-needed flag && shouldn't have it
                GU.SetDoSecurityPrefix(( event.commandSender), false); // set false / remove flag
            } else if (!GU.DoSecurityPrefix( event.commandSender) && DoPrefixTrue) { // if Player doesn't have a flag && should have it
                GU.SetDoSecurityPrefix(( event.commandSender), true); // set false / remove flag
            }
        }

        // send what the current state if after making changes if required
        if (GU.DoSecurityPrefix(event.commandSender)) {
            Content += (ChatColor.GREEN + "" + ChatColor.BOLD + "TRUE");
            GU.TellPlayer(event.commandSender, event.commandSender.getName());
        } else {
            Content += (ChatColor.RED + "" + ChatColor.BOLD + "FALSE");
        }

        // finally send updated information
        GU.TellPlayer(event.commandSender, Content); // Done
    }
}
