package me.tulio.clan.commands.subcommands;

import me.tulio.clan.Clan;
import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class AddAllysCommand extends BaseCommand {
    
    @Command(name = "clan.ally")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /clan ally (clan)"));
            return;
        }
        String targetClan = args[0].toUpperCase();
        if (!ClanHandler.alreadyCreated(targetClan)) {
            player.sendMessage(CC.translate("&cEste clan no existe"));
            return;
        }
        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo estás en un clan."));
            return;
        }
        String clan = ClanHandler.getClan(player);
        if (!ClanHandler.isLeader(player, clan)) {
            player.sendMessage(CC.translate("&cNecesitas ser Leader para enviar peticiones de ally."));
            return;
        }
        if (ClanHandler.isAlreadyAllyInvited(clan, targetClan)) {
            player.sendMessage(CC.translate("&c" + targetClan + " ya ha sido invitado."));
            return;
        }
        if (ClanHandler.getAllys(clan).size() >= Clan.get().getMainConfig().getInt("CLAN.ALLYS")) {
            player.sendMessage(CC.translate("&cYa estás al limite de allys en tu clan."));
            return;
        }
        ClanHandler.addAllysInvite(clan, targetClan);
        ClanHandler.sendCoAndLeaderMessage("&a" + targetClan + " &eha sido invitado como ally al clan.", clan);
        TextComponent message = new TextComponent();
        message.setText(CC.translate("&eHas sido invitado para ser ally con '&a" + clan + "&e' usa &f'/clan allyaccept " + clan + "'"));
//        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan allyaccept " + clan));
        ClanHandler.sendLeaderMessage(message.getText(), targetClan);
//        if (Bukkit.getPlayer(ClanHandler.getClanLeader(targetClan)) != null) {
//            Bukkit.getPlayer(ClanHandler.getClanLeader(targetClan)).spigot().sendMessage(message);
//        }
    }
}
