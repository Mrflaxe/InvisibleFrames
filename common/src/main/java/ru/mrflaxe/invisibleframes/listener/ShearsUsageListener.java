package ru.mrflaxe.invisibleframes.listener;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
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

import ru.mrflaxe.invisibleframes.version.VersionContext;

public class ShearsUsageListener implements Listener {

    private final VersionContext context;
    private final Random random = new Random();

    public ShearsUsageListener(VersionContext context) {
        this.context = context;
    }

    @EventHandler
    public void onShearsUsage(PlayerInteractEntityEvent event) {
        Entity involved = event.getRightClicked();

        // GlowItemFrame extends ItemFrame, so a single instanceof check covers both
        // the regular and the glowing variant without referencing version-specific
        // entity types that may not exist on older API.
        if (!(involved instanceof ItemFrame)) {
            return;
        }

        ItemFrame frame = (ItemFrame) involved;
        EquipmentSlot hand = event.getHand();
        Player player = event.getPlayer();

        ItemStack item = player.getInventory().getItem(hand);

        if (item == null || !item.getType().equals(Material.SHEARS)) {
            return;
        }

        if (!frame.isVisible()) {
            return;
        }

        // This will avoid the player's attempts to put scissors in the frame
        // when he will make it invisible.
        event.setCancelled(true);

        // If player is not in creative mode will damage the shears by 1 value
        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            reduceDurability(player, item, hand);
        }

        frame.setVisible(false);
        updatePlayerStatistic(player, item);

        Location frameLocation = frame.getLocation();
        frameLocation.getWorld().playSound(frameLocation, Sound.ENTITY_SHEEP_SHEAR, 1, 1);
    }

    private void reduceDurability(Player player, ItemStack shears, EquipmentSlot hand) {
        ItemMeta meta = shears.getItemMeta();
        Damageable shearsMeta = (Damageable) meta;

        // If the shears have unbreaking enchantment should to handle it
        // The chance not to reduce is range of int values. If random 'dice' gets in
        // this range durability will be decreased.
        Enchantment unbreaking = context.getUnbreakingEnchantment();
        int unbreakingLevel = unbreaking == null ? 0 : meta.getEnchantLevel(unbreaking);
        int chanceNotToReduce = 100 / (1 + unbreakingLevel);

        int dice = random.nextInt(100);

        // For each level of unbreaking chance not to reduce will gets lower and lower
        // When unbreaking level is 0 chance is 100%
        // When unbreaking level is 1 chance become 50% and etc.
        if (dice <= chanceNotToReduce) {
            int damage = shearsMeta.getDamage();
            shearsMeta.setDamage(damage + 1);
            shears.setItemMeta(meta);

            player.getInventory().setItem(hand, shears);
        }
    }

    private void updatePlayerStatistic(Player player, ItemStack shears) {
        int itemUseCount = player.getStatistic(Statistic.USE_ITEM, shears.getType());
        itemUseCount++;
        player.setStatistic(Statistic.USE_ITEM, shears.getType(), itemUseCount);
    }

    public void register(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}
