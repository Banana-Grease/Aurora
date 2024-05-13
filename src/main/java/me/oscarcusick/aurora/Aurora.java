package me.oscarcusick.aurora;

import me.oscarcusick.aurora.Commands.Executors.DoSecurityPrefixCommandExecutor;
import me.oscarcusick.aurora.Commands.Executors.ServerSpecCommandExecutor;
import me.oscarcusick.aurora.Listeners.ChatInterfaceListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Aurora extends JavaPlugin {

    @Override
    public void onEnable() {
        // --- Register Listeners
        getServer().getPluginManager().registerEvents(new ChatInterfaceListener(this), this);
        // Hidden commands
        getServer().getPluginManager().registerEvents(new DoSecurityPrefixCommandExecutor(this), this);
        getServer().getPluginManager().registerEvents(new ServerSpecCommandExecutor(this), this);

        // Register Custom Events
        getServer().getPluginManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
