package me.tulio.clan.commands.subcommands;

import me.tulio.clan.Clan;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class HelpCommand extends BaseCommand {

    @Command(name = "clan.help")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        Clan.get().getMainConfig().getStringList("CLAN.HELP").forEach(s -> {
            if (s.contains("{ops}")) {
                Clan.get().getMainConfig().getStringList("CLAN.OPS_HELP").forEach(s2 ->
                        player.sendMessage(CC.translate(s2.replace("{bars}", CC.CHAT_BAR))));
                return;
            }
            player.sendMessage(CC.translate(s.replace("{bars}", CC.CHAT_BAR)));
        });
    }
}
