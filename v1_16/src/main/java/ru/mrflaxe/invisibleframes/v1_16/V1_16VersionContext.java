package ru.mrflaxe.invisibleframes.v1_16;

import java.util.Random;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import ru.mrflaxe.invisibleframes.version.VersionContext;

public class V1_16VersionContext implements VersionContext {

    private final Random random = new Random();

    /**
     * Legacy Spigot path: no built-in {@code damageItemStack}, so the Unbreaking
     * roll and the durability write are done by hand. {@code Enchantment.DURABILITY}
     * is the pre-rename constant for Unbreaking.
     */
    @Override
    @SuppressWarnings("deprecation")
    public void damageShears(Player player, EquipmentSlot hand) {
        ItemStack shears = player.getInventory().getItem(hand);

        ItemMeta meta = shears.getItemMeta();
        Damageable shearsMeta = (Damageable) meta;

        int unbreakingLevel = meta.getEnchantLevel(Enchantment.DURABILITY);
        int chanceNotToReduce = 100 / (1 + unbreakingLevel);

        int dice = random.nextInt(100);

        // For each level of unbreaking chance not to reduce gets lower and lower.
        // When unbreaking level is 0 chance is 100%, when it is 1 it becomes 50%, etc.
        if (dice <= chanceNotToReduce) {
            int damage = shearsMeta.getDamage();
            shearsMeta.setDamage(damage + 1);
            shears.setItemMeta(meta);

            player.getInventory().setItem(hand, shears);
        }
    }
}
