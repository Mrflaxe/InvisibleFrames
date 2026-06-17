package ru.mrflaxe.invisibleframes.v1_20_5;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import ru.mrflaxe.invisibleframes.version.VersionContext;

public class V1_20_5VersionContext implements VersionContext {

    /**
     * Paper's {@code damageItemStack} runs the held item through the server's own
     * damage routine: it respects the Unbreaking enchantment, fires
     * {@code PlayerItemDamageEvent} / {@code PlayerItemBreakEvent}, and removes the
     * item once it breaks.
     */
    @Override
    public void damageShears(Player player, EquipmentSlot hand) {
        player.damageItemStack(hand, 1);
    }
}
