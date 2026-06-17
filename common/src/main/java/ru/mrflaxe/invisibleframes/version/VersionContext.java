package ru.mrflaxe.invisibleframes.version;

import org.bukkit.enchantments.Enchantment;

/**
 * Version-specific facade. Each supported Minecraft version range provides
 * its own implementation; the entry-point module picks the right one at runtime.
 */
public interface VersionContext {

    /**
     * The "Unbreaking" enchantment. Its API constant was renamed across versions
     * ({@code DURABILITY} on legacy builds, {@code UNBREAKING} on modern ones),
     * so each context resolves it against the API it was compiled with.
     */
    Enchantment getUnbreakingEnchantment();
}
