package me.tulio.clan.commands.subcommands;

import me.tulio.clan.Clan;
import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ReloadCommand extends BaseCommand {

    @Command(name = "clan.reload", permission = "clan.reload")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        long start = System.currentTimeMillis();
        Clan.get().getMainConfig().save();
        Clan.get().getMainConfig().reload();
        Clan.get().getClansConfig().save();
        Clan.get().getClansConfig().reload();
        long finish = System.currentTimeMillis();
        player.sendMessage(CC.translate("&aConfig's reiniciadas &7(" + (finish-start) + "ms)"));
    }
}
