package net.timenation.onevsone.manager.kit;

import net.timenation.onevsone.OneVsOne;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KitVoteManager {

    private final HashMap<KitType, Integer> kitVoteMap;
    private final HashMap<Player, KitType> playerVotedMap;

    public KitVoteManager() {
        this.kitVoteMap = new HashMap<>();
        this.playerVotedMap = new HashMap<>();

        for (KitType kit : KitType.values()) {
            this.kitVoteMap.put(kit, 0);
        }
    }

    public void playerVoteForKit(Player player, KitType kitType) {
        if (!hasPlayerVotedForKit(player, kitType)) {
            if(playerHasVotedForKit(player)) removeVoteFromKit(OneVsOne.getInstance().getKitVoteManager().getVotedKitFromPlayer(player));
            addVoteToKit(player, kitType);
            player.sendMessage(I18n.format(player, OneVsOne.getInstance().getPrefix(), "game.messages.voteforkit", OneVsOne.getInstance().getColor() + "§l" + kitType.getKitTranslateKey(player)));
            player.closeInventory();
        } else {
            player.sendMessage(I18n.format(player, OneVsOne.getInstance().getPrefix(), "game.messages.alreadyvotedforkit", OneVsOne.getInstance().getColor() + "§l" + kitType.getKitTranslateKey(player)));
        }
    }

    public void addVoteToKit(Player player, KitType kit) {
        this.kitVoteMap.put(kit, this.kitVoteMap.get(kit) + 1);
        this.playerVotedMap.put(player, kit);
    }

    public void removeVoteFromKit(KitType kit) {
        this.kitVoteMap.put(kit, this.kitVoteMap.get(kit) - 1);
    }

    public int getVotesFromKit(KitType kit) {
        return this.kitVoteMap.get(kit);
    }

    public KitType getVotedKitFromPlayer(Player player) {
        return this.playerVotedMap.get(player);
    }

    public KitType getKitWithMaxVotes() {
        for (Map.Entry<KitType, Integer> kitTypeIntegerEntry : this.kitVoteMap.entrySet()) {
            if(kitTypeIntegerEntry.getValue().equals(Collections.max(this.kitVoteMap.values()))) {
                return kitTypeIntegerEntry.getKey();
            }
        }

        return null;
    }

    public boolean hasPlayerVotedForKit(Player player, KitType kitType) {
        return kitType == this.playerVotedMap.get(player);
    }

    public boolean playerHasVotedForKit(Player player) {
        return this.playerVotedMap.get(player) != null;
    }

    public HashMap<KitType, Integer> getKitVoteMap() {
        return kitVoteMap;
    }

    public HashMap<Player, KitType> getPlayerVotedMap() {
        return playerVotedMap;
    }
}