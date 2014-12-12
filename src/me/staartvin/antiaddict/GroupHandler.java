package me.staartvin.antiaddict;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

public class GroupHandler {

	public AntiAddict plugin;

	public GroupHandler(final AntiAddict instance) {
		plugin = instance;
	}

	public String getGroup(final Player player) {
		if (!isInAGroup(player))
			return null;

		for (final String group : getGroups()) {
			if (player.hasPermission("antiaddict.groups." + group)) {
				return group;
			}
		}
		return null;
	}

	public List<String> getGroups() {
		final Set<String> groups = plugin.getGroupConfig().getConfig()
				.getConfigurationSection("Groups").getKeys(false);
		final List<String> groupList = new ArrayList<String>();

		for (final String group : groups) {
			groupList.add(group);
		}

		return groupList;
	}

	public Integer getShowTimeLimit(final String group) {
		return plugin.getGroupConfig().getConfig()
				.getInt("Groups." + group + ".time limit");
	}

	public Integer getTimeLimit(final String group) {
		// We add +1 for a correction in time. If I start with 300 it automatically removes 1, leaving us with 299 minutes left.
		return plugin.getGroupConfig().getConfig()
				.getInt("Groups." + group + ".time limit") + 1;
	}

	public Integer getTimeLimitMil(final String group) {
		return (plugin.getGroupConfig().getConfig()
				.getInt("Groups." + group + ".time limit") + 1) * 60000;
	}

	public boolean isInAGroup(final Player player) {
		// OP's don't have any restricted time
		if (player.isOp()) {
			return false;
		}
		
		for (final String group : getGroups()) {
			if (player.hasPermission("antiaddict.groups." + group)) {
				return true;
			}
		}
		return false;
	}
}
