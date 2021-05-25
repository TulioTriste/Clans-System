package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ChestCommand extends BaseCommand {

    @Command(name = "clan.chest")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo tienes un clan!"));
            return;
        }

        String clan = ClanHandler.getClan(player);
        ClanHandler.openChest(clan, player);
    }
}
