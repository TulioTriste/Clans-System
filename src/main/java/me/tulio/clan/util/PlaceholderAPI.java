package me.tulio.clan.util;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.tulio.clan.ClanHandler;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "electronclan";
    }

    @Override
    public String getAuthor() {
        return "TulioTriste";
    }

    @Override
    public String getVersion() {
        return "1.0-SNAPSHOT";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (player == null) return "";

        if (params.contains("format_clan")) {
            return ClanHandler.hasClan(player) ?
                    CC.translate("&7[" + ClanHandler.getLevel(ClanHandler.getClan(player)).getColor() + ClanHandler.getClan(player) + "&7] ") :
                    "";
        }
        else if (params.contains("default_clan")) return ClanHandler.hasClan(player) ? ClanHandler.getClan(player) : "";

        return null;
    }
}
