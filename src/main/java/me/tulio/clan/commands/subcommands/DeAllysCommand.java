package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class DeAllysCommand extends BaseCommand {
    
    @Command(name = "clan.deally", aliases = "clan.unally")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /clan deally (clan)"));
            return;
        }
        String targetClan = args[0].toUpperCase();
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
            player.sendMessage(CC.translate("&cNecesitas ser un Lider para poder eliminar allys."));
            return;
        }
        if (!ClanHandler.getAllys(clan).contains(targetClan)) {
            player.sendMessage(CC.translate("&c" + targetClan + " no es un ally tuyo."));
            return;
        }
        ClanHandler.removeAllys(clan, targetClan);
        ClanHandler.removeAllys(targetClan, clan);
        player.sendMessage(CC.translate("&eYa no eres ally con " + targetClan + '.'));
        ClanHandler.sendLeaderMessage("&eYa no eres ally con " + clan, targetClan);
    }
}
