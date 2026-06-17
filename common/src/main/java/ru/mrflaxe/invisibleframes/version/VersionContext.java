package ru.mrflaxe.invisibleframes.version;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

/**
 * Version-specific facade. Each supported Minecraft version range provides
 * its own implementation; the entry-point module picks the right one at runtime.
 */
public interface VersionContext {

    /**
     * Applies one point of wear to the item the player holds in {@code hand}.
     *
     * <p>Modern (Paper) contexts delegate to the server's own item-damage routine,
     * which already accounts for the Unbreaking enchantment and fires the matching
     * item-damage / item-break events. The legacy (Spigot) context reproduces that
     * behaviour by hand.
     */
    void damageShears(Player player, EquipmentSlot hand);
}
