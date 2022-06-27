package net.timenation.onevsone.manager;

import net.timenation.onevsone.OneVsOne;
import net.timenation.onevsone.manager.kit.KitType;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryManager {

    private final ItemStack blackGlass = new ItemManager(Material.BLACK_STAINED_GLASS_PANE, 1).setDisplayName(" ").build();

    public void openVoteKitInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 5, I18n.format(player, "game.inventory.kitvote.title", OneVsOne.getInstance().getPrefix()));

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, new ItemManager(Material.BLACK_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        }

        for (int i = 36; i < 45; i++) {
            inventory.setItem(i, new ItemManager(Material.BLACK_STAINED_GLASS_PANE, 1).setDisplayName(" ").build());
        }

        for (KitType kitType : KitType.values()) {
            inventory.addItem(new ItemManager(kitType.getKitObject().getItem()).setDisplayName(kitType.getKitName(player)).setLore(I18n.formatLines(player, "game.inventory.kitvote.votes", OneVsOne.getInstance().getColor(), OneVsOne.getInstance().getKitVoteManager().getVotesFromKit(kitType))).build());
        }

        player.openInventory(inventory);
    }
}
