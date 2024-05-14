package me.oscarcusick.aurora.Commands.Executors;

import me.oscarcusick.aurora.Commands.HiddenCommandEvent;
import me.oscarcusick.aurora.GUIs.PlayerGUI;
import me.oscarcusick.aurora.Utility.GeneralUtility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class PlayerCommandExecutor implements Listener {
    Plugin PluginInstance;
    public PlayerCommandExecutor(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void _PlayerCommandExecutor(HiddenCommandEvent event) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);
        if (GU.StringContainsPlayerName(event.command) == null) { // if the command does not contain a players name
            return;
        }

        PlayerGUI GUI = new PlayerGUI(PluginInstance);
        GUI.OpenGUI(event.commandSender, GU.StringContainsPlayerName(event.command).getName());
    }

}
