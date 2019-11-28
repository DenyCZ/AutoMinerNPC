package cz.denyk.autominernpc.commands;

import cz.denyk.autominernpc.Main;
import cz.denyk.autominernpc.system.AutoMiner;
import cz.denyk.autominernpc.system.EnumUpgrades;
import cz.denyk.autominernpc.utils.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Set;

public class CmdAutoMinerCreate implements CommandExecutor {

    private Main instance;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player) || args.length < 1) {
            commandSender.sendMessage("Please use /autominer help");
            return false;
        }

        Player player = (Player)commandSender;
        instance = Main.getInstance();


        if(args[0].equalsIgnoreCase("create")) {

            if(args.length < 2) {
                player.sendMessage("Usage is /autominer create {name}");
                return false;
            }

            if(instance.getDataStorage().amountOfMiners(player.getUniqueId()) >= instance.getConfig().getInt("max-autominers")) {
                player.sendMessage("You already have maximum of miners!");
                return false;
            }

            String name = args[1];

            if(instance.getDataStorage().hasAutoMiner(player.getUniqueId(), name)) {
                player.sendMessage("An autominer with this name already exists");
                return false;
            }

            AutoMiner miner = new AutoMiner();
            miner.setName(name);
            miner.setOwner(player);

            instance.getDataStorage().addAutoMiner(player.getUniqueId(), miner);
            player.sendMessage("An autominer has been added");
            player.sendMessage(String.valueOf(instance.getDataStorage().amountOfMiners(player.getUniqueId())));
        } else if(args[0].equalsIgnoreCase("setpos1")) {
            if(args.length < 2) {
                player.sendMessage("Usage is /autominer setpos1 {name}");
                return false;
            }

            String name = args[1];

            AutoMiner miner = instance.getDataStorage().getAutoMiner(player.getUniqueId(), name);
            if(miner == null) {
                player.sendMessage("This miner doesnt exist");
                return false;
            }

            miner.setPos1(player.getLocation());
            player.sendMessage("Position 1 succesfully set");

        } else if(args[0].equalsIgnoreCase("setpos2")) {
            if(args.length < 2) {
                player.sendMessage("Usage is /autominer setpos2 {name}");
                return false;
            }

            String name = args[1];

            AutoMiner miner = instance.getDataStorage().getAutoMiner(player.getUniqueId(), name);
            if(miner == null) {
                player.sendMessage("This miner doesnt exist");
                return false;
            }

            if(miner.getPos1() == null) {
                player.sendMessage("Please set pos1 first.");
                return false;
            }

            miner.setPos2(player.getLocation());
            player.sendMessage("Position 2 succesfully set");

        } else if(args[0].equalsIgnoreCase("work")) {
            if(args.length < 2) {
                player.sendMessage("Usage is /autominer work {name}");
                return false;
            }

            String name = args[1];

            AutoMiner miner = instance.getDataStorage().getAutoMiner(player.getUniqueId(), name);
            if(miner == null) {
                player.sendMessage("This miner doesnt exist");
                return false;
            }

            if(miner.getPos1() == null || miner.getPos2() == null) {
                player.sendMessage("Miner has no setted positions!");
                return false;
            }

            if(miner.getWorking()) {
                player.sendMessage("Miner is already working");
                return false;
            }

            miner.work();

        } else if(args[0].equalsIgnoreCase("stopwork")) {
            if(args.length < 2) {
                player.sendMessage("Usage is /autominer stopwork {name}");
                return false;
            }

            String name = args[1];

            AutoMiner miner = instance.getDataStorage().getAutoMiner(player.getUniqueId(), name);
            if(miner == null) {
                player.sendMessage("This miner doesnt exist");
                return false;
            }

            if(!miner.getWorking()) {
                player.sendMessage("This miner is not working");
                return false;
            }

            miner.setWorking(false);
        } else if(args[0].equalsIgnoreCase("list")) {
            player.sendMessage(instance.getDataStorage().getMiners(player.getUniqueId()));
        } else if(args[0].equalsIgnoreCase("upgrade")) {
            if(args.length < 3) {
                player.sendMessage("Usage is /autominer upgrade {name} {upgradename}");
                return false;
            }

            String name = args[1];

            AutoMiner miner = instance.getDataStorage().getAutoMiner(player.getUniqueId(), name);
            if(miner == null) {
                player.sendMessage("This miner doesnt exist");
                return false;
            }

            String upgradeName = args[2];

            EnumUpgrades upgrade = null;

            for (EnumUpgrades enumUpgrade : EnumUpgrades.values()) {
                if(enumUpgrade.getUpgradeName().equalsIgnoreCase(upgradeName)) {
                    upgrade = enumUpgrade;
                    break;
                }
            }

            if(upgrade == null) {
                player.sendMessage("Upgrade was not found, only possible updates are KEY / TOKEN");
                return false;
            }

            int level = miner.getLevel(upgrade)+1;
            System.out.println(level);

            if(level > instance.getConfig().getInt("upgrades."+upgradeName.toLowerCase()+"-max-level")) {
                player.sendMessage("This is already on maximum level");
                return false;
            }

            if(instance.getConfig().getConfigurationSection("upgrades."+upgradeName.toLowerCase()+"-price").contains("level-"+level)) {
                int price = instance.getConfig().getInt("upgrades."+upgradeName.toLowerCase()+"-price.level-"+level);

                //If player has enough money
                //Withdraw money

                miner.addLevel(upgrade);

            }

        }



        return true;
    }




}
