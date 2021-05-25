package me.tulio.clan.leaderboards.menu;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import me.tulio.clan.leaderboards.Leaderboard;
import me.tulio.clan.leaderboards.LeaderboardEntry;
import me.tulio.clan.util.ItemBuilder;
import me.tulio.clan.util.menu.Button;
import me.tulio.clan.util.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ClanTopKillsMenu extends Menu {

    public ClanTopKillsMenu(Plugin plugin) {
        super(plugin);
    }

    @Override
    public int getSize() {
        return 9*6;
    }

    @Override
    public String getTitle(Player player) {
        return "&5Clan Top Kills";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        List<Integer> slots = Arrays.asList(13, 21, 23, 29, 31, 33, 37, 39, 41, 43);
        for (int b = 0; b < Leaderboard.getLeaderboardEntries().stream().limit(10).count(); b++) {
            buttons.put(slots.get(b), new TopButton(Leaderboard.getLeaderboardEntries().get(b)));
        }

        return buttons;
    }

    @RequiredArgsConstructor
    private static class TopButton extends Button {

        private final LeaderboardEntry entry;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.EMERALD)
                    .name("&5" + entry.getClan())
                    .lore(Arrays.asList(
                            "&dKills&7: &f" + entry.getKills(),
                            "&dMembers&7: &f" + entry.getMembers()))
                    .build();
        }
    }
}
