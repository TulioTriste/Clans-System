package me.tulio.clan.commands.subcommands;

import me.tulio.clan.Clan;
import me.tulio.clan.leaderboards.menu.ClanTopKillsMenu;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class TopKillsCommand extends BaseCommand {

    @Command(name = "clan.topkills")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        new ClanTopKillsMenu(Clan.get()).openMenu(player);
    }
}
