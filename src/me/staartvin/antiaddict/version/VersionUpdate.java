package me.staartvin.antiaddict.version;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import me.staartvin.antiaddict.AntiAddict;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class VersionUpdate implements Listener {

	// Great that you're reading it, Bukkit Moderator/Admin!
	public class VersionNumber {
		int[] version;

		public VersionNumber(final String version) {
			// Split the input string by periods.
			final String[] parts = version.split("\\.");
			// Initialize the field.
			this.version = new int[parts.length];

			// Populate the field.
			for (int i = 0; i < parts.length; i++)
				this.version[i] = Integer.valueOf(parts[i]);
		}
	}

	AntiAddict plugin;

	public VersionUpdate(final AntiAddict plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerLogin(final PlayerJoinEvent event) {
		// Initialise stuff

		// Checks if Version Update Check is permitted.
		if (plugin.getConfig().getBoolean("AntiAddict.NoticeOnVersionUpdate") == false) {
			return;
		}
		final Player player = event.getPlayer();
		final VersionNumber currentVersion = new VersionNumber(plugin
				.getDescription().getVersion());
		final List<String> versionURL = readURL("https://sites.google.com/a/craft-me-in.com/cmi/latest.txt");
		final String lVersion = versionURL.get(0);
		final VersionNumber latestVersion = new VersionNumber(lVersion);

		if (currentVersion.version[0] < latestVersion.version[0]
				|| currentVersion.version[1] < latestVersion.version[1]
				|| currentVersion.version[2] < latestVersion.version[2]) {
			if (player.isOp()
					|| player.hasPermission("antiaddict.versionnotice")) {
				player.sendMessage(ChatColor.GOLD + "[AntiAddict] "
						+ ChatColor.AQUA
						+ " There is an update available. Go to: "
						+ ChatColor.GOLD
						+ "http://dev.bukkit.org/server-mods/antiaddict/");
			}
		}
	}

	public List<String> readURL(final String url) {
		try {
			final URL site = new URL(url);
			final URLConnection urlC = site.openConnection();
			final BufferedReader in = new BufferedReader(new InputStreamReader(
					urlC.getInputStream()));

			final List<String> lines = new ArrayList<String>();
			String line;
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}

			in.close();

			return lines;
		} catch (final MalformedURLException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
