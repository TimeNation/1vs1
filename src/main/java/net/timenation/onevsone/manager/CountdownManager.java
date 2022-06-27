package net.timenation.onevsone.manager;

import net.timenation.onevsone.OneVsOne;
import net.timenation.onevsone.manager.kit.KitType;
import net.timenation.timespigotapi.manager.ItemManager;
import net.timenation.timespigotapi.manager.game.TimeGame;
import net.timenation.timespigotapi.manager.game.countdown.Countdown;
import net.timenation.timespigotapi.manager.language.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

public class CountdownManager extends Countdown<OneVsOne> {

    public CountdownManager(TimeGame timeGame) {
        super(timeGame, "1vs1");
    }

    @Override
    public void at0() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            tpcount++;

            OneVsOne.getInstance().getIngameManager().teleportPlayerToLocation(player, tpcount, game);
            OneVsOne.getInstance().getIngameManager().sendIngameScoreboard(player, game);
            player.getInventory().setContents(OneVsOne.getInstance().getKitManager().getKitFromPlayer(player).getKitObject().getContent());
            player.getInventory().setArmorContents(OneVsOne.getInstance().getKitManager().getKitFromPlayer(player).getKitObject().getArmor());

            if (OneVsOne.getInstance().getKitManager().getKitFromPlayer(player).equals(KitType.AXE) || OneVsOne.getInstance().getKitManager().getKitFromPlayer(player).equals(KitType.NETHERITE)) {
                player.getInventory().setItemInOffHand(new ItemManager(Material.SHIELD).addEnchantment(Enchantment.DURABILITY, 2).build());
            }

            if(OneVsOne.getInstance().getKitManager().getKitFromPlayer(player).equals(KitType.HEALER)) {
                for (int i = 0; i < 33; i++) {
                    player.getInventory().addItem(new ItemManager(Material.SPLASH_POTION, 1).addPotionEffect(PotionEffectType.HEAL, 2, 0, true).build());
                }
            }
        });
    }

    @Override
    public void before0() {

    }

    @Override
    public void at10() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            OneVsOne.getInstance().getKitManager().getPlayerKitTypeMap().put(player, OneVsOne.getInstance().getKitVoteManager().getKitWithMaxVotes());
            OneVsOne.getInstance().getPlayerKit().put(player, OneVsOne.getInstance().getKitVoteManager().getKitWithMaxVotes().getKitTranslateKey(player));
            Bukkit.getScheduler().runTask(OneVsOne.getInstance(), () -> game.getScoreboardManager().sendLobbyKitScoreboardToPlayer(player, 10, OneVsOne.getInstance().getKitVoteManager().getKitWithMaxVotes().getKitTranslateKey(player)));
            player.sendMessage(I18n.format(player, game.getPrefix(), "game.messages.at10", game.getPrefix(), game.getColor(), game.getSecoundColor(), game.getGameMap(), game.getBuilder(), OneVsOne.getInstance().getKitVoteManager().getKitWithMaxVotes().getKitTranslateKey(player)));

            player.getInventory().getItem(2).setType(Material.BARRIER);
        });
    }

    @Override
    public void atEnd() {

    }
}
