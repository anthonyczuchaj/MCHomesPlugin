package org.main.mchomesplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.main.mchomesplugin.commands.HomeCommands;
import java.io.File;

public final class MCHomesPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        createConfig();
        this.saveConfig();
        HomeCommands homeCommands = new HomeCommands(this.getConfig());
        getCommand("sethome").setExecutor(homeCommands);
        getCommand("home").setExecutor(homeCommands);
        getCommand("deletehome").setExecutor(homeCommands);
        getCommand("homes").setExecutor(homeCommands);
    }

    private void createConfig() {
        // Define the path to the config file in the plugin's data folder
        File configFile = new File(getDataFolder(), "config.yml");

        // Check if the config file already exists
        if (!configFile.exists()) {
            // If it doesn't exist, create it and populate with defaults
            configFile.getParentFile().mkdirs(); // Ensure the folder exists
        }
    }

}
