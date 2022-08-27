package ru.mrflaxe.invisibleframes;

import org.bukkit.plugin.java.JavaPlugin;

import ru.mrflaxe.invisibleframes.listener.ShearsUsageListener;

public class InvisibleFrames extends JavaPlugin {
    
    
    @Override
    public void onEnable() {
        registerListeners();
    }
    
    private void registerListeners() {
        new ShearsUsageListener(this).register();
    }
}
