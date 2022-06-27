package net.timenation.onevsone.listener;

import net.timenation.onevsone.OneVsOne;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import net.timenation.timespigotapi.manager.game.phase.EndPhase;
import org.bukkit.entity.Player;

public class PlayerQuitListener extends EndPhase<OneVsOne> {

    private final TimeGame timeGame;

    public PlayerQuitListener(TimeGame timeGame) {
        super(timeGame, "1vs1");
        this.timeGame = timeGame;
    }

    @Override
    public void onQuit(Player player) {
        if (timeGame.getGameState().equals(GameState.LOBBY) || timeGame.getGameState().equals(GameState.STARTING)) {
            if (OneVsOne.getInstance().getKitVoteManager().playerHasVotedForKit(player))
                OneVsOne.getInstance().getKitVoteManager().removeVoteFromKit(OneVsOne.getInstance().getKitVoteManager().getVotedKitFromPlayer(player));
        }
    }

    @Override
    public void stopCountdown() {
        OneVsOne.getInstance().getCountdownManager().stopCountdown();
    }

    @Override
    public void startEndCountdown() {
        OneVsOne.getInstance().getCountdownManager().startEndCountdown();
    }
}