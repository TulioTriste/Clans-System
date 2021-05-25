package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class SetPvPCommand extends BaseCommand {

    @Command(name = "clan.setpvp")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /clan setpvp (true/false)"));
            return;
        }

        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo tienes un clan!"));
            return;
        }
        String clan = ClanHandler.getClan(player);
        if (!ClanHandler.isLeader(player, clan) && !ClanHandler.isCoLeader(player, clan) && !ClanHandler.isModerator(player, clan)) {
            player.sendMessage(CC.translate("&cNo tienes los permisos suficientes"));
            return;
        }
        boolean pvp = Boolean.parseBoolean(args[0]);
        ClanHandler.setPvP(clan, pvp);
        if (pvp) {
            player.sendMessage(CC.translate("&aHas activado el PvP"));
        } else {
            player.sendMessage(CC.translate("&cHas desactivado el PvP"));
        }
    }
}
