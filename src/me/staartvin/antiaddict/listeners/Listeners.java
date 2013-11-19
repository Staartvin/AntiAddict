package me.staartvin.antiaddict.listeners;

import java.util.HashMap;

import me.staartvin.antiaddict.AntiAddict;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {

	public HashMap<String, Boolean> taskBusy = new HashMap<String, Boolean>();
	public HashMap<String, Boolean> taskBusy2 = new HashMap<String, Boolean>();
	public HashMap<String, Long> triedToJoinTimes = new HashMap<String, Long>();

	// 1 minute
	private final long refreshTime = 1200;

	AntiAddict plugin;
	int count = 0;

	public Listeners(final AntiAddict plugin) {
		this.plugin = plugin;
	}

	// When a player moves
	public void calculateTimeLeft(final Player player) {
		final String playerName = player.getName();

		// A new day has arrived!
		if (plugin.getdCon().isItANewDay(playerName)) {
			plugin.getdCon().resetData(player);
			player.kickPlayer(ChatColor.GOLD
					+ "[AntiAddict]"
					+ ChatColor.AQUA
					+ "\nA new day has arrived!\nAll times are reset!\nJust login again!");
			return;
		}

		// Adjust date so it is correct.
		plugin.getdCon().adjustDate(playerName);

		if (taskBusy.get(playerName) == null) {
			taskBusy.put(playerName, false);
		}
		if (taskBusy.get(playerName)) {

			return;
		} else if (taskBusy.get(playerName) == false) {
			taskBusy.put(playerName, true);
		}
		if (taskBusy2.get(playerName) == null) {
			taskBusy2.put(playerName, false);
		}
		// Calculate time every 1200 ticks. (60 seconds) Instead of a calculation
		// every tick.
		if (taskBusy2.get(playerName) == false) {
			taskBusy2.put(playerName, true);
		} else if (taskBusy2.get(playerName)) {
			return;
		}

		if (plugin.getdCon().isNotDone(playerName).equalsIgnoreCase("false")) {
			return;
		}

		plugin.getdCon().isNotDone(playerName, "true");

		plugin.getdCon().calculateRestTime(player);

		if (plugin.getdCon().getRestTimeInMinutes(playerName) <= 1
				&& plugin.getdCon().isWarnMessage(playerName)
						.equalsIgnoreCase("false")) {
			plugin.getdCon().isWarnMessage(playerName, "true");

			player.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");
			player.sendMessage(ChatColor.AQUA
					+ plugin.getTranslationConfig()
							.getConfig()
							.getString(
									plugin.getdCon().getLanguage(playerName)
											+ ".AlmostKickedMessage.Part1"));
			player.sendMessage(ChatColor.AQUA
					+ plugin.getTranslationConfig()
							.getConfig()
							.getString(
									plugin.getdCon().getLanguage(playerName)
											+ ".AlmostKickedMessage.Part2"));
		}
		if (plugin.getdCon().getRestTimeInMinutes(playerName) <= 0) {
			plugin.getdCon().isNotDone(playerName, "false");
			player.kickPlayer(plugin
					.getTranslationConfig()
					.getConfig()
					.getString(
							plugin.getdCon().getLanguage(playerName)
									+ ".LimitKickMessage"));
			plugin.getdCon().isWarnMessage(playerName, "false");
			plugin.getdCon().isBanned(playerName, "true");
			System.out
					.println("[AntiAddict] "
							+ playerName
							+ " reached his/her daily limit and was kicked into RL again.");
		}

		plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {

			@Override
			public void run() {

				final String playerName = player.getName();
				taskBusy.put(playerName, false);
				taskBusy2.put(playerName, false);
			}
		}, refreshTime);

	}

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		if (plugin.isStatus()) {
			if (plugin.isUseMultiWorldSetup()) {

				if (plugin.getEWs() != null
						&& !plugin.getEWs().contains(
								event.getPlayer().getLocation().getWorld()
										.getName())) {

					if (plugin.getGroupHandler().isInAGroup(player)
							|| (plugin.isLimitall())
							&& (!player
									.hasPermission("antiaddict.ignorelimits"))) {

						WelcomePlayer(player);
					}
				}
			} // End of UseMultiWorldSetup
			else {
				if (plugin.getGroupHandler().isInAGroup(player)
						|| (plugin.isLimitall())
						&& (!player.hasPermission("antiaddict.ignorelimits"))) {

					WelcomePlayer(player);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerMove(final PlayerMoveEvent event) {
		final Player player = event.getPlayer();

		if (plugin.isStatus()) {
			if (plugin.isUseMultiWorldSetup()) {

				if (plugin.getEWs() != null
						&& !plugin.getEWs().contains(
								event.getPlayer().getLocation().getWorld()
										.getName())) {

					if (plugin.getGroupHandler().isInAGroup(player)
							|| (plugin.isLimitall())
							&& (!player
									.hasPermission("antiaddict.ignorelimits"))) {
						calculateTimeLeft(player);
					}
				}
			} else {

				if (plugin.getGroupHandler().isInAGroup(player)
						|| (plugin.isLimitall())
						&& (!player.hasPermission("antiaddict.ignorelimits"))) {

					calculateTimeLeft(player);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		final String playerName = player.getName();

		plugin.getdCon().isWarnMessage(playerName, "false");

		if (plugin.isStatus()) {
			if (plugin.isUseMultiWorldSetup()) {

				if (plugin.getEWs() != null
						&& !plugin.getEWs().contains(
								event.getPlayer().getLocation().getWorld()
										.getName())) {

					if (plugin.getGroupHandler().isInAGroup(player)
							|| (plugin.isLimitall())
							&& (!player
									.hasPermission("antiaddict.ignorelimits"))) {

						plugin.getdCon().calculateRestTime(player);
						plugin.getdCon().setLastDifference(playerName, 0L);
					}
				}
			} else {
				if (plugin.getGroupHandler().isInAGroup(player)
						|| (plugin.isLimitall())
						&& (!player.hasPermission("antiaddict.ignorelimits"))) {

					plugin.getdCon().calculateRestTime(player);
					plugin.getdCon().setLastDifference(playerName, 0L);
				}
			}
		}
	}

	// When a player joins
	public void WelcomePlayer(final Player player) {
		final String playerName = player.getName();

		// If player is addicted but not listed in the database
		if (!plugin.getdCon().isInDatabase(playerName)
				&& plugin.getGroupHandler().isInAGroup(player)) {
			plugin.getdCon().createNewEntry(player);
		}

		// A new day has arrived!
		if (plugin.getdCon().isItANewDay(playerName)) {
			plugin.getdCon().resetData(player);
			player.kickPlayer(ChatColor.GOLD
					+ "[AntiAddict]"
					+ ChatColor.AQUA
					+ "\nA new day has arrived!\nAll times are reset!\nJust login again!");
			return;
		}

		// Adjust date so it is correct.
		plugin.getdCon().adjustDate(playerName);

		// Check for reputation
		if (plugin.getdCon().getReputation(playerName) == 0) {
			// TODO: Methods for reputation when it is 0.
		} else if (plugin.getdCon().getReputation(playerName) <= plugin
				.getConfig().getInt("AntiAddict.ReputationPointsBan")) {
			player.kickPlayer("You can't join '"
					+ plugin.getServer().getServerName()
					+ "' anymore because \nyour reputation is under "
					+ plugin.getConfig().getInt(
							"AntiAddict.ReputationPointsBan")
					+ ". \nContact an admin for help.");
			System.out
					.print("[AntiAddict] "
							+ playerName
							+ " is banned from this server because his/her reputation is "
							+ plugin.getdCon().getReputation(playerName));
			System.out.print("[AntiAddict] If you want to allow "
					+ playerName
					+ " access, raise their reputation to anything above "
					+ plugin.getConfig().getInt(
							"AntiAddict.ReputationPointsBan") + ".");
			return;
		}

		// If the player is banned, do certain actions
		if (plugin.getdCon().isBanned(playerName).equalsIgnoreCase("true")) {
			if (triedToJoinTimes.get(playerName) == null) {
				triedToJoinTimes.put(playerName, 0L);
			}
			// If the player tried to login three times in a row, its reputation get lowered with 0.5
			if (triedToJoinTimes.get(playerName) >= 3) {
				plugin.getdCon().setReputation(playerName,
						plugin.getdCon().getReputation(playerName) - 1);
				player.kickPlayer("You have been trying to login for more than 3 times. \nYour reputation is now "
						+ plugin.getdCon().getReputation(playerName));
				return;
			}
			// Add one to the tried times
			triedToJoinTimes.put(playerName,
					(triedToJoinTimes.get(playerName) + 1));
			player.kickPlayer(plugin
					.getTranslationConfig()
					.getConfig()
					.getString(
							plugin.getdCon().getLanguage(playerName)
									+ ".KickMessageWhenTryingToLoginAgain"));
			return;
		}

		plugin.getdCon().setJoinTime(playerName, System.currentTimeMillis());

		// Do we need to output info when an addict joins
		if (plugin.getConfig().getBoolean(
				"AntiAddict.ShowWhenAddictJoinsServer")) {
			System.out.println("[AntiAddict] The player " + playerName
					+ " just logged in.");
			System.out.println("[AntiAddict] (S)he was assigned to group '"
					+ plugin.getGroupHandler().getGroup(player)
					+ "', so his/her playtime");
			System.out.println("[AntiAddict] is restricted to "
					+ plugin.getGroupHandler().getShowTimeLimit(
							plugin.getGroupHandler().getGroup(player))
					+ " minutes.");
		}

		// Send the player a message!
		player.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");
		player.sendMessage(ChatColor.AQUA
				+ plugin.getTranslationConfig()
						.getConfig()
						.getString(
								plugin.getdCon().getLanguage(playerName)
										+ ".JoinMessage")
						.replace(
								"%minutes%",
								plugin.getGroupHandler().getShowTimeLimit(
										plugin.getGroupHandler().getGroup(
												player))
										+ ""));
	}
}