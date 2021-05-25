package me.tulio.clan.commands.subcommands;

import me.tulio.clan.Clan;
import me.tulio.clan.ClanHandler;
import me.tulio.clan.level.Levels;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class NivelesCommand extends BaseCommand {

    @Command(name = "clan.niveles")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        player.sendMessage(CC.translate(CC.CHAT_BAR));
        for (Levels value : Levels.values()) {
            if (value == Levels.DEFAULT) continue;

            String identifier = String.valueOf(value.getKills());
            if (ClanHandler.hasClan(player) && ClanHandler.getLevel(ClanHandler.getClan(player)).getKills() >= value.getKills())
                identifier = CC.translate("&aCompletado");

            player.sendMessage(CC.translate(Clan.get().getMainConfig().getString("CLAN.NIVELES")
                    .replace("{color}", value.getColor())
                    .replace("{level}", value.getIdentifier())
                    .replace("{identifier}", identifier)));
        }
        player.sendMessage(CC.translate(CC.CHAT_BAR));
        Clan.get().getMainConfig().getStringList("CLAN.NIVELES").forEach(s ->
                player.sendMessage(CC.translate(s.replace("{bars}", CC.CHAT_BAR))));
    }
}
