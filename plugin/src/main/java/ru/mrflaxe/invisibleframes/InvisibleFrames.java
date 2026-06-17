package ru.mrflaxe.invisibleframes;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import ru.mrflaxe.invisibleframes.bootstrap.VersionResolver;
import ru.mrflaxe.invisibleframes.listener.ShearsUsageListener;
import ru.mrflaxe.invisibleframes.version.VersionContext;

public class InvisibleFrames extends JavaPlugin {

    @Override
    public void onEnable() {
        VersionContext context;
        try {
            context = VersionResolver.resolve(getLogger());
        } catch (Throwable t) {
            getLogger().log(Level.SEVERE, "Failed to resolve a version context. Plugin will be disabled.", t);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        new ShearsUsageListener(context).register(this);
    }
}
