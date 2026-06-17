package ru.mrflaxe.invisibleframes.listener;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.RequiredArgsConstructor;
import ru.mrflaxe.invisibleframes.version.VersionContext;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShearsUsageListener implements Listener {

    VersionContext context;

    @EventHandler
    public void onShearsUsage(PlayerInteractEntityEvent event) {
        Entity involved = event.getRightClicked();

        if (!(involved instanceof ItemFrame)) {
            return;
        }

        ItemFrame frame = (ItemFrame) involved;
        EquipmentSlot hand = event.getHand();
        Player player = event.getPlayer();

        ItemStack item = player.getInventory().getItem(hand);

        if (!item.getType().equals(Material.SHEARS)) {
            return;
        }

        if (!frame.isVisible()) {
            return;
        }

        event.setCancelled(true);

        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            context.damageShears(player, hand);
        }

        frame.setVisible(false);
        updatePlayerStatistic(player, item);

        Location frameLocation = frame.getLocation();
        frameLocation.getWorld().playSound(frameLocation, Sound.ENTITY_SHEEP_SHEAR, 1, 1);
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
