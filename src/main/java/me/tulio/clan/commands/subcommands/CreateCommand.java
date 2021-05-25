package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class CreateCommand extends BaseCommand {

    @Command(name = "clan.create")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /clan create (name)"));
            return;
        }
        String clan = args[0].toUpperCase();
        if (clan.length() < 3) {
            player.sendMessage(CC.translate("&cEl minimo de caracteres es 3."));
            return;
        }
        if (clan.length() > 12) {
            player.sendMessage(CC.translate("&cEl maximo de caracteres es 2."));
            return;
        }
        if (clan.contains("*") ||
                clan.contains(",") ||
                clan.contains("[") ||
                clan.contains("]") ||
                clan.contains("-") ||
                clan.contains(".") ||
                clan.contains("&")) {
            player.sendMessage(CC.translate("&cNo puedes usar este simbolo."));
            return;
        }
        if (ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cYa te encuentras en un clan."));
            return;
        }
        if (ClanHandler.alreadyCreated(clan)) {
            player.sendMessage(CC.translate("&cEl Clan '" + clan + "' ya está creado."));
            return;
        }
        ClanHandler.createClan(player, clan);
    }
}
