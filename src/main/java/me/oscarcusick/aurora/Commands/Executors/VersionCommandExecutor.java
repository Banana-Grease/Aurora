package me.oscarcusick.aurora.Commands.Executors;

import me.oscarcusick.aurora.Aurora;
import me.oscarcusick.aurora.Commands.HiddenCommandEvent;
import me.oscarcusick.aurora.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class VersionCommandExecutor implements Listener {
    Plugin PluginInstance;
    String Version, Build;
    public VersionCommandExecutor(Plugin PluginInstance, String Version, String Build) {
        this.PluginInstance = PluginInstance;
        this.Version = Version;
        this.Build = Build;
    }

    @EventHandler
    public void DoSecurityPrefixCommand(HiddenCommandEvent event) {
        if (!event.command.equalsIgnoreCase("Version")) {
            return;
        }
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        GU.TellPlayer(event.commandSender, "Version: " + GU.Purple + "" + ChatColor.BOLD + Version);
        GU.TellPlayer(event.commandSender, "Build: " + GU.Purple + "" + ChatColor.BOLD + Build);
    }
}
