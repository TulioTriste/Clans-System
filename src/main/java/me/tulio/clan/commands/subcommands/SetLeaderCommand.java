package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetLeaderCommand extends BaseCommand {

    @Command(name = "clan.setleader")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /clan setleader (member)"));
            return;
        }

        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo estás en un clan!"));
            return;
        }
        String clan = ClanHandler.getClan(player);
        if (!ClanHandler.isLeader(player, clan)) {
            player.sendMessage(CC.translate("&cNo eres el Leader del clan!"));
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&cEste usuario no existe o no está conectado!"));
            return;
        }

        ClanHandler.setLeader(clan, target);
        player.sendMessage(CC.translate("&aSe ha puesto a " + target.getName() + " como Leader"));
    }
}
