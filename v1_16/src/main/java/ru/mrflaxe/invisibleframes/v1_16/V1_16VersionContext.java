package ru.mrflaxe.invisibleframes.v1_16;

import org.bukkit.enchantments.Enchantment;

import ru.mrflaxe.invisibleframes.version.VersionContext;

public class V1_16VersionContext implements VersionContext {

    public static final int[] MIN_VERSION = {1, 16, 0};

    @Override
    @SuppressWarnings("deprecation")
    public Enchantment getUnbreakingEnchantment() {
        return Enchantment.DURABILITY;
    }
}
