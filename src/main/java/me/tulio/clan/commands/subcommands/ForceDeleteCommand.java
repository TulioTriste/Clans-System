package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ForceDeleteCommand extends BaseCommand {

    @Command(name = "clan.forcedelete", aliases = "clan.forcedisband")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /clan forcedisband (clanName)"));
            return;
        }
        String clan = args[0];
        if (!ClanHandler.alreadyCreated(clan)) {
            player.sendMessage(CC.translate("&cClan no encontrado."));
            return;
        }
        ClanHandler.disbandClan(clan, player);
    }
}
