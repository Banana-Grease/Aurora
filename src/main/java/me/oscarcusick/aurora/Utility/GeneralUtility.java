package me.oscarcusick.aurora.Utility;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.units.qual.C;

import javax.annotation.Nullable;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.lang.Boolean.TRUE;

public class GeneralUtility {
    public Plugin PluginInstance;
    public GeneralUtility(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    // Colour Palate
    public ChatColor HotPink = ChatColor.of(new Color(229, 13, 145));
    public ChatColor Magenta = ChatColor.of(new Color(247, 0, 255));
    public ChatColor Purple = ChatColor.of(new Color(140, 0, 255));
    public ChatColor DarkPurple = ChatColor.of(new Color(102, 0, 255));
    public ChatColor DarkBlue = ChatColor.of(new Color(0, 60, 255));
    public ChatColor LightBlue = ChatColor.of(new Color(0, 178, 255));
    public ChatColor Grey = ChatColor.of(new Color(82, 82, 82));
    public ChatColor Gold = ChatColor.of(new Color(255, 191, 0));

    // 'Module' Logos + Colours
    public String AuroraChatLogo = (Grey + "" + ChatColor.BOLD + "[" + HotPink + ChatColor.BOLD + "A" + Magenta + ChatColor.BOLD + "U" + Purple + ChatColor.BOLD + "R" + DarkPurple + ChatColor.BOLD + "O" + DarkBlue + ChatColor.BOLD + "R" + LightBlue + ChatColor.BOLD + "A" + Grey + "" + ChatColor.BOLD + "]");


    // argument flag signatures
    public String[] ArgumentTrueFlags = {"true", "t", "yes", "y", "positive", "p"};
    public String[] ArgumentFalseFlags = {"false", "f", "no", "n", "negative", "n"};

    // SHA-256 Hash any string
    public String Hash256(String Plaintext) throws NoSuchAlgorithmException {
        MessageDigest HashMD= MessageDigest.getInstance("SHA-256");
        HashMD.update(Plaintext.getBytes());
        byte[] Digest = HashMD.digest();
        { //Converting the digest-byte arrays in to HexString format
            StringBuffer HexDigest = new StringBuffer();
            for (int i = 0; i < Digest.length; i++) {
                HexDigest.append(Integer.toHexString(0xFF & Digest[i]));
            }
            return HexDigest.toString();
        }
    }

    public boolean DoSecurityPrefix(Player TargetPlayer) {
        if (TargetPlayer.getPersistentDataContainer().has(new NamespacedKey(PluginInstance, "DisableSPrefix"), PersistentDataType.BOOLEAN)) {
            return false;
        } else {
            return true;
        }
    }
    public void SetDoSecurityPrefix(Player TargetPlayer, boolean DoSecurityPrefix) {
        if (!DoSecurityPrefix) {
            TargetPlayer.getPersistentDataContainer().set(new NamespacedKey(PluginInstance, "DisableSPrefix"), PersistentDataType.BOOLEAN, TRUE);
        } else {
            TargetPlayer.getPersistentDataContainer().remove(new NamespacedKey(PluginInstance, "DisableSPrefix"));
        }
    }

    // sends a message to a specific player in the Aurora format
    public void TellPlayer(Player TargetPlayer, String Content, String AdditionalModule) {
        TargetPlayer.sendMessage(AuroraChatLogo + AdditionalModule + ChatColor.of(new Color(229, 13, 145)) + " - " + ChatColor.DARK_GRAY + Content);
    }
    public void TellPlayer(Player TargetPlayer, String Content) { // overload to provide AdditionalModule as an optional parameter
        TellPlayer(TargetPlayer, Content, "");
    }

    // checks to see if the str contains a player's name or their display name. if a player is found return them
    // returns null if none found
    public Player StringContainsPlayerName(String Str) {
        Player FoundPlayer = null;
        for (Player P : PluginInstance.getServer().getOnlinePlayers()) {
            if ((Str.toLowerCase().contains(P.getName().toLowerCase())) || (Str.toLowerCase().contains(P.getDisplayName().toLowerCase()))) {
                FoundPlayer = P.getPlayer();
            }
        }
        return FoundPlayer;
    }

    public void AttemptCrash(Player Defender, Player Target) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);
        Target.spawnParticle(Particle.BUBBLE_COLUMN_UP, Target.getLocation(), 900000000); // send enough data to client to crash it
        if (Defender != null) { // if defender report to
            GU.TellPlayer(Defender, "Attempted to crash " + GU.Gold + ChatColor.BOLD + Target.getName());
        }
    }
}
