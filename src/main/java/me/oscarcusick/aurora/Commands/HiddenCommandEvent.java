package me.oscarcusick.aurora.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.*;

public class HiddenCommandEvent extends Event {
    // custom event specific vars
    public Player commandSender; // who sent the hidden command
    public String command; // what the hidden command is. NOTE NOT COMMAND TYPE AS THAT WON'T WORK AFTER 30 SECONDS OF TESTING
    public Object[] strings; // what are the arguments of the hidden command

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public HiddenCommandEvent(Player commandSender, String command, Object[] strings){
        this.commandSender = commandSender;
        this.command = command;
        this.strings = strings;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
