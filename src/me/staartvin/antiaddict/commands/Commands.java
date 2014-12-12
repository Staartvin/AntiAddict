package me.staartvin.antiaddict.commands;

import me.staartvin.antiaddict.AntiAddict;
import me.staartvin.antiaddict.database.DatabaseConnector;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	public AntiAddict plugin;

	public Commands(final AntiAddict plugin) {
		this.plugin = plugin;
	}

	public boolean hasPermission(final String permission,
			final CommandSender sender) {
		if (!sender.hasPermission(permission)) {
			sender.sendMessage(ChatColor.RED + "You need to have ("
					+ permission + ") to do this!");
			return false;
		}
		return true;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd,
			final String commandLabel, final String[] args) {

		if (args.length <= 0) {
			if (!hasPermission("antiaddict.admin", sender)) {
				return true;
			}
			plugin.getMethods().showUsage(sender, 1);
			return true;
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("help")) {
				if (!hasPermission("antiaddict.admin", sender)) {
					return true;
				}
				plugin.getMethods().showUsage(sender, 1);
				return true;
			} else if (args[0].equalsIgnoreCase("on")) {
				if (!hasPermission("antiaddict.admin", sender)) {
					return true;
				}
				plugin.getMethods().enableAntiAddict(sender);
				return true;
			} else if (args[0].equalsIgnoreCase("off")) {
				if (!hasPermission("antiaddict.admin", sender)) {
					return true;
				}
				plugin.getMethods().disableAntiAddict(sender);
				return true;
			} else if (args[0].equalsIgnoreCase("secret")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage("[AntiAddict] "
							+ "This command can only be performed by a player!");
					return true;
				} else {
					final Player player = (Player) sender;
					final World world = player.getWorld();
					final Location location = player.getLocation();
					world.strikeLightning(location);
					player.sendMessage(ChatColor.AQUA
							+ "Thou shall be smitten!");
				}
			} else if (args[0].equalsIgnoreCase("left")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "[AntiAddict] "
							+ "This command can only be performed by a player!");
					return true;
				}
				if (!hasPermission("antiaddict.lefttime", sender)) {
					sender.sendMessage(ChatColor.AQUA
							+ "You don't have the sufficient access to perform this command!");
					return true;
				}
				plugin.getMethods().showTimeLeftForSender(sender);
				return true;
			} else if (args[0].equalsIgnoreCase("language")) {
				if (!hasPermission("antiaddict.language", sender)
						|| !hasPermission("antiaddict.language.set", sender)
						|| !hasPermission("antiaddict.language.setother",
								sender)) {
					return true;
				}

				sender.sendMessage(ChatColor.AQUA + "Incorrect command use!");
				sender.sendMessage(ChatColor.GRAY
						+ "/antiaddict language set <language code> (player)");
				sender.sendMessage(ChatColor.GRAY + "/antiaddict language list");
				return true;
			} else if (args[0].equalsIgnoreCase("about")) {
				if (!hasPermission("antiaddict.admin", sender)) {
					return true;
				}
				sender.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");
				sender.sendMessage(ChatColor.AQUA + "Version of AntiAddict: "
						+ ChatColor.GRAY + plugin.getDescription().getVersion());
				sender.sendMessage(ChatColor.AQUA + "Developer of AntiAddict: "
						+ ChatColor.GRAY + plugin.getDescription().getAuthors());
				sender.sendMessage(ChatColor.AQUA
						+ "Thanks for Phiwa (original developer) for the code. He couldn't update anymore. ");
				sender.sendMessage(ChatColor.AQUA
						+ "Thank you for using AntiAddict plugin.");
				sender.sendMessage(ChatColor.AQUA
						+ "Any questions? Send them to " + ChatColor.GRAY
						+ "mijnleraar@gmail.com");
				return true;
			} else if (args[0].equalsIgnoreCase("status")) {
				if (!hasPermission("antiaddict.admin", sender)) {
					return true;
				}
				if (plugin.isStatus()) {
					sender.sendMessage(ChatColor.AQUA + "AntiAddict is "
							+ ChatColor.GREEN + "on");
				} else {
					sender.sendMessage(ChatColor.AQUA + "AntiAddict is "
							+ ChatColor.RED + "off");
				}
				return true;
			} // args.length = 2
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("config")) {
				if (args.length != 1) {
					if (!hasPermission("antiaddict.admin", sender)) {
						return true;
					}
					if (args[1].equalsIgnoreCase("reload")) {
						plugin.reloadConfigs();
						sender.sendMessage(ChatColor.GOLD + "[AntiAddict]"
								+ ChatColor.AQUA + " Configs reloaded!");
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.GOLD + "[AntiAddict]"
							+ ChatColor.AQUA
							+ " Correct usage: /antiaddict config reload!");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("language")) {
				if (args[1].equalsIgnoreCase("list")) {
					if (!hasPermission("antiaddict.language", sender)) {
						return true;
					}
					sender.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");
					sender.sendMessage(ChatColor.AQUA + "English: "
							+ ChatColor.GRAY + "en_US");
					sender.sendMessage(ChatColor.AQUA
							+ "Português brasileiro: " + ChatColor.GRAY
							+ "pt_BR");
					sender.sendMessage(ChatColor.AQUA + "le Français: "
							+ ChatColor.GRAY + "fr_FR");
					sender.sendMessage(ChatColor.AQUA + "Nederlands: "
							+ ChatColor.GRAY + "nl_NL");
					sender.sendMessage(ChatColor.AQUA + "Deutsch: "
							+ ChatColor.GRAY + "de_DE");
					sender.sendMessage(ChatColor.AQUA + "Italiano: "
							+ ChatColor.GRAY + "it_IT");
					sender.sendMessage(ChatColor.AQUA + "Nihongo: "
							+ ChatColor.GRAY + "ja_jp");
					sender.sendMessage(ChatColor.AQUA + "Hangugeo: "
							+ ChatColor.GRAY + "ko_KR");
					sender.sendMessage(ChatColor.AQUA + "Español americano: "
							+ ChatColor.GRAY + "es_MX");
					sender.sendMessage(ChatColor.AQUA + "polszczyzna: "
							+ ChatColor.GRAY + "pl_PL");
					sender.sendMessage(ChatColor.AQUA + "Fántizì: "
							+ ChatColor.GRAY + "zh_CN");
					sender.sendMessage(ChatColor.AQUA + "Español: "
							+ ChatColor.GRAY + "es_ES");
					sender.sendMessage(ChatColor.AQUA + "Jiantizì: "
							+ ChatColor.GRAY + "zh_TW");
					sender.sendMessage(ChatColor.AQUA + "Dansk sprog: "
							+ ChatColor.GRAY + "da_DK");
					return true;
				} else {
					sender.sendMessage(ChatColor.AQUA
							+ "See /antiaddict help for help");
				}
			} else if (args[0].equalsIgnoreCase("info")) {
				if (!hasPermission("antiaddict.infoplayer", sender)) {
					return true;
				}
				if (args.length != 2) {
					sender.sendMessage(ChatColor.GOLD + "[AntiAddict]"
							+ ChatColor.AQUA
							+ " Correct usage: /antiaddict info <player>!");
					return true;
				} else {
					showInformation(sender, args[1]);
					return true;
				}
			} else if (args[0].equalsIgnoreCase("help")) {
				if (!hasPermission("antiaddict.admin", sender)) {
					return true;
				}
				int page;
				try {
					page = Integer.parseInt(args[1]);
				} catch (final Exception e) {
					sender.sendMessage(ChatColor.RED + "'" + args[1]
							+ "' is not a number!");
					return true;
				}
				plugin.getMethods().showUsage(sender, page);
				return true;
			}
		} // Args Length = 3
		else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("language")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "[AntiAddict] "
							+ "This command can only be performed by a player!");
					return true;
				}
				if (args[1].equalsIgnoreCase("set")) {
					if (!hasPermission("antiaddict.language.set", sender)) {
						return true;
					}
					sender.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");
					plugin.getLoadConfiguration().changeLanguage(sender,
							args[2], null);
					return true;
				} else {
					sender.sendMessage(ChatColor.AQUA
							+ "See /antiaddict help for help");
				}
			}// Args.length = 4
		} else if (args.length == 4) {
			if (args[0].equalsIgnoreCase("language")) {
				if (args[3].equalsIgnoreCase(sender.getName())) {
					if (!hasPermission("antiaddict.language.set", sender)) {
						return true;
					}
				} else if (!hasPermission("antiaddict.language.setother",
						sender)) {
					return true;
				}
				if (args[1].equalsIgnoreCase("set")) {
					sender.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");
					plugin.getLoadConfiguration().changeLanguage(sender,
							args[2], args[3]);
					return true;
				}
			}
		}
		sender.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");
		sender.sendMessage(ChatColor.AQUA + "Could not find command!");
		return true;
	}

	@SuppressWarnings("deprecation")
	public boolean showInformation(final CommandSender sender, String playerName) {

		final DatabaseConnector dCon = plugin.getdCon();
		Player player;
		String group = "";

		if (!dCon.isInDatabase(playerName)) {
			sender.sendMessage(ChatColor.AQUA + playerName
					+ " is not listed as an addict!");
			return true;
		}

		playerName = dCon.getCorrectName(playerName);

		// If the player is online, calculate its playtime.
		if (plugin.getServer().getPlayer(playerName) != null) {

			player = plugin.getServer().getPlayer(playerName);
			group = plugin.getGroupHandler().getGroup(player);

			plugin.getdCon().calculateRestTime(player);
		}

		sender.sendMessage(ChatColor.GOLD + "--- [AntiAddict] ---");
		sender.sendMessage(ChatColor.AQUA + "Info about: " + ChatColor.GRAY
				+ playerName);
		if (group != null) {
			// Do not check time left if player is exempted
			sender.sendMessage(ChatColor.AQUA
					+ "Time left: "
					+ ChatColor.GRAY
					+ plugin.getMethods().singularOrPlural(
							dCon.getRestTimeInMinutes(playerName)));
		}
		sender.sendMessage(ChatColor.AQUA
				+ "Total playtime: "
				+ ChatColor.GRAY
				+ plugin.getMethods().singularOrPlural(
						dCon.getPlayTimeInMinutes(playerName)));
		sender.sendMessage(ChatColor.AQUA + "Reputation: " + ChatColor.GRAY
				+ dCon.getReputation(playerName));
		sender.sendMessage(ChatColor.AQUA + "Language: " + ChatColor.GRAY
				+ dCon.getLanguage(playerName));
		if (group != null && !group.equalsIgnoreCase("")) {
			sender.sendMessage(ChatColor.AQUA + "Group: " + ChatColor.GRAY
					+ group);
		}
		return true;

	} // Closes instance of test
}
