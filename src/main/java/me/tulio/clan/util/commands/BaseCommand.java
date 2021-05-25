package me.tulio.clan.util.commands;

public abstract class BaseCommand {

    public BaseCommand() {
        CommandManager.getInstance().registerCommands(this, null);
    }

    public abstract void onCommand(CommandArgs command);
}
