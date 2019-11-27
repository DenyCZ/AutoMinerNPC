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

        config = new Configuration(getInstance(), "config");
        storage = new DataStorage();


        registerCommands();
    }

    @Override
    public void onDisable() {
        //Save all
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
}
