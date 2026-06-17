package ru.mrflaxe.invisibleframes.listener;

import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Reveals an invisible item frame once it loses its item but survives as an entity.
 */
@NoArgsConstructor
public class FrameItemDropListener implements Listener {

    private JavaPlugin plugin;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFrameDamage(EntityDamageEvent event) {
        Entity damaged = event.getEntity();

        if (!(damaged instanceof ItemFrame)) {
            return;
        }

        ItemFrame frame = (ItemFrame) damaged;

        if (frame.isVisible()) {
            return;
        }

        if (frame.getItem().getType() == Material.AIR) {
            return;
        }

        Bukkit.getScheduler().runTask(plugin, () -> {
            if (!frame.isValid()) {
                return;
            }

            if (frame.getItem().getType() != Material.AIR) {
                return;
            }

            frame.setVisible(true);
        });
    }

    public void register(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}
