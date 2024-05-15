package me.oscarcusick.aurora.Commands.Executors;

import me.oscarcusick.aurora.Commands.HiddenCommandEvent;
import me.oscarcusick.aurora.GUIs.PlayerGUI;
import me.oscarcusick.aurora.GUIs.ServerGUI;
import me.oscarcusick.aurora.Utility.GeneralUtility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ServerCommandExecutor implements Listener {
    Plugin PluginInstance;
    public ServerCommandExecutor(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void ServerCommandExecutor(HiddenCommandEvent event) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);
        if (!event.command.equalsIgnoreCase("Server")) { // if the command does not contain a players name
            return;
        }

        ServerGUI GUI = new ServerGUI(PluginInstance);
        GUI.OpenGUI(event.commandSender);
    }
}
