package me.staartvin.antiaddict.version;

import me.staartvin.antiaddict.AntiAddict;
import me.staartvin.antiaddict.version.Updater.UpdateResult;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateHandler implements Listener {
	AntiAddict plugin;

	public UpdateHandler(final AntiAddict plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent event) {
		// Initialise stuff
		final Player player = event.getPlayer();
		
		if (!player.hasPermission("antiaddict.versionnotice")) return;
		
		// Checks if Version Update Check is permitted.
		if (!plugin.getConfig().getBoolean("AntiAddict.NoticeOnVersionUpdate")) return;
		
		final Updater updater = plugin.getLatestUpdater();
		
		if (updater.getResult().equals(UpdateResult.UPDATE_AVAILABLE)) {
			// Schedule it later so it will appear at the bottom
			plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					player.sendMessage(ChatColor.GREEN + updater.getLatestName() + ChatColor.GOLD + " is now available for download!");
					player.sendMessage(ChatColor.GREEN + "Available at: " + ChatColor.GOLD + updater.getLatestFileLink());
				}
				
			}, 10L);
		}

	}

}
