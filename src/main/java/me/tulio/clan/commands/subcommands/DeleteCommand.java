package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class DeleteCommand extends BaseCommand {

    @Command(name = "clan.delete", aliases = "clan.disband")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo estás en un clan."));
            return;
        }
        if (!ClanHandler.isLeader(player, ClanHandler.getClan(player))) {
            player.sendMessage(CC.translate("&cNecesitas ser el Leader para eliminar este clan."));
            return;
        }
        ClanHandler.disbandClan(ClanHandler.getClan(player), player);
    }
}
