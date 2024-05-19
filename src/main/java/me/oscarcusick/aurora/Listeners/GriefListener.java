package me.oscarcusick.aurora.Listeners;

import me.oscarcusick.aurora.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;

import java.util.ArrayList;
import java.util.List;

public class GriefListener implements Listener {
    Plugin PluginInstance;
    int MaxRayTraceDist = 80;
    public GriefListener (Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    private Location RayTrace(Player P, int Distance) {
        Block TargetBlock = P.getTargetBlock(null, Distance);
        if (TargetBlock.getType().equals(Material.AIR)) {
            return null;
        }
        return TargetBlock.getLocation();
    }

    private Player RayTracePlayer(Player P, int Distance) {
        RayTraceResult RTR = P.getWorld().rayTraceEntities((P.getEyeLocation().add(P.getLocation().getDirection().multiply(3))), P.getEyeLocation().getDirection(), MaxRayTraceDist, 1); // starts ray-trace 3 block in-front of player
        if (RTR != null) {
            if (RTR.getHitEntity() instanceof Player) {
                return (Player)RTR.getHitEntity();
            }
        }
        return null;
    }

    public static List<Block> GetNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    @EventHandler // prevent player from dropping items
    public void PreventDropItem(PlayerDropItemEvent event) {
        if (!event.getPlayer().getPersistentDataContainer().has(new NamespacedKey(PluginInstance, "Griefing"))) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler // prevent player any inventory events
    public void PreventDropItem(InventoryCreativeEvent event) {
        if (!event.getWhoClicked().getPersistentDataContainer().has(new NamespacedKey(PluginInstance, "Griefing"))) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler // prevent player from die-ing
    public void PreventDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) { // make sure entity is player
            return;
        }
        Player EventPlayer = (Player)event.getEntity();
        if (!EventPlayer.getPersistentDataContainer().has(new NamespacedKey(PluginInstance, "Griefing"))) { // make sure they have the NBT
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void ItemUse(PlayerInteractEvent event) {
        if (!event.getPlayer().getPersistentDataContainer().has(new NamespacedKey(PluginInstance, "Griefing")) || event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        } // only right-clicking air remains at this point for verified greifers

        // check ray trace distance and result for null
        if (RayTrace(event.getPlayer(), MaxRayTraceDist) == null) {
            return;
        }


        event.setCancelled(true);
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        switch (event.getItem().getType()) {
            case ALLAY_SPAWN_EGG:
                event.getPlayer().getWorld().spawnEntity(RayTrace(event.getPlayer(), MaxRayTraceDist), EntityType.GIANT);
                GU.TellPlayer(event.getPlayer(), "Spawned a 'Giant'", ChatColor.DARK_RED + "" + ChatColor.BOLD + "GRIEF");
                return;
            case ENDERMAN_SPAWN_EGG:
                event.getPlayer().getWorld().spawnEntity(RayTrace(event.getPlayer(), MaxRayTraceDist), EntityType.WITHER);
                GU.TellPlayer(event.getPlayer(), "Spawned a 'Wither'");
                return;
            case NETHER_STAR:
                event.getPlayer().getWorld().createExplosion(RayTrace(event.getPlayer(), MaxRayTraceDist), 15);
                GU.TellPlayer(event.getPlayer(), "Summoned a small explosion");
                return;
            case END_CRYSTAL:
                event.getPlayer().getWorld().createExplosion(RayTrace(event.getPlayer(), MaxRayTraceDist), 100);
                GU.TellPlayer(event.getPlayer(), "Summoned a large explosion");
                return;
            case BLAZE_ROD:
                if (RayTracePlayer(event.getPlayer(), MaxRayTraceDist) != null) {
                    RayTracePlayer(event.getPlayer(), MaxRayTraceDist).setHealth(0);
                }
                return;
            case LAVA_BUCKET:
                {
                    List<Block> Blocks = GetNearbyBlocks(RayTrace(event.getPlayer(), MaxRayTraceDist), 6);
                    for (Block B : Blocks) {
                        B.setType(Material.LAVA);
                    }
                }
                return;
            case WATER_BUCKET:
                {
                    List<Block> Blocks = GetNearbyBlocks(RayTrace(event.getPlayer(), MaxRayTraceDist), 6);
                    for (Block B : Blocks) {
                        B.setType(Material.WATER);
                    }
                }
                return;
            case ENDER_EYE:
                for (Player P : PluginInstance.getServer().getOnlinePlayers()) {
                    if (!P.equals(event.getPlayer())) {
                        P.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, PotionEffect.INFINITE_DURATION, 1, true, false, false));
                    }
                }
                GU.TellPlayer(event.getPlayer(), "Gave all other players glowing effect");
                return;
            case TOTEM_OF_UNDYING:
                for (Player P : PluginInstance.getServer().getOnlinePlayers()) {
                    if (!P.equals(event.getPlayer())) {
                        P.setHealth(0);
                    }
                }
                GU.TellPlayer(event.getPlayer(), "Killed all other players");
                return;
            default:
                return;
        }


    }
}
