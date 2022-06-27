package net.timenation.onevsone.listener;

import net.timenation.onevsone.OneVsOne;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            switch (event.getItem().getType()) {
                case BARREL -> OneVsOne.getInstance().getInventoryManager().openVoteKitInventory(player);
            }
        }
    }
}
