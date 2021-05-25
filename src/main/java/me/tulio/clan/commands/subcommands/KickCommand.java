package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class KickCommand extends BaseCommand {
    
    @Command(name = "clan.kick")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /clan kick (player)"));
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&cEste player no existe o no se encuentra conectado"));
            return;
        }
        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo estás en un Clan."));
            return;
        }
        String clan = ClanHandler.getClan(player);
        if (!ClanHandler.isLeader(player, clan) && !ClanHandler.isCoLeader(player, clan)) {
            player.sendMessage(CC.translate("&cNecesitas ser Leader o Co-Leader para kickear gente."));
            return;
        }
        if (!ClanHandler.isMember(target, ClanHandler.getClan(player))) {
            player.sendMessage(CC.translate("&c" + target.getName() + " no está en tu clan."));
            return;
        }
        ClanHandler.removeMember(ClanHandler.getClan(player), target);
        player.sendMessage(CC.translate("&eHas kickeado a " + target.getName() + " del Clan."));
        if (target.isOnline()) {
            ((Player)target).sendMessage(CC.translate("&eHas sido kickeado del Clan " + ClanHandler.getClan(player) + "."));
            ((Player)target).playSound(((Player)target).getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
        }
    }
}
