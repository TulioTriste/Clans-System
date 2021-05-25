package me.tulio.clan;

import java.util.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import me.tulio.clan.level.Levels;
import me.tulio.clan.util.CC;
import me.tulio.clan.util.InventoryUtil;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;

import java.text.SimpleDateFormat;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class ClanHandler {
	
    public static FileConfiguration clans = Clan.get().getClansConfig().getConfiguration();
    @Getter public static Map<String, Inventory> inventorys = Maps.newHashMap();

    public static void init() {
        clans.getConfigurationSection("Clans").getKeys(false).forEach(s -> {
                inventorys.put(s, InventoryUtil.toInventory(clans, "Clans." + s + ".chest"));
        });
    }

    public static void saveInventorys() {
        inventorys.forEach((s, itemStacks) -> {
            InventoryUtil.saveInventory(itemStacks, clans, "Clans." + s + ".chest");
        });
    }

	public static void createClan(Player player, String clan) {
        clans.set("Clans." + clan + ".Leader", player.getName());
        clans.set("Clans." + clan + ".Co-Leader", "");
        clans.set("Clans." + clan + ".Mods", Lists.newArrayList());
        List<String> list = Collections.singletonList(player.getName());
        clans.set("Clans." + clan + ".Members", list);
        clans.set("Clans." + clan + ".Invite", Lists.newArrayList());
        clans.set("Clans." + clan + ".InviteAllys", Lists.newArrayList());
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        clans.set("Clans." + clan + ".Date", format.format(now));
        clans.set("Clans." + clan + ".TotalKills", 0L);
        clans.set("Clans." + clan + ".pvp", false);
        clans.set("Clans." + clan + ".level", "Sin División");
        clans.set("Clans." + clan + ".allys", Lists.newArrayList());
        inventorys.put(clan, Bukkit.createInventory(null, InventoryType.PLAYER, "Clan Chest"));
        saveClans();
        player.sendMessage(CC.translate("&aClan creado correctamente."));
    }
	
	public static int getMinClanSize() {
    	return Clan.get().getMainConfig().getInt("CLAN.SIZE");
    }
	
    public static void disbandClan(String clan) {
        clans.set("Clans." + clan, null);
        saveClans();
    }
    
    public static void setTotalKills(String clan, int totalKills) {
    	clans.set("Clans." + clan + ".TotalKills", totalKills);
    	saveClans();
    }

    public static void incrementTotalKills(String clan) {
	    clans.set("Clans." + clan + ".TotalKills", (getTotalKills(clan) + 1));
    }
    
    public static int getTotalKills(String clan) {
    	return clans.getInt("Clans." + clan + ".TotalKills");
    }
    
    public static void disbandClan(String clan, Player player) {
        clans.set("Clans." + clan, null);
        saveClans();
        player.sendMessage(CC.translate("&cTu Clan ha sido disbandeado."));
        inventorys.remove(clan);
    }
    
    public static void printClanInformation(Player player, String clan) {
        List<String> list = clans.getStringList("Clans." + clan + ".Members");
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            if (Bukkit.getPlayer(s) != null) builder.append(CC.translate("&a" + s)).append(CC.translate("&7, "));
            else builder.append(CC.translate("&c" + s)).append(CC.translate("&7, "));
        }
    	player.sendMessage(CC.translate(""));
        player.sendMessage(CC.translate("&5&lInformación del Clan"));
        player.sendMessage(CC.translate(" &dNombre: &f" + getLevel(clan).getColor() + clan));
        player.sendMessage(CC.translate(" &dLider: &f" + getClanLeader(clan)));
        player.sendMessage(CC.translate(" &dCo-Lider: &f" + getClanCoLeader(clan)));
        player.sendMessage(CC.translate(" &dModeradores: &f" + getClanModerators(clan)
                .toString().replace("[", "").replace("]", "")));
        player.sendMessage(CC.translate(" &dTotal Kills: &f" + getTotalKills(clan)));
        player.sendMessage(CC.translate(" &dNivel: &f" + getLevel(clan).getColor() + getLevel(clan).getIdentifier()));
        player.sendMessage(CC.translate(" &dAllys: &f" + (getAllys(clan).isEmpty() ? "None" :
                getAllys(clan).toString().replace("[", "").replace("]", ""))));
        player.sendMessage(CC.translate(" &dMiembros: &c(" + getClanMembers(clan) + "/" + getMinClanSize() + ")" + "&r " + builder.substring(0, builder.length()-2)));
        player.sendMessage(CC.translate(""));
    }
    
    public static boolean isAlreadyInvited(String clan, Player player) {
        return clans.getStringList("Clans." + clan + ".Invite").contains(player.getName());
    }

    public static boolean isAlreadyAllyInvited(String clan, String targetClan) {
        return clans.getStringList("Clans." + clan + ".InviteAllys").contains(targetClan);
    }

    public static void sendAllysMessage(String message, String clan) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (isMember(online, clan) || (hasClan(online) && getAllys(clan).contains(getClan(online)))) {
                online.sendMessage(CC.translate(message));
            }
        }
    }
    
	public static void sendClanMessage(String message, String clan) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (isMember(online, clan)) {
                online.sendMessage(CC.translate(message));
            }
        }
    }

    public static void sendCoAndLeaderMessage(String message, String clan) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (isLeader(online, clan) || isCoLeader(online, clan)) {
                online.sendMessage(CC.translate(message));
            }
        }
    }

    public static void sendLeaderMessage(String message, String clan) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (isLeader(online, clan)) {
                online.sendMessage(CC.translate(message));
            }
        }
    }
    
    public static void removeInvite(String clan, Player player) {
        List<String> invite = new ArrayList<String>();
        invite.remove(player.getName());
        clans.set("Clans." + clan + ".Invite", invite);
        saveClans();
    }
    
    public static boolean areMember(Player player, Player target, String clan) {
        return isMember(player, clan) && isMember(target, clan);
    }
    
    public static boolean isMember(OfflinePlayer player, String clan) {
        return clans.getStringList("Clans." + clan + ".Members").contains(player.getName());
    }
    
    public static void addInvite(String clan, Player player) {
        List<String> invite = new ArrayList<>();
        invite.add(player.getName());
        clans.set("Clans." + clan + ".Invite", invite);
        saveClans();
    }
    
    public static String getInvites(String clan) {
        if (alreadyCreated(clan)) {
            List<String> list = clans.getStringList("Clans." + clan + ".Invite");
            if (list.size() != 0) {
                Collections.sort(list);
                clans.set("Clans." + clan + ".Invite", list);
                saveClans();
            }
            return list.toString().replace("[", "").replace("]", "");
        }
        return null;
    }

	public static String getClan(Player player) {
        Iterator<String> localIterator1 = listClans().iterator();
        while (localIterator1.hasNext()) {
            String clan = localIterator1.next();
            if (clans.getString("Clans." + clan + ".Leader").equalsIgnoreCase(player.getName())) {
                return clan;
            }
            if (clans.getString("Clans." + clan + ".Co-Leader").equalsIgnoreCase(player.getName())) {
                return clan;
            }
            if (clans.getString("Clans." + clan + ".Mods").contains(player.getName())) {
                return clan;
            }
            if (clans.getStringList("Clans." + clan + ".Members").contains(player.getName())) {
                return clan;
            }
            localIterator1.hasNext();
        }
        return null;
    }
    
    public static String getClanLeader(String clan) {
        return clans.getString("Clans." + clan + ".Leader");
    }

    public static String getClanCoLeader(String clan) {
        return clans.getString("Clans." + clan + ".Co-Leader");
    }

    public static List<String> getClanModerators(String clan) {
        return clans.getStringList("Clans." + clan + ".Mods");
    }
    
    public static void removeMember(String clan, OfflinePlayer player) {
        List<String> list = clans.getStringList("Clans." + clan + ".Members");
        list.remove(player.getName());
        clans.set("Clans." + clan + ".Members", list);
        if (isModerator(player, clan)) removeModerator(clan, player);
        if (isCoLeader(player, clan)) removeCoLeader(clan);
        saveClans();
    }
    
    public static void addMember(String clan, Player player) {
        List<String> list = clans.getStringList("Clans." + clan + ".Members");
        if (!list.contains(player.getName())) list.add(player.getName());
        clans.set("Clans." + clan + ".Members", list);
        List<String> list2 = clans.getStringList("Clans." + clan + ".Invite");
        list2.remove(player.getName());
        clans.set("Clans." + clan + ".Invite", list2);
        saveClans();
    }
    
    public static String getMembers(String clan) {
        if (alreadyCreated(clan)) {
            List<String> list = clans.getStringList("Clans." + clan + ".Members");
            if (list.size() != 0) {
                Collections.sort(list);
                clans.set("Clans." + clan + ".Members", list);
                saveClans();
            }
            return list.toString().
                    replace("[", "").replace("]", "");
        }
        return null;
    }
    
    public static int getClanMembers(String clan) {
        List<String> list = clans.getStringList("Clans." + clan + ".Members");
        return list.size();
    }
    
    public static boolean hasClan(Player player) {
        return ClanHandler.getClan(player) != null;
    }
    
    public static boolean alreadyCreated(String clan) {
        return clans.contains("Clans." + clan);
    }

	private static boolean existClan(String clan) {
        return getRightClan(ChatColor.stripColor(clan)) != null;
    }
    
    private static String getRightClan(String clan) {
        for (String clan2 : listClans()) {
            if (clan2.equalsIgnoreCase(clan)) {
                return clan2;
            }
        }
        return null;
    }
    
