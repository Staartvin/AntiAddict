package me.staartvin.antiaddict.vault;

import me.staartvin.antiaddict.AntiAddict;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Handles all connections with Vault
 * 
 * @author Vincent
 * 
 */
public class VaultHandler {

	private final AntiAddict plugin;
	public static Permission permission = null;

	public VaultHandler(final AntiAddict plugin) {
		this.plugin = plugin;
	}

	public boolean setupPermissions() {
		final RegisteredServiceProvider<Permission> permissionProvider = plugin
				.getServer()
				.getServicesManager()
				.getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

}
