package me.tulio.clan.commands.subcommands;

import me.tulio.clan.Clan;
import me.tulio.clan.leaderboards.menu.ClanTopKillsMenu;
import me.tulio.clan.leaderboards.menu.ClanTopLevelsMenu;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class TopNivelesCommand extends BaseCommand {

    @Command(name = "clan.topniveles")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        new ClanTopLevelsMenu(Clan.get()).openMenu(player);
    }
}
