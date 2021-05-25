package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class JoinCommand extends BaseCommand {
    
    @Command(name = "clan.join")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /clan join (clanName)"));
            return;
        }
        String clan = args[0];
        if (ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cYa estás en un clan."));
            return;
        }
        if (!ClanHandler.alreadyCreated(clan)) {
            player.sendMessage(CC.translate("&cClan '" + clan + "' no encontrado."));
            return;
        }
        if (!ClanHandler.isAlreadyInvited(clan, player)) {
            player.sendMessage(CC.translate("&cNecesitas que te inviten para poder entrar."));
            return;
        }
        ClanHandler.addMember(clan, player);
        player.sendMessage(CC.translate("&eHas entrado al Clan &a" + clan + "&e."));
        ClanHandler.sendClanMessage("&a" + player.getName() + " &eha entrado al Clan.", ClanHandler.getClan(player));
    }
}
