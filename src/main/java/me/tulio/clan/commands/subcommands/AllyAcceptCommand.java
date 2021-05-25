package me.tulio.clan.commands.subcommands;

import me.tulio.clan.Clan;
import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class AllyAcceptCommand extends BaseCommand {
    
    @Command(name = "clan.allyaccept")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /clan allyaccept (clan)"));
            return;
        }
        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo tienes un clan."));
            return;
        }
        String clan = ClanHandler.getClan(player);
        if (!ClanHandler.isLeader(player, clan)) {
            player.sendMessage(CC.translate("&cNecesitas ser leader para aceptar esta peticion."));
            return;
        }
        String clanInvite = args[0].toUpperCase();
        if (!ClanHandler.getInvitedAllys(clanInvite).contains(clan)) {
            player.sendMessage(CC.translate("&cNo tienes una peticion de este clan."));
            return;
        }
        if (!ClanHandler.alreadyCreated(clanInvite)) {
            player.sendMessage(CC.translate("&cClan '" + clanInvite + "' no encontrado."));
            return;
        }
        if (ClanHandler.getAllys(clan).size() >= Clan.get().getMainConfig().getInt("CLAN.ALLYS")) {
            player.sendMessage(CC.translate("&cEste clan ya se encuentra en el limite de allys."));
            return;
        }
        ClanHandler.removeAllysInvite(clanInvite, clan);
        ClanHandler.addAllys(clanInvite, clan);
        ClanHandler.addAllys(clan, clanInvite);
        player.sendMessage(CC.translate("&eAhora eres Ally con el clan &a" + clanInvite + "&e."));
        ClanHandler.sendClanMessage("&d" + clan + " &eahora es ally del clan.", clanInvite);
    }
}
