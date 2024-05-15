package me.oscarcusick.aurora.GUIs;

import me.oscarcusick.aurora.Utility.GUIUtility;
import me.oscarcusick.aurora.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class AntiBanKickGUI {
    Plugin PluginInstance;
    public AntiBanKickGUI(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public String GetRetaliationLevel(Player Defender, boolean CheckKick) { // fix
        if (CheckKick) {
            if (Defender.getPersistentDataContainer().has(new NamespacedKey(PluginInstance, "AK"))) {
                switch (Defender.getPersistentDataContainer().get(new NamespacedKey(PluginInstance, "AK"), PersistentDataType.INTEGER)) {
                    case 0:
                        return "Nothing";
                    case 1:
                        return "Retaliation";
                    case 2:
                        return "Crash";
                    default:
                        return (ChatColor.RED + "" + ChatColor.BOLD + "NULL");
                }
            }
        } else {
            if (Defender.getPersistentDataContainer().has(new NamespacedKey(PluginInstance, "AB"))) {
                switch (Defender.getPersistentDataContainer().get(new NamespacedKey(PluginInstance, "AB"), PersistentDataType.INTEGER)) {
                    case 0:
                        return "Nothing";
                    case 1:
                        return "Retaliation";
                    case 2:
                        return "Crash";
                    default:
                        return "";
                }
            }
        }
        return "";
    }

    public void OpenGUI(Player GUIOwner, String TargetName, String ModuleName) {
        GUIUtility GUIUtil = new GUIUtility();
        GeneralUtility GU = new GeneralUtility(PluginInstance);
        int GUISize = 9 * 3; // how wide * how many rows the GUI will be
        String RetaliationLevel;

        // SAFETY - if target name cant find player, return
        if (GU.StringContainsPlayerName(TargetName) == null) {
            GU.TellPlayer(GUIOwner, "Error in safety check\nAntiBanKickGUI class");
            return;
        }

        Inventory GUI = Bukkit.createInventory(GUIOwner, GUISize, (GU.AuroraChatLogo + GU.Grey + ChatColor.BOLD + "[" + ChatColor.BOLD + ChatColor.RED + ModuleName + GU.Grey + ChatColor.BOLD + "]"));
        ArrayList<String> GUIButtonLore = new ArrayList<>();

        // Empty Slots
        ItemStack EmptySlot = GUIUtil.GenerateGUIButton(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ");
        ItemStack DarkEmptySlot = GUIUtil.GenerateGUIButton(Material.GRAY_STAINED_GLASS_PANE, " ");

        // format GUI
        GUIUtil.FormatGUI(GUI, GUISize, DarkEmptySlot, EmptySlot);

        // Buttons
        ArrayList<String> ButtonLore = new ArrayList<>();
        ButtonLore.add(GU.Purple + "" + ChatColor.ITALIC + "Your Target Player");
        ButtonLore.add(GU.Purple + "" + ChatColor.ITALIC + "Click This To Go Back");
        GUI.setItem(0, GUIUtil.GenerateGUIButton(Material.PLAYER_HEAD, (ChatColor.RED  + TargetName), ButtonLore)); // Target Player, also back button

        // if the GUI is for anti kick
        if (ModuleName.equalsIgnoreCase("Anti Kick")) {
            // if anti kick is turned off
            if (!GU.StringContainsPlayerName(TargetName).getPersistentDataContainer().has(new NamespacedKey(PluginInstance, "AK"))) {
                ButtonLore.set(0, GU.Purple + "" + ChatColor.ITALIC + "Cancel the kick with");
                ButtonLore.set(1, GU.Purple + "" + ChatColor.ITALIC + "No retaliation");
                GUI.setItem(3+9, GUIUtil.GenerateGUIButton(Material.BRUSH,  ChatColor.GREEN + "Nothing", ButtonLore));

                ButtonLore.set(0, GU.Purple + "" + ChatColor.ITALIC + "Kick the player who");
                ButtonLore.set(1, GU.Purple + "" + ChatColor.ITALIC + "Tried to kick you");
                GUI.setItem(4+9, GUIUtil.GenerateGUIButton(Material.IRON_BOOTS,  ChatColor.YELLOW + "Kick", ButtonLore));

                ButtonLore.set(0, GU.Purple + "" + ChatColor.ITALIC + "Crash the player who");
                ButtonLore.set(1, GU.Purple + "" + ChatColor.ITALIC + "Tried to kick you");
                GUI.setItem(5+9, GUIUtil.GenerateGUIButton(Material.BLAZE_POWDER,  ChatColor.RED + "Crash", ButtonLore));
            } else { // if anti kick is turned on
                ButtonLore.set(0, GU.Purple + "" + ChatColor.ITALIC + "Disable Anti Kick");

                RetaliationLevel = GetRetaliationLevel(GUIOwner, true);

                ButtonLore.set(1, GU.Purple + "" + ChatColor.ITALIC + "Currently: " + ChatColor.GREEN + RetaliationLevel);
                GUI.setItem(4+9, GUIUtil.GenerateGUIButton(Material.MUSIC_DISC_PIGSTEP,  ChatColor.RED + "Disable", ButtonLore));
                ButtonLore.add(""); // add it back so there's always two lines of lore
            }
        } else { // anti ban
            // if anti ban is turned off
            if (!GU.StringContainsPlayerName(TargetName).getPersistentDataContainer().has(new NamespacedKey(PluginInstance, "AB"))) {
                ButtonLore.set(0, GU.Purple + "" + ChatColor.ITALIC + "Cancel the ban with");
                ButtonLore.set(1, GU.Purple + "" + ChatColor.ITALIC + "No retaliation");
                GUI.setItem(3+9, GUIUtil.GenerateGUIButton(Material.BRUSH,  ChatColor.GREEN + "Nothing", ButtonLore));

                ButtonLore.set(0, GU.Purple + "" + ChatColor.ITALIC + "Ban the player who");
                ButtonLore.set(1, GU.Purple + "" + ChatColor.ITALIC + "Tried to ban you");
                GUI.setItem(4+9, GUIUtil.GenerateGUIButton(Material.IRON_BOOTS,  ChatColor.YELLOW + "Ban", ButtonLore));

                ButtonLore.set(0, GU.Purple + "" + ChatColor.ITALIC + "Crash the player who");
                ButtonLore.set(1, GU.Purple + "" + ChatColor.ITALIC + "Tried to ban you");
                GUI.setItem(5+9, GUIUtil.GenerateGUIButton(Material.BLAZE_POWDER,  ChatColor.RED + "Crash", ButtonLore));
            } else { // if anti ban is turned on
                ButtonLore.set(0, GU.Purple + "" + ChatColor.ITALIC + "Disable Anti Kick");

                RetaliationLevel = GetRetaliationLevel(GUIOwner, false);

                ButtonLore.set(1, GU.Purple + "" + ChatColor.ITALIC + "Currently: " + ChatColor.GREEN + RetaliationLevel);
                GUI.setItem(4+9, GUIUtil.GenerateGUIButton(Material.MUSIC_DISC_PIGSTEP,  ChatColor.RED + "Disable", ButtonLore));
                ButtonLore.add(""); // add it back so there's always two lines of lore
            }
        }

        // Open the GUI
        GUIOwner.openInventory(GUI);
    }
}
