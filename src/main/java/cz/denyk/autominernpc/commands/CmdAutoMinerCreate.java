package cz.denyk.autominernpc.commands;

import cz.denyk.autominernpc.Main;
import cz.denyk.autominernpc.system.AutoMiner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

            if(Main.getInstance().getDataStorage().hasAutoMiner(player.getUniqueId())) {
                player.sendMessage("You already have an autominer!");
                return false;
            }

            if(instance.getDataStorage().amountOfMiners(player.getUniqueId()) >= instance.getConfig().getInt("autominer.max")) {
                player.sendMessage("You already have maximum of miners!");
            }

            String name = args[1];

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
        }



        return true;
    }




}
