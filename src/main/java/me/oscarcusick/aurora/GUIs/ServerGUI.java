package me.oscarcusick.aurora.GUIs;

import me.oscarcusick.aurora.Utility.GUIUtility;
import me.oscarcusick.aurora.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class ServerGUI {
    Plugin PluginInstance;
    public ServerGUI(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public void OpenGUI(Player GUIOwner) {
        GUIUtility GUIUtil = new GUIUtility();
        GeneralUtility GU = new GeneralUtility(PluginInstance);
        int GUISize = 9*3; // how wide * how many rows the GUI will be
        Inventory GUI = Bukkit.createInventory(GUIOwner, GUISize, (GU.AuroraChatLogo + GU.Grey + ChatColor.BOLD + "[" + GU.Purple + ChatColor.BOLD + "Server" + GU.Grey + ChatColor.BOLD + "]"));
        ArrayList<String> GUIButtonLore = new ArrayList<>();

        // format GUI
        GUIUtil.FormatGUI(GUI, GUISize, GUIUtil.GenerateGUIButton(Material.GRAY_STAINED_GLASS_PANE, " "), GUIUtil.GenerateGUIButton(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));

        // Buttons
        GUIButtonLore.add(GU.Purple + "" + ChatColor.ITALIC + "Restarts the Operating System");
        GUI.setItem(3+9, GUIUtil.GenerateGUIButton(Material.BRICK, ChatColor.RED + "" + ChatColor.BOLD + "Operating System Restart", GUIButtonLore));

        GUIButtonLore.set(0, GU.Purple + "" + ChatColor.ITALIC + "Server freeze & crash");
        GUI.setItem(4+9, GUIUtil.GenerateGUIButton(Material.BLAZE_POWDER, ChatColor.RED + "" + ChatColor.BOLD + "Freezes the server", GUIButtonLore));

        GUIButtonLore.set(0, GU.Purple + "" + ChatColor.ITALIC + "Restarts the server");
        GUI.setItem(5+9, GUIUtil.GenerateGUIButton(Material.STRING, ChatColor.YELLOW + "" + ChatColor.BOLD + "Server restart", GUIButtonLore));

        // Open the GUI
        GUIOwner.openInventory(GUI);
    }
}
