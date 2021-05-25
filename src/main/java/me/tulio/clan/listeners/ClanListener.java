package me.tulio.clan.listeners;

import me.tulio.clan.ChatMode;
import me.tulio.clan.ClanHandler;
import me.tulio.clan.commands.subcommands.MessageCommand;
import me.tulio.clan.util.CC;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ClanListener implements Listener {
    
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if (ClanHandler.hasClan(damager) && ClanHandler.hasClan(player)) {
                String playerClan = ClanHandler.getClan(player);
                String damagerClan = ClanHandler.getClan(damager);
                if (ClanHandler.isMember(player, damagerClan)) {
                    if (ClanHandler.isPvP(playerClan)) {
                        event.setCancelled(false);
                        return;
                    }
                    event.setCancelled(true);
                    damager.sendMessage(CC.translate("&cNo puedes golpear a " + player.getName() + '.'));
                }
                else if (ClanHandler.getAllys(damagerClan).contains(playerClan)) {
                    event.setCancelled(true);
                    damager.sendMessage(CC.translate("&cNo puedes golpear a " + player.getName() + '.'));
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (player.getKiller() != null) {
            Player killer = event.getEntity().getKiller();

            if (ClanHandler.hasClan(killer)) {
                String clan = ClanHandler.getClan(killer);
                ClanHandler.incrementTotalKills(clan);
                ClanHandler.checkLevels(clan);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (ClanHandler.hasClan(player)) {
            String clan = ClanHandler.getClan(player);
            if (MessageCommand.msg.containsKey(player.getUniqueId())) {
                if (MessageCommand.msg.get(player.getUniqueId()) == ChatMode.CLAN) {
                    event.setCancelled(true);
                    ClanHandler.sendClanMessage("&7(" + ClanHandler.getLevel(clan).getColor() + clan + "&7) &a" + player.getName() + "&7: &f" + message, clan);
                }
                else if (MessageCommand.msg.get(player.getUniqueId()) == ChatMode.ALLY) {
                    event.setCancelled(true);
                    ClanHandler.sendAllysMessage("&7(" + ClanHandler.getLevel(clan).getColor() + clan + "&7) &d" + player.getName() + "&7: &f" + message, clan);
                }
            }
        }
    }
}
