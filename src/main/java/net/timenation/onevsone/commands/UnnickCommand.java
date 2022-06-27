package net.timenation.onevsone.commands;

import net.timenation.onevsone.OneVsOne;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.game.commands.CommandUnnick;

public class UnnickCommand extends CommandUnnick<OneVsOne> {

    public UnnickCommand(TimeGame timeGame) {
        super(timeGame);
    }
}