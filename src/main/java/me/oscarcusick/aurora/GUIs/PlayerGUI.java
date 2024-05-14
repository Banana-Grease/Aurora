package me.oscarcusick.aurora.GUIs;

import net.md_5.bungee.api.ChatColor;
import me.oscarcusick.aurora.Utility.GUIUtility;
import me.oscarcusick.aurora.Utility.GeneralUtility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class PlayerGUI {
    Plugin PluginInstance;
    public PlayerGUI(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public void OpenGUI(Player GUIOwner, String TargetName) {
        GUIUtility GUIUtil = new GUIUtility();
        GeneralUtility GU = new GeneralUtility(PluginInstance);
        int GUISize = 9*3; // how wide * how many rows the GUI will be
        Inventory GUI = Bukkit.createInventory(GUIOwner, GUISize, (GU.AuroraChatLogo + GU.Grey + ChatColor.BOLD + "[" + ChatColor.RED + TargetName + GU.Grey + ChatColor.BOLD + "]"));
        ArrayList<String> GUIButtonLore = new ArrayList<>();

        // Empty Slots
        ItemStack EmptySlot = GUIUtil.GenerateGUIButton(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ");
        ItemStack DarkEmptySlot = GUIUtil.GenerateGUIButton(Material.GRAY_STAINED_GLASS_PANE, " ");

        // format GUI
        GUIUtil.FormatGUI(GUI, GUISize, DarkEmptySlot, EmptySlot);

        // Buttons
        GUI.setItem(2+9, GUIUtil.GenerateGUIButton(Material.DRAGON_HEAD, (ChatColor.RED + "" + ChatColor.BOLD + "Anti Ban")));

        GUI.setItem(3+9, GUIUtil.GenerateGUIButton(Material.WITHER_SKELETON_SKULL, (ChatColor.RED + "" + ChatColor.BOLD + "Anti Kick")));

        GUI.setItem(4+9, GUIUtil.GenerateGUIButton(Material.SKELETON_SKULL, (ChatColor.RED + "Kill")));

        GUI.setItem(5+9, GUIUtil.GenerateGUIButton(Material.IRON_BOOTS, (ChatColor.YELLOW + "Kick")));

        GUI.setItem(6+9, GUIUtil.GenerateGUIButton(Material.BLAZE_POWDER, (ChatColor.YELLOW + "Crash")));

        // Open the GUI
        GUIOwner.openInventory(GUI);
    }
}
