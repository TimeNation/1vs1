package net.timenation.onevsone.listener;

import net.timenation.onevsone.OneVsOne;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.game.defaultitems.DefaultGameExplainItem;
import net.timenation.timespigotapi.manager.game.defaultitems.DefaultGameNavigatorItem;
import net.timenation.timespigotapi.manager.game.phase.LobbyPhase;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerJoinListener extends LobbyPhase<OneVsOne> {

    private final TimeGame timeGame;

    public PlayerJoinListener(TimeGame timeGame) {
        super(timeGame, "1vs1");
        this.timeGame = timeGame;

        new DefaultGameNavigatorItem(timeGame, 1);
        new DefaultGameExplainItem(timeGame, 6, "game.explain.1vs1");
    }

    @Override
    public void setKitStuff(Player player) {
        player.getInventory().setItem(2, new ItemManager(Material.BARREL, 1).setDisplayName(I18n.format(player, "game.item.votekit", OneVsOne.getInstance().getPrefix())).build());
        timeGame.getPlayerKit().put(player, "Voting...");
    }

    @Override
    public void setDefaultKit(Player player) {
        timeGame.getPlayerKit().put(player, "Voting...");
    }

    @Override
    public void startCountdown() {
        OneVsOne.getInstance().getCountdownManager().countdown = 20;
        OneVsOne.getInstance().getCountdownManager().startCountdown();
    }

    @Override
    public void updateScoreboard(Player player) {
        timeGame.getScoreboardManager().sendLobbyKitScoreboardToPlayer(player, timeGame.getCountdown(), "Voting...");
    }
}
