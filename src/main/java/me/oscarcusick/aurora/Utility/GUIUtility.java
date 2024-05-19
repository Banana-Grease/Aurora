package me.oscarcusick.aurora.Utility;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class GUIUtility {
    // Generates a GUI Button with lore
    public ItemStack GenerateGUIButton(Material ItemType, String Name, ArrayList<String> Lore) {
        ItemStack ResultButton = new ItemStack(ItemType);
        ItemMeta ResultButtonMeta = ResultButton.getItemMeta();
        ResultButtonMeta.setDisplayName(Name);
        ResultButtonMeta.setLore(Lore);
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES); // remove armour stats, etc.
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM); // remove armour stats, etc.
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_DYE); // remove armour stats, etc.
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_DESTROYS); // remove armour stats, etc.
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS); // remove armour stats, etc.
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON); // remove armour stats, etc.
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); // remove armour stats, etc.
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE); // remove armour stats, etc.
        ResultButton.setItemMeta(ResultButtonMeta);
        return ResultButton;
    }

    // Generates a GUI Button with no lore
    public ItemStack GenerateGUIButton(Material ItemType, String Name) {
        return GenerateGUIButton(ItemType, Name, new ArrayList<>());
    }

    public ItemStack GeneratePlayerHead(Player Player, String ItemName, ArrayList<String> Lore) {
        ItemStack PlayerHead = GenerateGUIButton(Material.PLAYER_HEAD, ItemName, Lore);
        SkullMeta HeadMeta = (SkullMeta) PlayerHead.getItemMeta();
        HeadMeta.setOwningPlayer(Player);
        PlayerHead.setItemMeta(HeadMeta);
        return PlayerHead;
    }

    // returns a nicely formatted gui with empty slots and a border
    public Inventory FormatGUI(Inventory GUI, int GUISize, ItemStack BorderItem, ItemStack EmptySlot) {
        //set all unset slots to EmptySlot or grey empty slot if it's an outermost slot
        for (int i = 0; i < GUI.getSize(); i++) {
            if (i < 9 || (i > 9*((GUISize/9)-1)-1) || i%9==0) {
                GUI.setItem(i, BorderItem);
            } else {
                GUI.setItem(i, EmptySlot);
            }
        }
        GUI.setItem(17, BorderItem); // these two are here because the left side is too hard. everything else autoconfigures to any GUI Size.
        GUI.setItem(26, BorderItem);

        return GUI;
    }
}
