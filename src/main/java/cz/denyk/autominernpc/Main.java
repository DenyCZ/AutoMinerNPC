package cz.denyk.autominernpc;

import cz.denyk.autominernpc.commands.CmdAutoMinerCreate;
import cz.denyk.autominernpc.system.DataStorage;
import cz.denyk.autominernpc.utils.Configuration;
import cz.denyk.autominernpc.utils.Tools;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private static Main instance;
    private Configuration config;
    private DataStorage storage;
    private Tools tools;
    private Economy economy;

    @Override
    public void onEnable() {
        instance = this;

        tools = new Tools();
        config = new Configuration(getInstance(), "config");
        storage = new DataStorage();
        registerCommands();


        if(!checkDependencies()) {
            getLogger().log(Level.SEVERE, "Some dependency is missing! Please check if you have installed Vault, TokenManager and CrazyCrates!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if(!setupEconomy()) {
            getLogger().log(Level.SEVERE, "Could not setup economy!");

        }

        System.out.println(economy);


    }

    @Override
    public void onDisable() {
        //Disable all miners
        //Save all to flatfile
        getLogger().log(Level.WARNING, "vypinam");
    }

    private boolean checkDependencies() {

        if(getServer().getPluginManager().getPlugin("Vault") == null
        || getServer().getPluginManager().getPlugin("TokenManager") == null //TODO: Set to tokenenchant
        || getServer().getPluginManager().getPlugin("CrazyCrates") == null) {
            return false; //TODO: Return false
        }

        return true;
    }

    private boolean setupEconomy()
    {
        getServer().getServicesManager().register(Economy.class, null, this, ServicePriority.Highest);
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
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

    public Economy getEcon() {
        return economy;
    }

    public Tools getTools() {
        return tools;
    }

    public DataStorage getDataStorage() {
        return storage;
    }
}
