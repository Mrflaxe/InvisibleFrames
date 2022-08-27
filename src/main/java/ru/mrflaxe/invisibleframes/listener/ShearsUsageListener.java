package ru.mrflaxe.invisibleframes.listener;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
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
import org.bukkit.plugin.java.JavaPlugin;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShearsUsageListener implements Listener {
    
    private final JavaPlugin plugin;
    
    @EventHandler
    public void onShearsUsage(PlayerInteractEntityEvent event) {
        Entity involved = event.getRightClicked();
        
        if(!involved.getType().equals(EntityType.ITEM_FRAME)) {
            return;
        }
        
        // Version check helps to avoid loading not existing classes
        // getBukkitVersion returns version like "1.16.5-R0.1-SNAPSHOT"
        String bukkitVersion = plugin.getServer().getBukkitVersion();
        // Cutting out only second number after the first dot
        // From previous example will return "16"
        String secondNumber = bukkitVersion.substring(2, 4);
        
        int version = Integer.parseInt(secondNumber);
        
        if(version > 16) {
            if(!involved.getType().equals(EntityType.GLOW_ITEM_FRAME)) {
                return;
            }
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
            reduceDurability(player, item, hand);
        }
        
        frame.setVisible(false);
        
        Location frameLocation = frame.getLocation();
        frameLocation.getWorld().playSound(frameLocation, Sound.ENTITY_SHEEP_SHEAR, 1, 1);
    }
    
    private void reduceDurability(Player player, ItemStack shears, EquipmentSlot hand) {
        Damageable shearsMeta = (Damageable) shears.getItemMeta();
        
        // If the shears have unbreaking enchantment should to handle it
        // The chance not to reduce is range of int values. If random 'dice' gets in
        // this range durability will be decreased.
        int unbreakingLevel = shears.getItemMeta().getEnchantLevel(Enchantment.DURABILITY);
        int chanceNotToReduce = 100 / (1 + unbreakingLevel);
        
        Random random = new Random();
        int dice = random.nextInt(100);
        
        // For each level of unbreaking chance not to reduce will gets lower and lower
        // When unbreaking level is 0 chance is 100%
        // When unbreaking level is 1 chance become 50% and etc.
        if(dice <= chanceNotToReduce) {
            int damage = shearsMeta.getDamage();
            shearsMeta.setDamage(damage + 1);
            shears.setItemMeta(shearsMeta);
            
            player.getInventory().setItem(hand, shears);
        }
    }
    
    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