//    public static boolean isLeader(Player player) {
//        for (String clan : listClans()) {
//            if (clans.getString("Clans." + clan + ".Leader").contains(player.getName())) {
//                return true;
//            }
//        }
//        return false;
//    }
    
    public static boolean isLeader(Player player, String clan) {
        return clans.getString("Clans." + clan + ".Leader").contains(player.getName());
    }

//    public static boolean isCoLeader(OfflinePlayer player) {
//        for (String clan : listClans()) {
//            if (clans.getString("Clans." + clan + ".Co-Leader").contains(player.getName())) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static boolean isCoLeader(OfflinePlayer player, String clan) {
        return clans.getString("Clans." + clan + ".Co-Leader").contains(player.getName());
    }

    public static boolean isModerator(OfflinePlayer player, String clan) {
        return clans.getStringList("Clans." + clan + ".Mods").contains(player.getName());
    }
    
    private static boolean isMember(Player player) {
        for (String clan : listClans()) {
            if (clans.getStringList("Clans." + clan + ".Members").contains(player.getName())) {
                return true;
            }
        }
        return false;
    }

    public static void renameClan(String clan, String newName) {
        Map<String, Object> stringMap = Maps.newHashMap();
        clans.getConfigurationSection("Clans." + clan).getKeys(false).forEach(s -> {
            stringMap.put(s, clans.get("Clans." + clan + "." + s));
//            clans.set("Clans." + newName + "." + s, clans.get("Clans." + clan + "." + s));
        });
        stringMap.forEach((s, o) -> clans.set("Clans." + newName + "." + s, o));
//        clans.set("Clans." + clan, null);
        clans.set("Clans." + clan, null);
        saveClans();
    }

    public static void setLeader(String clan, Player leader) {
	    clans.set("Clans." + clan + ".Leader", leader.getName());
	    saveClans();
    }

    public static void setCoLeader(String clan, Player coleader) {
        clans.set("Clans." + clan + ".Co-Leader", coleader.getName());
        saveClans();
    }

    public static void addModerator(String clan, Player mod) {
	    List<String> mods = clans.getStringList("Clans." + clan + ".Mods");
	    mods.add(mod.getName());
        clans.set("Clans." + clan + ".Mods", mods);
        saveClans();
    }

    public static void removeModerator(String clan, OfflinePlayer mod) {
        List<String> mods = clans.getStringList("Clans." + clan + ".Mods");
        mods.remove(mod.getName());
        clans.set("Clans." + clan + ".Mods", mods);
        saveClans();
    }

    public static void removeCoLeader(String clan) {
        clans.set("Clans." + clan + ".Co-Leader", "None");
        saveClans();
    }

    public static void setPvP(String clan, boolean pvp) {
	    clans.set("Clans." + clan + ".pvp", pvp);
	    saveClans();
    }

    public static boolean isPvP(String clan) {
	    return clans.getBoolean("Clans." + clan + ".pvp");
    }

    public static Levels getLevel(String clan) {
	    return Levels.getByIdentifier(clans.getString("Clans." + clan + ".level"));
    }

    public static void setLevel(String clan, Levels value) {
	    clans.set("Clans." + clan + ".level", value.getIdentifier());
    }
    
    public static void checkLevels(String clan) {
        for (Levels value : Levels.values()) {
            if (getTotalKills(clan) >= value.getKills()) {
                setLevel(clan, value);
            }
        }
        saveClans();
    }

    public static void openChest(String clan, Player player) {
        player.openInventory(inventorys.get(clan));
    }
    
    public static Set<String> listClans() {
        return clans.getConfigurationSection("Clans").getKeys(false);
    }

    public static List<String> getAllys(String clan) {
        return clans.getStringList("Clans." + clan + ".allys");
    }

    public static List<String> getInvitedAllys(String clan) {
        return clans.getStringList("Clans." + clan + ".InviteAllys");
    }

    public static void addAllysInvite(String clan, String ally) {
        List<String> allys = clans.getStringList("Clans." + clan + ".InviteAllys");
        allys.add(ally);
        clans.set("Clans." + clan + ".InviteAllys", allys);
        saveClans();
    }

    public static void removeAllysInvite(String clan, String ally) {
        List<String> allys = clans.getStringList("Clans." + clan + ".InviteAllys");
        allys.remove(ally);
        clans.set("Clans." + clan + ".InviteAllys", allys);
        saveClans();
    }

    public static void addAllys(String clan, String ally) {
        List<String> allys = clans.getStringList("Clans." + clan + ".allys");
        allys.add(ally);
        clans.set("Clans." + clan + ".allys", allys);
        saveClans();
    }

    public static void removeAllys(String clan, String ally) {
        List<String> allys = clans.getStringList("Clans." + clan + ".allys");
        allys.remove(ally);
        clans.set("Clans." + clan + ".allys", allys);
        saveClans();
    }
    
    public static void saveClans() {
        Clan.get().getClansConfig().save();
        Clan.get().getClansConfig().reload();
    }
}
