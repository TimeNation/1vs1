package net.timenation.onevsone.listener;

import net.timenation.onevsone.OneVsOne;
import net.timenation.timespigotapi.TimeSpigotAPI;
import net.timenation.timespigotapi.manager.language.I18n;
import net.timenation.timespigotapi.player.TimeStatsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void handlePlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Player killer = player.getKiller();
        TimeStatsPlayer timeStatsPlayer = TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(player, "1vs1");
        TimeStatsPlayer timeStatsKiller = TimeSpigotAPI.getInstance().getTimeStatsPlayerManager().getTimeStatsPlayer(killer, "1vs1");

        player.spigot().respawn();
        event.setDeathMessage(null);

        Bukkit.getOnlinePlayers().forEach(players -> {
            players.sendMessage(I18n.format(player, OneVsOne.getInstance().getPrefix(), "game.messages.death", TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(player.getUniqueId()).getPlayersNameWithRankColor(player.getUniqueId()), TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(killer.getUniqueId()).getPlayersNameWithRankColor(killer.getUniqueId())));
            players.teleport(new Location(Bukkit.getWorld("world"), 111.5, 114.0, -262.5, -45, 0));
            players.getInventory().clear();

            if (TimeSpigotAPI.getInstance().getTimePlayerManager().getTimePlayer(players).isNicked()) TimeSpigotAPI.getInstance().getNickManager().unnick(players, OneVsOne.getInstance());

            OneVsOne.getInstance().getScoreboardManager().sendEndScoreboardToPlayer(players, killer);
            OneVsOne.getInstance().getDefaultGameQuitItem().setItem(players);
            players.sendTitle(I18n.format(players, "game.loose.top"), I18n.format(players, "game.loose.bottom", (Object) TimeSpigotAPI.getInstance().getRankManager().getPlayersRank(killer.getUniqueId()).getPlayersRankAndName(killer.getUniqueId())));
        });

        OneVsOne.getInstance().getCountdownManager().startEndCountdown();

        killer.sendTitle(I18n.format(killer, "game.win.top"), I18n.format(player, "game.win.bottom"));
        timeStatsKiller.setKills(timeStatsKiller.getKills() + 1);
        timeStatsKiller.setWins(timeStatsKiller.getWins() + 1);

        timeStatsPlayer.setDeaths(timeStatsPlayer.getDeaths() + 1);
        timeStatsPlayer.setLooses(timeStatsPlayer.getLooses() + 1);
    }
}
