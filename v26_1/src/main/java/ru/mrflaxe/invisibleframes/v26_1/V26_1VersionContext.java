package ru.mrflaxe.invisibleframes.v26_1;

import ru.mrflaxe.invisibleframes.v1_20_5.V1_20_5VersionContext;

/**
 * 26.1.x context. The Bukkit API surface used by the plugin is fully compatible
 * with the 1.20.5 implementation as of 26.1.2; this class exists as an explicit
 * registration point and an extension hook for any future 26.x-only divergence.
 */
public class V26_1VersionContext extends V1_20_5VersionContext {

    public static final int[] MIN_VERSION = {26, 1, 0};
}
