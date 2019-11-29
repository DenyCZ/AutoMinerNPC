package cz.denyk.autominernpc.utils;

import cz.denyk.autominernpc.Main;
import cz.denyk.autominernpc.system.EnumUpgrades;


public class Tools {
    private Main instance;

    public Tools() {
        instance = Main.getInstance();
    }

    public double getPriceForLevel(int level) {
        double totalxp = instance.getConfig().getDouble("base-price");
        int i = 1;
        while(i<level) {
            i++;
            totalxp *= 2;
        }

        return totalxp;
    }

    public int amountToDrop(EnumUpgrades upgrade, int lvl) {
        int amount = 0;

        if(upgrade == EnumUpgrades.KEY) {
            if(lvl < 10) {
                amount = 1;
            }
            if(lvl < 20) {
                amount = 2;
            }
            if(lvl >= 20) {
                amount = 3;
            }
        }

        if(upgrade == EnumUpgrades.TOKEN) {
            if(lvl < 10) {
                amount = 50;
            }
            if(lvl < 20) {
                amount = 100;
            }
            if(lvl >= 20) {
                amount = 200;
            }
        }

        return amount;
    }
}
