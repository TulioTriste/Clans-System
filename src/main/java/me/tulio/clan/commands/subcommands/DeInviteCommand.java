package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DeInviteCommand extends BaseCommand {
    
    @Command(name = "clan.deinvite", aliases = "clan.uninvite")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /clan deinvite (player)"));
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&cEste player no existe o no se encuentra conectado"));
            return;
        }
        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo estás en un clan."));
            return;
        }
        String clan = ClanHandler.getClan(player);
        if (!ClanHandler.isLeader(player, clan) && !ClanHandler.isCoLeader(player, clan)) {
            player.sendMessage(CC.translate("&cNecesitas ser un Lider o CoLider para poder eliminar el invite."));
            return;
        }
        if (!ClanHandler.isAlreadyInvited(clan, target)) {
            player.sendMessage(CC.translate("&c" + target.getName() + " no está invitado en tu clan."));
            return;
        }
        ClanHandler.removeInvite(clan, target);
        player.sendMessage(CC.translate("&eSe ha revocado el invite a " + target.getName() + '.'));
    }
}
