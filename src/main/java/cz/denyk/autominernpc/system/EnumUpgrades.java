package cz.denyk.autominernpc.system;

public enum EnumUpgrades {
    KEY("key"),
    TOKEN("token");

    private final String upgradeName;

    EnumUpgrades(String name) {
        upgradeName = name;
    }

    public String getUpgradeName() {
        return upgradeName;
    }
}

