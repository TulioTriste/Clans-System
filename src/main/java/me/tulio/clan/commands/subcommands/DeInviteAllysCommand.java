package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class DeInviteAllysCommand extends BaseCommand {
    
    @Command(name = "clan.deinviteally", aliases = "clan.uninviteally")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /clan deinviteally (clan)"));
            return;
        }
        String targetClan = args[0];
        if (!ClanHandler.alreadyCreated(targetClan)) {
            player.sendMessage(CC.translate("&cEste clan no existe."));
            return;
        }
        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo estás en un clan."));
            return;
        }
        String clan = ClanHandler.getClan(player);
        if (!ClanHandler.isLeader(player, clan)) {
            player.sendMessage(CC.translate("&cNecesitas ser un Lider para poder eliminar la invitacion."));
            return;
        }
        if (!ClanHandler.isAlreadyAllyInvited(clan, targetClan)) {
            player.sendMessage(CC.translate("&c" + targetClan + " no tiene una invitacion."));
            return;
        }
        ClanHandler.removeAllysInvite(clan, targetClan);
        player.sendMessage(CC.translate("&eSe ha revocado el invite a " + targetClan + '.'));
    }
}
