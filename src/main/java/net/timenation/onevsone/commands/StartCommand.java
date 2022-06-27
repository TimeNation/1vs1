package net.timenation.onevsone.commands;

import net.timenation.onevsone.OneVsOne;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.game.commands.CommandStart;

public class StartCommand extends CommandStart<OneVsOne> {


    public StartCommand(TimeGame timeGame) {
        super(timeGame);
    }

    @Override
    public void startGame() {
        OneVsOne.getInstance().getCountdownManager().startGame();
    }
}