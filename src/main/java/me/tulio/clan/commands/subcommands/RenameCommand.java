package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class RenameCommand extends BaseCommand {

    @Command(name = "clan.rename")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /clan rename (newName)"));
            return;
        }

        String newName = args[0];
        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo tienes un clan."));
            return;
        }
        if (newName.length() < 3) {
            player.sendMessage(CC.translate("&cEl minimo de caracteres es 3."));
            return;
        }
        if (newName.length() > 12) {
            player.sendMessage(CC.translate("&cEl maximo de caracteres es 12."));
            return;
        }
        if (newName.contains("*") ||
                newName.contains(",") ||
                newName.contains("[") ||
                newName.contains("]") ||
                newName.contains("-") ||
                newName.contains(".") ||
                newName.contains("&")) {
            player.sendMessage(CC.translate("&cNo puedes usar este simbolo."));
            return;
        }
        String clan = ClanHandler.getClan(player);
        if (!ClanHandler.getClanLeader(clan).equals(player.getName())) {
            player.sendMessage(CC.translate("&cNo eres el Leader."));
            return;
        }
        ClanHandler.renameClan(clan, newName);
        player.sendMessage(CC.translate("&aClan renameado correctamente"));
    }
}
