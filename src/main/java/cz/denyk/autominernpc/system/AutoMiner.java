package cz.denyk.autominernpc.system;

import cz.denyk.autominernpc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class AutoMiner {

    private Main instance;

    private String name;
    private Player owner;
    private Location pos1;
    private Location pos2;

    private double maxX, minX, maxZ, minZ, maxY, minY;

    private Location lastPosition;
    private Queue<Block> blockQueue = new ConcurrentLinkedQueue<>();
    private ArmorStand as;

    private Map<EnumUpgrades, Integer> upgrades = new HashMap<>();

    private boolean isWorking;

    public AutoMiner() {
        isWorking = false;

        instance = Main.getInstance();
        upgrades.put(EnumUpgrades.KEY, 1);
        upgrades.put(EnumUpgrades.TOKEN, 1);
    }

    public void addLevel(EnumUpgrades upgrade) {
        if(upgrades.containsKey(upgrade)) {
            upgrades.put(upgrade, upgrades.get(upgrade)+1);
        }
    }

    public void work() {

        if(isWorking) {
            return;
        }
        setWorking(true);

        if(pos1 == null || pos2 ==null) {
            return;
        }

        maxX = Math.max(pos1.getX(), pos2.getX());
        minX = Math.min(pos1.getX(), pos2.getX());
        maxY = Math.max(pos1.getY(), pos2.getY());
        minY = Math.min(pos1.getY(), pos2.getY());
        maxZ = Math.max(pos1.getZ(), pos2.getZ());
        minZ = Math.min(pos1.getZ(), pos2.getZ());

        Location startPosition = new Location(pos1.getWorld(), maxX, maxY, maxZ);

        if(lastPosition != null) {
            startPosition = lastPosition;
        }

        as = (ArmorStand)startPosition.getWorld().spawnEntity(startPosition.add(0,0.5,0), EntityType.ARMOR_STAND);
        as.setBasePlate(false);
        as.setArms(true);
        as.setGravity(false);
        as.setSmall(true);
        as.setVisible(true);
        as.setCanPickupItems(false);
        as.setCustomName(name);
        as.setCustomNameVisible(true);

        for (double y = maxY; y >= minY; y--) {
            for (double x = maxX; x >= minX; x--) {
                for (double z = maxZ; z >= minZ; z--) {
                    Block b = new Location(pos1.getWorld(), x, y, z).getBlock();
                    if (b.getType() == Material.AIR) {
                        continue;
                    }
                    blockQueue.add(b);
                }
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if(blockQueue.size() > 0) {
                    Block block = blockQueue.poll();
                    as.teleport(block.getLocation().add(0.5,1,0.5));
                    lastPosition = as.getLocation();
                    dropBlock(block);
                } else {
                    as.remove();
                    cancel();
                }

            }
        }.runTaskTimer(instance, 0, 10);

        setWorking(false);


    }

    private void dropBlock(Block block) {
        int random = ThreadLocalRandom.current().nextInt(100);

        if(getLevel(EnumUpgrades.TOKEN) < random) {
            String command = String.format("te add %s %d", getOwner().getName(), instance.getTools().amountToDrop(EnumUpgrades.TOKEN, getLevel(EnumUpgrades.TOKEN)));
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
        }

        random = ThreadLocalRandom.current().nextInt(100);

        if(getLevel(EnumUpgrades.KEY) < random) {
            String command = String.format("cc give p Mine %d %s", instance.getTools().amountToDrop(EnumUpgrades.KEY, getLevel(EnumUpgrades.KEY)), getOwner().getName());
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
        }

        block.breakNaturally();
    }

    //Getters

    public int getLevel(EnumUpgrades upgrade) {
        return upgrades.containsKey(upgrade) ? upgrades.get(upgrade) : 0;
    }

    public void setName(String value) {
        name = value;
    }

    public String getName() {
        return name;
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos1(Location value) {
        pos1 = value;
    }

    public void setPos2(Location value) {
        pos2 = value;
    }

    public boolean getWorking() {
        return isWorking;
    }

    public void setWorking(boolean value) {
        isWorking = value;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;

    }

}
