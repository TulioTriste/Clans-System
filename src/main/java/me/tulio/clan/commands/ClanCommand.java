package me.tulio.clan.commands;

import me.tulio.clan.Clan;
import me.tulio.clan.commands.subcommands.*;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.commands.BaseCommand;
import me.tulio.clan.util.commands.Command;
import me.tulio.clan.util.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ClanCommand extends BaseCommand {

    public ClanCommand() {
        super();
        new CreateCommand();
        new DeInviteCommand();
        new DeleteCommand();
        new ForceDeleteCommand();
        new ForceJoinCommand();
        new HelpCommand();
        new InviteCommand();
        new JoinCommand();
        new KickCommand();
        new LeaveCommand();
        new RenameCommand();
        new MessageCommand();
        new ShowCommand();
        new TopKillsCommand();
        new SetLeaderCommand();
        new AddModeratorCommand();
        new SetCoLeaderCommand();
        new SetPvPCommand();
        new ForceSetLeader();
        new ChestCommand();
        new RemoveCoLeader();
        new RemoveModerator();
        new NivelesCommand();
        new TopNivelesCommand();
        new AddAllysCommand();
        new AllyAcceptCommand();
        new DeAllysCommand();
        new DeInviteAllysCommand();
        new ReloadCommand();
    }

    @Command(name = "clan")
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