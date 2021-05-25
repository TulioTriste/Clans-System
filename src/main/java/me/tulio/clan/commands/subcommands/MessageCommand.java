package me.tulio.clan.commands.subcommands;

import com.google.common.collect.Maps;
import me.tulio.clan.ChatMode;
import me.tulio.clan.ClanHandler;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class MessageCommand extends BaseCommand {

    public static Map<UUID, ChatMode> msg = Maps.newHashMap();

    @Command(name = "clan.message", aliases = {"clan.msg"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            if (msg.containsKey(player.getUniqueId())) {
                if (msg.get(player.getUniqueId()) == ChatMode.DEFAULT) {
                    msg.put(player.getUniqueId(), ChatMode.CLAN);
                    player.sendMessage(CC.translate("&aHas entrado en el modo Clan de chat."));
                }
                else if (msg.get(player.getUniqueId()) == ChatMode.CLAN) {
                    msg.put(player.getUniqueId(), ChatMode.ALLY);
                    player.sendMessage(CC.translate("&dHas entrado en el modo Ally de chat."));
                }
                else if (msg.get(player.getUniqueId()) == ChatMode.ALLY) {
                    msg.put(player.getUniqueId(), ChatMode.DEFAULT);
                    player.sendMessage(CC.translate("&7Has entrado en el modo Normal de chat."));
                }
                else {
                    msg.put(player.getUniqueId(), ChatMode.DEFAULT);
                    player.sendMessage(CC.translate("&7Has entrado en el modo Normal de chat."));
                }
            } else {
                msg.put(player.getUniqueId(), ChatMode.CLAN);
                player.sendMessage(CC.translate("&aHas entrado en el modo Clan de chat."));
            }
            return;
        }
        if (!ClanHandler.hasClan(player)) {
            player.sendMessage(CC.translate("&cNo estás en un clan."));
            return;
        }
        String clan = ClanHandler.getClan(player);
        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(arg).append(" ");
        }
        if (msg.containsKey(player.getUniqueId())) {
            if (msg.get(player.getUniqueId()) == ChatMode.DEFAULT || msg.get(player.getUniqueId()) == ChatMode.CLAN) {
                ClanHandler.sendClanMessage("&7(" + ClanHandler.getLevel(clan).getColor() + clan + "&7) &a" + player.getName() + "&7: &f" + message, ClanHandler.getClan(player));
            } else if (msg.get(player.getUniqueId()) == ChatMode.ALLY) {
                ClanHandler.sendAllysMessage("&7(" + ClanHandler.getLevel(clan).getColor() + clan + "&7) &d" + player.getName() + "&7: &f" + message, ClanHandler.getClan(player));
            }
        } else {
            ClanHandler.sendClanMessage("&7(" + ClanHandler.getLevel(clan).getColor() + clan + "&7) &a" + player.getName() + "&7: &f" + message, ClanHandler.getClan(player));
        }
    }
}
