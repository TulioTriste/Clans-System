package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ShowCommand extends BaseCommand {

    @Command(name = "clan.show", aliases = {"clan.info", "clan.who"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            if (!ClanHandler.hasClan(player)) {
                player.sendMessage(CC.translate("&cNo estás en un clan."));
                return;
            }
            ClanHandler.printClanInformation(player, ClanHandler.getClan(player));
        } else {
            if (Bukkit.getPlayer(args[0]) != null) {
                Player fuckinMeeent = Bukkit.getPlayer(args[0]);
                if (ClanHandler.hasClan(fuckinMeeent)) {
                    ClanHandler.printClanInformation(player, ClanHandler.getClan(fuckinMeeent));
                } else {
                    player.sendMessage(CC.translate("&cEste jugador no tiene Clan."));
                }
            } else {
                String clan = args[0].toUpperCase();
                if (!ClanHandler.alreadyCreated(clan)) {
                    player.sendMessage(CC.translate("&cClan '" + clan + "' no encontrado."));
                    return;
                }
                ClanHandler.printClanInformation(player, clan);
            }
        }
    }
}
