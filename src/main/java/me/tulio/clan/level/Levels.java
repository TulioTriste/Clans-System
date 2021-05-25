package me.tulio.clan.level;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Levels {

    DEFAULT("&r", "Sin División", 0, 1),
    BRONZE_1("&6", "Bronze I", 50, 2),
    BRONZE_2("&6", "Bronze II", 100, 3),
    BRONZE_3("&6", "Bronze III", 150, 4),
    BRONZE_4("&6", "Bronze IV", 200, 5),
    BRONZE_5("&6", "Bronze V", 250, 6),
    SILVER_1("&7", "Silver I", 300, 7),
    SILVER_2("&7", "Silver II", 350, 8),
    SILVER_3("&7", "Silver III", 400, 9),
    SILVER_4("&7", "Silver IV", 450, 10),
    SILVER_5("&7", "Silver V", 500, 11),
    ORO_1("&e", "Oro I", 550, 12),
    ORO_2("&e", "Oro II", 600, 13),
    ORO_3("&e", "Oro III", 650, 14),
    ORO_4("&e", "Oro IV", 700, 15),
    ORO_5("&e", "Oro V", 750, 16),
    PLATINO_1("&9", "Platino I", 800, 17),
    PLATINO_2("&9", "Platino II", 850, 18),
    PLATINO_3("&9", "Platino III", 900, 19),
    PLATINO_4("&9", "Platino IV", 950, 20),
    PLATINO_5("&9", "Platino V", 1000, 21),
    DIAMANTE_1("&b", "Diamante I", 1050, 22),
    DIAMANTE_2("&b", "Diamante II", 1100, 23),
    DIAMANTE_3("&b", "Diamante III", 1150, 24),
    DIAMANTE_4("&b", "Diamante IV", 1200, 25),
    DIAMANTE_5("&b", "Diamante V", 1250, 26),
    ESMERALDA_1("&a", "Esmeralda I", 1300, 27),
    ESMERALDA_2("&a", "Esmeralda II", 1350, 28),
    ESMERALDA_3("&a", "Esmeralda III", 1400, 29),
    ESMERALDA_4("&a", "Esmeralda IV", 1450, 30),
    ESMERALDA_5("&a", "Esmeralda V", 1500, 31),
    OBSIDIANA_1("&3", "Obsidiana I", 1550, 32),
    OBSIDIANA_2("&3", "Obsidiana II", 1600, 33),
    OBSIDIANA_3("&3", "Obsidiana III", 1650, 34),
    OBSIDIANA_4("&3", "Obsidiana IV", 1700, 35),
    OBSIDIANA_5("&3", "Obsidiana V", 1750, 36),
    MASTER_1("&d&o", "Master I", 1800, 37),
    MASTER_2("&d&o", "Master II", 1900, 38),
    MASTER_3("&d&o", "Master III", 2000, 39),
    MASTER_4("&d&o", "Master IV", 2500, 40),
    MASTER_5("&d&o", "Master V", 3000, 41);

    public final String color;
    public final String identifier;
    public final int kills, topIdentifier;

    public static Levels getByIdentifier(String identifier) {
        for (Levels value : Levels.values()) {
            if (value.getIdentifier().equalsIgnoreCase(identifier)) return value;
        }
        return null;
    }
}
