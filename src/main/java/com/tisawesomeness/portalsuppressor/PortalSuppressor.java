package com.tisawesomeness.portalsuppressor;

import org.bukkit.plugin.java.JavaPlugin;

public final class PortalSuppressor extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new SpawnListener(), this);
    }

    @Override
    public void onDisable() {}

}
