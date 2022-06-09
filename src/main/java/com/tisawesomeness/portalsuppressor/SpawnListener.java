package com.tisawesomeness.portalsuppressor;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.util.BoundingBox;

import java.util.EnumSet;

public class SpawnListener implements Listener {

    private static final EnumSet<SpawnReason> BLOCKED_SPAWN_REASONS = EnumSet.of(
            SpawnReason.NATURAL, SpawnReason.JOCKEY, SpawnReason.SPAWNER, SpawnReason.VILLAGE_DEFENSE,
            SpawnReason.VILLAGE_INVASION, SpawnReason.REINFORCEMENTS, SpawnReason.RAID, SpawnReason.PATROL
    );

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (!BLOCKED_SPAWN_REASONS.contains(event.getSpawnReason())) {
            return;
        }
        if (isGoingToSpawnInPortal(event.getEntity())) {
            dont(event);
        }
    }
    private static boolean isGoingToSpawnInPortal(LivingEntity entity) {
        World world = entity.getWorld();
        BoundingBox box = entity.getBoundingBox();
        int xa = (int) box.getMinX();
        int xb = (int) Math.ceil(box.getMaxX());
        int ya = (int) box.getMinY();
        int yb = (int) Math.ceil(box.getMaxY());
        int za = (int) box.getMinZ();
        int zb = (int) Math.ceil(box.getMaxZ());
        for (int x = xa; x <= xb; x++) {
            for (int y = ya; y <= yb && y < world.getMaxHeight(); y++) {
                for (int z = za; z <= zb; z++) {
                    Material mat = world.getBlockAt(x, y, z).getType();
                    if (mat == Material.NETHER_PORTAL || mat == Material.END_PORTAL) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private static void dont(CreatureSpawnEvent event) {
        event.setCancelled(true);
    }

}
