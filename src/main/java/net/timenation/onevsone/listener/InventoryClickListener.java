package net.timenation.onevsone.listener;

import net.timenation.onevsone.OneVsOne;
import net.timenation.onevsone.manager.kit.KitType;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        if (event.getView().getTitle().equals(I18n.format(player, "game.inventory.kitvote.title", OneVsOne.getInstance().getPrefix()))) {
            switch (event.getCurrentItem().getType()) {
                case LEATHER_CHESTPLATE -> OneVsOne.getInstance().getKitVoteManager().playerVoteForKit(player, KitType.DEFAULT);
                case DIAMOND_AXE -> OneVsOne.getInstance().getKitVoteManager().playerVoteForKit(player, KitType.AXE);
                case TRIDENT -> OneVsOne.getInstance().getKitVoteManager().playerVoteForKit(player, KitType.DROWNED);
                case SPLASH_POTION -> OneVsOne.getInstance().getKitVoteManager().playerVoteForKit(player, KitType.HEALER);
                case NETHERITE_SWORD -> OneVsOne.getInstance().getKitVoteManager().playerVoteForKit(player, KitType.NETHERITE);
            }
        }
    }
}
