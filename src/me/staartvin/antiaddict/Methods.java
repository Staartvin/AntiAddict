package me.staartvin.antiaddict;

import java.util.Calendar;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Methods {

	public AntiAddict plugin;

	public Methods(final AntiAddict instance) {
		plugin = instance;
	}

	// Show time of sender disguised as a player
	public void calculateTimeForSender(final Player player) {

		if ((player instanceof Player)) {
			final String playerName = player.getName();

			if (plugin.getdCon().isNotDone(playerName)
					.equalsIgnoreCase("false")) {
				return;
			}

			plugin.getdCon().isNotDone(playerName, "true");

			// Calculate time
			plugin.getdCon().calculateRestTime(player);

			// When the time left is lower or equal to 1:
			player.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");

			if (plugin.getdCon().getRestTimeInMinutes(playerName) <= 1) {
				player.sendMessage(ChatColor.AQUA
						+ "You have "
						+ plugin.getMethods().singularOrPlural(
								plugin.getdCon().getRestTimeInMinutes(
										playerName)) + " left today!");
				player.sendMessage(ChatColor.AQUA
						+ "You will be kicked shortly!");
			} else {
				player.sendMessage(ChatColor.AQUA + "You have "
						+ plugin.getdCon().getRestTimeInMinutes(playerName)
						+ " minutes left today!");
			}
		} else {
			System.out.println(ChatColor.AQUA
					+ "This command can only be used ingame!");
		}
	}

	public void disableAntiAddict(final CommandSender sender) {
		if ((sender.hasPermission("antiaddict.admin")) || (sender.isOp())) {
			if (plugin.isStatus() == true) {
				plugin.setStatus(false);
				sender.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");
				sender.sendMessage(ChatColor.AQUA
						+ "AntiAddict has been disabled!");
			} else {
				sender.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");
				sender.sendMessage(ChatColor.AQUA
						+ "AntiAddict has already been disabled!");
			}
		} else {
			sender.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");
			sender.sendMessage(ChatColor.AQUA + "You are not an Admin...");
		}
	}

	public void enableAntiAddict(final CommandSender sender) {
		if ((sender.hasPermission("antiaddict.admin")) || (sender.isOp())) {
			if (plugin.isStatus() == false) {
				plugin.setStatus(true);
				sender.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");
				sender.sendMessage(ChatColor.AQUA
						+ "AntiAddict has been enabled!");
			} else {
				sender.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");
				sender.sendMessage(ChatColor.AQUA
						+ "AntiAddict has already been enabled!");
			}
		} else {
			sender.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");
			sender.sendMessage(ChatColor.AQUA + "You are not an Admin...");
		}
	}

	public long getDate() {

		final Calendar cal = Calendar.getInstance();
		final int day = cal.get(Calendar.DATE);
		final int month = cal.get(Calendar.MONTH) + 1;
		final int year = cal.get(Calendar.YEAR);
		final long dateFormat = day + month + year;
		return dateFormat;
	}

	public void showTimeLeftForSender(final CommandSender sender) {
		if ((sender instanceof Player)) {
			final Player player = ((Player) sender).getPlayer();
			final Location loc = player.getLocation();

			if (plugin.isUseMultiWorldSetup()) {

				if (plugin.getEWs() != null
						&& !plugin.getEWs().contains(loc.getWorld().getName())) {

					if (plugin.getGroupHandler().isInAGroup(player)
							|| (plugin.isLimitall())
							&& (!player
									.hasPermission("antiaddict.ignorelimits"))) {
						calculateTimeForSender(player);
					} // Closes Permissions Part
					else {
						sender.sendMessage(ChatColor.AQUA
								+ "You are not listed as addicted.");
						sender.sendMessage(ChatColor.AQUA + "Keep it up!");
					}
				} else {
					sender.sendMessage(ChatColor.AQUA
							+ "There is not being monitored in this world.");
					sender.sendMessage(ChatColor.AQUA + "Lucky you!");
				}
			} else {
				if (plugin.getGroupHandler().isInAGroup(player)
						|| (plugin.isLimitall())
						&& (!player.hasPermission("antiaddict.ignorelimits"))) {
					calculateTimeForSender(player);
				} else {
					sender.sendMessage(ChatColor.AQUA
							+ "You are not listed as addicted.");
					sender.sendMessage(ChatColor.AQUA + "Keep it up!");
				}
			}

		} // Closes instance of test
		else {
			System.out.println("This command can only be used in-game!");
		}
	}

	public void showUsage(final CommandSender sender, final int page) {
		final int maxPage = 2;
		if (page == 1) {
			sender.sendMessage(ChatColor.GOLD + "--- [Page 1 of " + maxPage
					+ "] ---");
			sender.sendMessage(ChatColor.GOLD + "Commands: " + ChatColor.GRAY
					+ " <required>   (optional)");
			sender.sendMessage(ChatColor.AQUA + "/antiaddict on"
					+ ChatColor.GRAY + " - Enables AntiAddict.");
			sender.sendMessage(ChatColor.AQUA + "/antiaddict off"
					+ ChatColor.GRAY + " - Disables AntiAddict.");
			sender.sendMessage(ChatColor.AQUA + "/antiaddict status"
					+ ChatColor.GRAY
					+ " - Shows the status of AntiAddict (on/off).");
			sender.sendMessage(ChatColor.AQUA
					+ "/antiaddict left"
					+ ChatColor.GRAY
					+ " - Shows how much time you have left before getting kicked.");
			sender.sendMessage(ChatColor.AQUA + "/antiaddict about"
					+ ChatColor.GRAY
					+ " - Shows all helpful information about AntiAddict.");
			sender.sendMessage(ChatColor.GOLD + "/antiaddict help (page)"
					+ ChatColor.GRAY + " for a page.");
		} else if (page == 2) {
			sender.sendMessage(ChatColor.GOLD + "--- [Page 2 of " + maxPage
					+ "] ---");
			sender.sendMessage(ChatColor.AQUA + "/antiaddict config reload"
					+ ChatColor.GRAY + " - Reloads AntiAddict's config.");
			sender.sendMessage(ChatColor.AQUA
					+ "/antiaddict language set <language code> (player)"
					+ ChatColor.GRAY
					+ " - Changes language to specified language for (player) if specified.");
			sender.sendMessage(ChatColor.AQUA + "/antiaddict language list"
					+ ChatColor.GRAY
					+ " - Shows all available language in a simple list.");
			sender.sendMessage(ChatColor.AQUA + "/antiaddict info <player>"
					+ ChatColor.GRAY + " - Shows information about a player.");
			sender.sendMessage(ChatColor.GOLD + "/antiaddict help (page)"
					+ ChatColor.GRAY + " for a page.");
		} else {
			sender.sendMessage(ChatColor.GOLD
					+ "There seems to be nothing at this page!");
		}
	}

	public String singularOrPlural(final long time) {
		if (time == 1)
			return time + " minute";
		else
			return time + " minutes";
	}
}
