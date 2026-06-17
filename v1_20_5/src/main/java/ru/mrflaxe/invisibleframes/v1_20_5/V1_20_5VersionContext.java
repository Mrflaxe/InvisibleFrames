package ru.mrflaxe.invisibleframes.v1_20_5;

import org.bukkit.enchantments.Enchantment;

import ru.mrflaxe.invisibleframes.version.VersionContext;

public class V1_20_5VersionContext implements VersionContext {

    public static final int[] MIN_VERSION = {1, 20, 5};

    @Override
    public Enchantment getUnbreakingEnchantment() {
        return Enchantment.UNBREAKING;
    }
}
