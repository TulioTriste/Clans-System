package me.tulio.clan.leaderboards;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.tulio.clan.level.Levels;

@Getter @Setter
@RequiredArgsConstructor
public class ClansLevelsEntry {

    public final String clan;
    public Levels level;
    public int members;
}
