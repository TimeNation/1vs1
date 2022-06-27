package net.timenation.onevsone.manager.kit;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import java.util.Map;

public class KitManager {

    private final Map<Player, KitType> playerKitTypeMap = Maps.newHashMap();

    public KitType getKitFromPlayer(Player player) {
        return getPlayerKitTypeMap().get(player);
    }

    public boolean useKit(Player player, KitType kitType){
        return getPlayerKitTypeMap().get(player).equals(kitType);
    }

    public Map<Player, KitType> getPlayerKitTypeMap() {return playerKitTypeMap;}
}
