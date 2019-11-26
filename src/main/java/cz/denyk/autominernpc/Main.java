package cz.denyk.autominernpc;

import cz.denyk.autominernpc.commands.CmdAutoMinerCreate;
import cz.denyk.autominernpc.system.DataStorage;
import cz.denyk.autominernpc.utils.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {

    private static Main instance;
    private Configuration config;
    private DataStorage storage;


    @Override
    public void onEnable() {
        instance = this;

        if(getServer().getPluginManager().getPlugin("Citizens") == null || getServer().getPluginManager().getPlugin("Citizens").isEnabled() == false) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        config = new Configuration(getInstance(), "config");
        storage = new DataStorage();


        registerCommands();
    }

    @Override
    public void onDisable() {

    }


    public void registerCommands() {
        this.getCommand("autominer").setExecutor(new CmdAutoMinerCreate());
    }


    //Getters
    public static Main getInstance() {
        return instance;
    }

    public FileConfiguration getConfig() {
        return config.getYaml();
    }

    public Configuration getCfg() {
        return config;
    }

    public DataStorage getDataStorage() {
        return storage;
    }


    //User uses command /autominer create
    //User must define position 1 and 2 /autominer setpos1 /autominer setpos2
    //NPC spawns at first location and starts mining towards second position
}
