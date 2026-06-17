package ru.mrflaxe.invisibleframes.listener;

import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Reveals an invisible frame once its item is knocked out of it.
 */
@NoArgsConstructor
public class FrameItemDropListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFrameHit(EntityDamageByEntityEvent event) {
        Entity damaged = event.getEntity();

        if (!(damaged instanceof ItemFrame)) {
            return;
        }

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        ItemFrame frame = (ItemFrame) damaged;

        if (frame.isVisible()) {
            return;
        }

        ItemStack content = frame.getItem();
        if (content.getType() == Material.AIR) {
            return;
        }

        frame.setVisible(true);
    }

    public void register(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}
