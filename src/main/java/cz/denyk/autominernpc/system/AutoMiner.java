package cz.denyk.autominernpc.system;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AutoMiner {

    private String name;
    private Player owner;
    private Location pos1;
    private Location pos2;

    private boolean isWorking;

    public AutoMiner() {
        isWorking = false;
    }

    public void work() {
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
