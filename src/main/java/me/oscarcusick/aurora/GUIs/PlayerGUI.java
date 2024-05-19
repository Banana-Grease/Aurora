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
        Player TargetPlayer = GU.StringContainsPlayerName(TargetName);

        // Empty Slots
        ItemStack EmptySlot = GUIUtil.GenerateGUIButton(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ");
        ItemStack DarkEmptySlot = GUIUtil.GenerateGUIButton(Material.GRAY_STAINED_GLASS_PANE, " ");

        // format GUI
        GUIUtil.FormatGUI(GUI, GUISize, DarkEmptySlot, EmptySlot);

        // Buttons

        GUIButtonLore.add(GU.Purple + "" + ChatColor.ITALIC + "Name: " + GU.Gold + ChatColor.ITALIC + TargetPlayer.getName()); // players name
        GUIButtonLore.add(GU.Purple + "" + ChatColor.ITALIC + "World: " + GU.Gold + ChatColor.ITALIC + TargetPlayer.getLocation().getWorld().getName()); // players world
        GUIButtonLore.add(GU.Purple + "" + ChatColor.ITALIC + "Location: " + GU.Gold + ChatColor.ITALIC + (int)TargetPlayer.getLocation().getX() + GU.Purple + ", " + GU.Gold + (int)TargetPlayer.getLocation().getZ()); // players location (X, Z)
        GUIButtonLore.add(GU.Purple + "" + ChatColor.ITALIC + "IPV4: " + GU.Gold + ChatColor.ITALIC + TargetPlayer.getAddress()); // players IP
        GUIButtonLore.add(GU.Purple + "" + ChatColor.ITALIC + "Locale: " + GU.Gold + ChatColor.ITALIC + TargetPlayer.getLocale()); // players locale
        GUIButtonLore.add(GU.Purple + "" + ChatColor.ITALIC + "Ping: " + GU.Gold + ChatColor.ITALIC + TargetPlayer.getPing()); // players ping
        GUI.setItem(0, GUIUtil.GeneratePlayerHead(TargetPlayer, (ChatColor.GREEN + "" + ChatColor.BOLD + "Player Information"), GUIButtonLore));

        GUI.setItem(2+9, GUIUtil.GenerateGUIButton(Material.DRAGON_HEAD, (ChatColor.RED + "" + ChatColor.BOLD + "Anti Ban")));

        GUI.setItem(3+9, GUIUtil.GenerateGUIButton(Material.WITHER_SKELETON_SKULL, (ChatColor.RED + "" + ChatColor.BOLD + "Anti Kick")));

        GUI.setItem(4+9, GUIUtil.GenerateGUIButton(Material.SKELETON_SKULL, (ChatColor.RED + "Kill")));

        GUI.setItem(5+9, GUIUtil.GenerateGUIButton(Material.IRON_BOOTS, (ChatColor.YELLOW + "Kick")));

        GUI.setItem(6+9, GUIUtil.GenerateGUIButton(Material.BLAZE_POWDER, (ChatColor.YELLOW + "Crash")));

        // Open the GUI
        GUIOwner.openInventory(GUI);
    }
}
