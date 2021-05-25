package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class AddModeratorCommand extends BaseCommand {

    @Command(name = "clan.addmod", aliases = {"clan.addmoderator", "clan.mod"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /clan addmod (member)"));
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

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&cEste usuario no existe o no está conectadco"));
            return;
        }
        if (ClanHandler.hasClan(target) && !Objects.equals(ClanHandler.getClan(target), clan)) {
            player.sendMessage(CC.translate("&cEste jugador ya pertenece a un clan"));
            return;
        }

        ClanHandler.addModerator(clan, target);
        player.sendMessage(CC.translate("&aSe ha añadido a " + target.getName() + " como Moderador"));
    }
}
