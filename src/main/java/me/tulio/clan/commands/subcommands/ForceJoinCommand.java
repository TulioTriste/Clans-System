package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ForceJoinCommand extends BaseCommand {
    
    @Command(name = "clan.forcejoin")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (!player.isOp()) {
            player.sendMessage(CC.translate("&cNo permissions"));
            return;
        }
        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /clan forcejoin (clanName)"));
            return;
        }
        final String clan = args[0];
        if (!ClanHandler.alreadyCreated(clan)) {
            player.sendMessage(CC.translate("&cClan '" + clan + "' no encontrado."));
            return;
        }
        ClanHandler.addMember(clan, player);
        player.sendMessage(CC.translate("&eHas entrado correctamente a " + clan + '.'));
    }
}
