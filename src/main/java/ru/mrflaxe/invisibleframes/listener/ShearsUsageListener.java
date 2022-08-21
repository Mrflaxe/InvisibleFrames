package ru.mrflaxe.invisibleframes.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ShearsUsageListener implements Listener {
    
    @EventHandler
    public void onShearsUsage(PlayerInteractEntityEvent event) {
        Entity involved = event.getRightClicked();
        
        if(!involved.getType().equals(EntityType.ITEM_FRAME)) {
            return;
        }
        
        ItemFrame frame = (ItemFrame) involved;
        EquipmentSlot hand = event.getHand();
        Player player = event.getPlayer();
        
        ItemStack item = player.getInventory().getItem(hand);
        
        if(!item.getType().equals(Material.SHEARS)) {
            return;
        }
        
        if(frame.isVisible() == false) {
            return;
        }
        
        // This will avoid the player's attempts to put scissors in the frame
        // when he will make it invisible.
        event.setCancelled(true);
        
        // If player is not in creative mode will damage the shears by 1 value
        if(!player.getGameMode().equals(GameMode.CREATIVE)) {
            Damageable shearsMeta = (Damageable) item.getItemMeta();
            int damage = shearsMeta.getDamage();
            shearsMeta.setDamage(damage + 1);
            item.setItemMeta((ItemMeta) shearsMeta);
            
            player.getInventory().setItem(hand, item);
        }
        
        frame.setVisible(false);
        
        Location frameLocation = frame.getLocation();
        frameLocation.getWorld().playSound(frameLocation, Sound.ENTITY_SHEEP_SHEAR, 1, 1);
    }
    
    public void register(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
