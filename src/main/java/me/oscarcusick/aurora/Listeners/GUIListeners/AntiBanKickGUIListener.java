package me.oscarcusick.aurora.Listeners.GUIListeners;

import me.oscarcusick.aurora.GUIs.AntiBanKickGUI;
import me.oscarcusick.aurora.GUIs.PlayerGUI;
import me.oscarcusick.aurora.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class AntiBanKickGUIListener implements Listener {
    Plugin PluginInstance;
    public AntiBanKickGUIListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public String GetModuleName(String NBTKeyShort) {
        // determine module name to update GUI & inform user
        if (NBTKeyShort.equalsIgnoreCase("AK")) {
            return "Anti Kick";
        } else {
            return "Anti Ban";
        }
    }

    public String GetNBTKey(String GUITitle) {
        if (GUITitle.contains("Kick")) { // kick
            return "AK";
        } else { // ban
            return "AB";
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void GUIClickListener(InventoryClickEvent event) {
        // make sure it contains anti, and safety checks
        if (!event.getView().getTitle().toLowerCase().contains("anti") || (event.getEventName().equalsIgnoreCase("InventoryCreativeEvent")) || (event == null)) {
            return;
        } event.setCancelled(true);

        if (!event.getCurrentItem().hasItemMeta()) { // SAFETY CHECK - make sure item has meta
            return;
        }

        GeneralUtility GU = new GeneralUtility(PluginInstance);

        /* Which NBT means what
        * No AB / No AK means Anti Ban / Kick Disabled
        * AB / AK 0 means noting
        * AB / AK 1 means Kick
        * AB / AK 2 means Crash attacker
        * */

        String NBTKey;
        String ModuleName;
        AntiBanKickGUI UpdatedGUI = new AntiBanKickGUI(PluginInstance);
        // figure out which button pressed and act appropriately
        switch (event.getCurrentItem().getType()) {
            case PLAYER_HEAD:
                PlayerGUI PGUI = new PlayerGUI(PluginInstance);
                PGUI.OpenGUI((Player)event.getWhoClicked(), (GU.StringContainsPlayerName(event.getCurrentItem().getItemMeta().getDisplayName()).getName()));
                return;
            case BRUSH: // do nothing for either ban / kick
                NBTKey = GetNBTKey(event.getView().getTitle());
                ModuleName = GetModuleName(NBTKey);

                event.getWhoClicked().getPersistentDataContainer().set(new NamespacedKey(PluginInstance, NBTKey), PersistentDataType.INTEGER, 0);
                GU.TellPlayer((Player)event.getWhoClicked(), "Enabled: " + ChatColor.RED + ChatColor.BOLD + ModuleName + GU.Purple + ChatColor.BOLD + "\n(" + ChatColor.GREEN + ChatColor.BOLD + "No Retaliation" + GU.Purple + ChatColor.BOLD + ")");
                UpdatedGUI = new AntiBanKickGUI(PluginInstance);
                UpdatedGUI.OpenGUI((Player)event.getWhoClicked(), event.getClickedInventory().getItem(0).getItemMeta().getDisplayName(), ModuleName);
                return;
            case IRON_BOOTS: // kick / ban for either ban / kick
                NBTKey = GetNBTKey(event.getView().getTitle());
                ModuleName = GetModuleName(NBTKey);

                event.getWhoClicked().getPersistentDataContainer().set(new NamespacedKey(PluginInstance, NBTKey), PersistentDataType.INTEGER, 1);
                GU.TellPlayer((Player)event.getWhoClicked(), "Enabled: " + ChatColor.RED + ChatColor.BOLD + ModuleName + GU.Purple + ChatColor.BOLD + "\n(" + ChatColor.GREEN + ChatColor.BOLD + "Retaliation" + GU.Purple + ChatColor.BOLD + ")");
                UpdatedGUI = new AntiBanKickGUI(PluginInstance);
                UpdatedGUI.OpenGUI((Player)event.getWhoClicked(), event.getClickedInventory().getItem(0).getItemMeta().getDisplayName(), ModuleName);
                return;
            case BLAZE_POWDER: // do nothing for either ban / kick
                NBTKey = GetNBTKey(event.getView().getTitle());
                ModuleName = GetModuleName(NBTKey);

                event.getWhoClicked().getPersistentDataContainer().set(new NamespacedKey(PluginInstance, NBTKey), PersistentDataType.INTEGER, 2);
                GU.TellPlayer((Player)event.getWhoClicked(), "Enabled: " + ChatColor.RED + ChatColor.BOLD + ModuleName + GU.Purple + ChatColor.BOLD + "\n(" + ChatColor.GREEN + ChatColor.BOLD + "Crash" + GU.Purple + ChatColor.BOLD + ")");
                UpdatedGUI = new AntiBanKickGUI(PluginInstance);
                UpdatedGUI.OpenGUI((Player)event.getWhoClicked(), event.getClickedInventory().getItem(0).getItemMeta().getDisplayName(), ModuleName);
                return;

                case MUSIC_DISC_PIGSTEP: // disable either
                    NBTKey = GetNBTKey(event.getView().getTitle());
                    ModuleName = GetModuleName(NBTKey);

                    event.getWhoClicked().getPersistentDataContainer().remove(new NamespacedKey(PluginInstance, NBTKey));
                    GU.TellPlayer((Player)event.getWhoClicked(), "Disabled: " + ChatColor.RED + ChatColor.BOLD + ModuleName);
                    UpdatedGUI = new AntiBanKickGUI(PluginInstance);
                    UpdatedGUI.OpenGUI((Player)event.getWhoClicked(), event.getClickedInventory().getItem(0).getItemMeta().getDisplayName(), ModuleName);
                return;
            default:
                return;
        }

    }
}
