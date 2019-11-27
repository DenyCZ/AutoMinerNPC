package cz.denyk.autominernpc.system;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AutoMiner {

    private String name;
    private Player owner;
    private Location pos1;
    private Location pos2;

    private double maxX, minX, maxZ, minZ, maxY, minY;

    private Location lastPosition;
    private Queue<Block> blockQueue = new ConcurrentLinkedQueue<>();

    private boolean isWorking;

    public AutoMiner() {
        isWorking = false;
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

        ArmorStand as;

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

        while(blockQueue.size() > 0) {
            Block b = blockQueue.poll();
            System.out.println("Trying to break " + b.getLocation().toString());
            b.breakNaturally();
        }

        setWorking(false);


    }


    //Getters
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
