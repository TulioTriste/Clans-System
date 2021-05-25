package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetCoLeaderCommand extends BaseCommand {

    @Command(name = "clan.setcoleader", aliases = {"clan.coleader"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /clan coleader (member)"));
            return;
        }

        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo tienes un clan!"));
            return;
        }

        String clan = ClanHandler.getClan(player);
        if (!ClanHandler.isLeader(player, clan)) {
            player.sendMessage(CC.translate("&cTienes que ser leader para hacer eso"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&cEste usuario no existe o no está conectadco"));
            return;
        }

        ClanHandler.setCoLeader(clan, target);
        player.sendMessage(CC.translate("&aSe ha puesto a " + target.getName() + " como Co-Leader"));
    }
}
