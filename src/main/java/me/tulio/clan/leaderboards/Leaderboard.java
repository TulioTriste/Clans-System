package me.tulio.clan.leaderboards;

import com.google.common.collect.Lists;
import lombok.Getter;
import me.tulio.clan.Clan;
import me.tulio.clan.ClanHandler;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Leaderboard {

    @Getter public static List<LeaderboardEntry> leaderboardEntries = Lists.newArrayList();
    @Getter public static List<ClansLevelsEntry> clanLevelsEntries = Lists.newArrayList();

    public static void init() {
        Clan.get().getServer().getScheduler().runTaskTimerAsynchronously(Clan.get(), () -> {
            leaderboardEntries.clear();
            clanLevelsEntries.clear();
            Clan.get().getClansConfig().getConfiguration().getConfigurationSection("Clans").getKeys(false).forEach(s -> {
                LeaderboardEntry entry = new LeaderboardEntry(s);
                entry.setKills(ClanHandler.getTotalKills(s));
                entry.setMembers(ClanHandler.getClanMembers(s));
                leaderboardEntries.add(entry);

                ClansLevelsEntry entry1 = new ClansLevelsEntry(s);
                entry1.setLevel(ClanHandler.getLevel(s));
                entry1.setMembers(ClanHandler.getClanMembers(s));
                clanLevelsEntries.add(entry1);
            });
        }, 60L, 1200L);

        Clan.get().getServer().getScheduler().runTaskTimerAsynchronously(Clan.get(), () -> {
            leaderboardEntries = leaderboardEntries.stream()
                    .sorted(Comparator.comparingInt(LeaderboardEntry::getKills).reversed())
                    .collect(Collectors.toList());
            clanLevelsEntries = clanLevelsEntries.stream()
                    .sorted(new Comparator<ClansLevelsEntry>() {
                        @Override
                        public int compare(ClansLevelsEntry o1, ClansLevelsEntry o2) {
                            return Integer.compare(o1.getLevel().getTopIdentifier(), o2.getLevel().getTopIdentifier());
                        }
                    }.reversed())
                    .collect(Collectors.toList());
        }, 100L, 1200L);
    }

}
