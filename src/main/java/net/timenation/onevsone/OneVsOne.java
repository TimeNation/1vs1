package net.timenation.onevsone;

import lombok.Getter;
import net.timenation.onevsone.commands.StartCommand;
import net.timenation.onevsone.commands.UnnickCommand;
import net.timenation.onevsone.listener.*;
import net.timenation.onevsone.manager.CountdownManager;
import net.timenation.onevsone.manager.IngameManager;
import net.timenation.onevsone.manager.InventoryManager;
import net.timenation.onevsone.manager.kit.KitManager;
import net.timenation.onevsone.manager.kit.KitVoteManager;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.game.defaultitems.DefaultGameQuitItem;
import net.timenation.timespigotapi.manager.game.features.TrampolineFeature;
import net.timenation.timespigotapi.manager.game.gamestates.GameState;
import net.timenation.timespigotapi.manager.game.manager.ConfigManager;
import net.timenation.timespigotapi.manager.game.modules.ForcemapModule;
import net.timenation.timespigotapi.manager.game.modules.NickModule;
import net.timenation.timespigotapi.manager.game.scoreboard.ScoreboardManager;
import net.timenation.timespigotapi.manager.game.team.TeamManager;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;

@Getter
public final class OneVsOne extends TimeGame {

    private static OneVsOne instance;
    private CountdownManager countdownManager;
    private InventoryManager inventoryManager;
    private KitVoteManager kitVoteManager;
    private IngameManager ingameManager;
    private KitManager kitManager;

    @Override
    public void onEnable() {
        instance = this;
        countdownManager = new CountdownManager(this);
        inventoryManager = new InventoryManager();
        kitVoteManager = new KitVoteManager();
        ingameManager = new IngameManager();
        kitManager = new KitManager();

        setPrefix("<GRADIENT:dbad2e>§l1vs1</GRADIENT:dbc42e>");
        setColor("<SOLID:dbad2e>");
        setSecoundColor("<SOLID:dbc42e>");
        setScoreboardManager(new ScoreboardManager(this));

        setCountdown(60);
        setGameState(GameState.LOBBY);
        setGameWithKits(true);
        setDefaultGameQuitItem(new DefaultGameQuitItem(this, 7));

        new TrampolineFeature(this);
        new ForcemapModule(this, "1vs1");
        new NickModule(this);

        loadMaps("1vs1");

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new LobbyProtection(), this);

        getCommand("start").setExecutor(new StartCommand(this));
        getCommand("unnick").setExecutor(new UnnickCommand(this));

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            if (gameState.equals(GameState.LOBBY) && Bukkit.getOnlinePlayers().size() < getNeededPlayers()) {
                Bukkit.getOnlinePlayers().forEach(player -> player.sendActionBar(I18n.format(player, getPrefix(), getNeededPlayers() - Bukkit.getOnlinePlayers().size() == 1 ? "game.actionbar.iding.one" : "game.actionbar.iding", getSecoundColor(), getNeededPlayers() - Bukkit.getOnlinePlayers().size())));
            } else if(gameState.equals(GameState.STARTING)) {
                Bukkit.getOnlinePlayers().forEach(player -> player.sendActionBar(I18n.format(player, getPrefix(), "game.actionbar.countdown", getSecoundColor(), getCountdown())));
            }
        }, 1, 1);
    }

    public static OneVsOne getInstance() {
        return instance;
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public ArrayList<Player> getSpecatePlayers() {
        return specatePlayer;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public int getNeededPlayers() {
        return 2;
    }

    @Override
    public ConfigManager getConfigManager() {
        return configManager;
    }

    @Override
    public TeamManager getTeamManager() {
        return teamManager;
    }

    @Override
    public DefaultGameQuitItem getDefaultGameQuitItem() {
        return defaultGameQuitItem;
    }
}