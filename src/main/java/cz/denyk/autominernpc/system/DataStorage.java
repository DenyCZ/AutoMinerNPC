package cz.denyk.autominernpc.system;

import java.util.*;

public class DataStorage {

    public Map<UUID, List<AutoMiner>> minerOwners = new HashMap<>();

    public DataStorage() {

    }

    public void addAutoMiner(UUID playerUUID, AutoMiner miner) {
        if(minerOwners.containsKey(playerUUID)) {
            List<AutoMiner> actualMiners = minerOwners.get(playerUUID);
            actualMiners.add(miner);
            minerOwners.put(playerUUID, actualMiners);
        } else {
            List<AutoMiner> miners = new ArrayList<>();
            miners.add(miner);
            minerOwners.put(playerUUID, miners);
        }
    }

    public boolean hasAutoMiner(UUID playerUUID) {
        return minerOwners.containsKey(playerUUID);
    }

    public AutoMiner getAutoMiner(UUID playerUUID, String nameOfMiner) {
        if(!minerOwners.containsKey(playerUUID)) {
            return null;
        }

        List<AutoMiner> miners = minerOwners.get(playerUUID);
        for (AutoMiner miner : miners) {
            if(miner.getName().equalsIgnoreCase(nameOfMiner)) {
                return miner;
            }
        }

        return null;
    }

    public int amountOfMiners(UUID playerUUID) {
        if(!minerOwners.containsKey(playerUUID)) {
            return 0;
        }
        return minerOwners.get(playerUUID).size();
    }

    public String getMiners(UUID playerUUID) {
        if(!minerOwners.containsKey(playerUUID)) {
            return "";
        }

        String ret = "";

        List<AutoMiner> miners = minerOwners.get(playerUUID);
        for (AutoMiner miner: miners) {
            ret += miner.getName() + ";";
        }

        return ret;
    }
}
