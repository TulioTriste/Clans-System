package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class LeaveCommand extends BaseCommand {
    
    @Command(name = "clan.leave")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo estás en un clan."));
            return;
        }
        String clan = ClanHandler.getClan(player);
        if (ClanHandler.isLeader(player, clan)) {
            player.sendMessage(CC.translate("&cNo puedes salirte del Clan por que eres el Leader."));
            return;
        }
        ClanHandler.removeMember(ClanHandler.getClan(player), player);
        player.sendMessage(CC.translate("&eTe has salido del Clan."));
    }
}
