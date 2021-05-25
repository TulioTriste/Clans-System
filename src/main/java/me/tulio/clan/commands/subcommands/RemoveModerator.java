package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class RemoveModerator extends BaseCommand {

    @Command(name = "clan.removemod", aliases = {"clan.removemoderator", "clan.deletemod"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /clan removemod (member)"));
            return;
        }

        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo tienes un clan!"));
            return;
        }

        String clan = ClanHandler.getClan(player);
        if (!ClanHandler.isLeader(player, clan) && !ClanHandler.isCoLeader(player, clan)) {
            player.sendMessage(CC.translate("&cTienes que ser leader o co-leader para hacer eso"));
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&cEste usuario no existe"));
            return;
        }
        if (!ClanHandler.isModerator(target, clan)) {
            player.sendMessage(CC.translate("&cEste jugador no es un moderador"));
            return;
        }

        ClanHandler.removeModerator(clan, target);
        player.sendMessage(CC.translate("&aSe ha removido a " + target.getName() + " como Moderador"));
    }
}
