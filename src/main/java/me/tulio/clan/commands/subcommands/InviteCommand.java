package me.tulio.clan.commands.subcommands;

import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InviteCommand extends BaseCommand {
    
    @Command(name = "clan.invite")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /clan invite (player)"));
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&cEste player no existe o no se encuentra conectado"));
            return;
        }
        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo estás en un clan."));
            return;
        }
        String clan = ClanHandler.getClan(player);
        if (!ClanHandler.isLeader(player, clan) && !ClanHandler.isCoLeader(player, clan) && !ClanHandler.isModerator(player, clan)) {
            player.sendMessage(CC.translate("&cNecesitas ser Leader, Co-Leader o Moderador para invitar a jugadores."));
            return;
        }
        if (ClanHandler.hasClan(target)) {
            player.sendMessage(CC.translate("&c" + target.getName() + " ya está en un clan."));
            return;
        }
        if (ClanHandler.isAlreadyInvited(clan, target)) {
            player.sendMessage(CC.translate("&c" + target.getName() + " ya está invitado al clan."));
            return;
        }
        if (ClanHandler.getClanMembers(clan) >= ClanHandler.getMinClanSize()) {
            player.sendMessage(CC.translate("&cYa estás al limite de miembros en tu clan."));
            return;
        }
        ClanHandler.addInvite(ClanHandler.getClan(player), target);
        ClanHandler.sendClanMessage("&a" + target.getName() + " &eha sido invitado al clan.", ClanHandler.getClan(player));
        TextComponent message = new TextComponent();
        message.setText(CC.translate("&eHas sido invitado para unirte a '&a" + ClanHandler.getClan(player) + "&e' usa &f'/clan join " + ClanHandler.getClan(player) + "'"));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan join " + ClanHandler.getClan(player)));
        target.sendMessage(message.getText());
    }
}
