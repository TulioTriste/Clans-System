package me.tulio.clan.leaderboards;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class LeaderboardEntry {

    public final String clan;
    public int kills, members;
}
