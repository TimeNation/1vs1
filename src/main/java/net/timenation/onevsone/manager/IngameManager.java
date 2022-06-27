package net.timenation.onevsone.manager;

import net.timenation.onevsone.OneVsOne;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.language.I18n;
import net.timenation.timespigotapi.manager.scoreboard.ScoreboardBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class IngameManager {

    public void teleportPlayerToLocation(Player player, int tpcount, TimeGame timeGame) {
        player.teleport(new Location(timeGame.getWorld(), timeGame.getConfigManager().getDouble("team" + tpcount + ".x"), timeGame.getConfigManager().getDouble("team" + tpcount + ".y"), timeGame.getConfigManager().getDouble("team" + tpcount + ".z"), timeGame.getConfigManager().getFloat("team" + tpcount + ".yaw"), 0));
    }

    public void sendIngameScoreboard(Player player, TimeGame timeGame) {
        ScoreboardBuilder scoreboardBuilder = new ScoreboardBuilder(player);
        scoreboardBuilder.setTitle(I18n.format(player, "game.scoreboard.title", (Object) timeGame.getColor(), timeGame.getGameName(), Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers()));

        scoreboardBuilder.setLine(0, "§8§m                        ");
        scoreboardBuilder.setLine(1, "§1");
        scoreboardBuilder.setLine(2, I18n.format(player, "game.scoreboard.players"));
        scoreboardBuilder.setLine(3, "  §8● " + timeGame.getColor() + Bukkit.getOnlinePlayers().size() + "§8/" + timeGame.getColor() + Bukkit.getMaxPlayers() + timeGame.getSecoundColor() + " ☺");
        scoreboardBuilder.setLine(4, "§2");
        scoreboardBuilder.setLine(5,  I18n.format(player, "game.scoreboard.kit", (Object) timeGame.getSecoundColor()));
        scoreboardBuilder.setLine(6, "  §8● " + timeGame.getColor() + OneVsOne.getInstance().getKitVoteManager().getKitWithMaxVotes().getKitTranslateKey(player));
        scoreboardBuilder.setLine(7, "§3");
        scoreboardBuilder.setLine(8, I18n.format(player, "game.scoreboard.map", (Object) timeGame.getSecoundColor()));
        scoreboardBuilder.setLine(9, "  §8● " + timeGame.getColor() + timeGame.getGameMap() + timeGame.getSecoundColor() + " ☁");
        scoreboardBuilder.setLine(10, "§4");
        scoreboardBuilder.setLine(11, "§r§8§m                        ");
    }
}
