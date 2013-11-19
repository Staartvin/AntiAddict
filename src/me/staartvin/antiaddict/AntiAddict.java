package me.staartvin.antiaddict;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import me.staartvin.antiaddict.commands.Commands;
import me.staartvin.antiaddict.config.Config;
import me.staartvin.antiaddict.config.LoadConfiguration;
import me.staartvin.antiaddict.database.DatabaseConnector;
import me.staartvin.antiaddict.database.SqLiteDatabase;
import me.staartvin.antiaddict.listeners.Listeners;
import me.staartvin.antiaddict.vault.VaultHandler;
import me.staartvin.antiaddict.version.VersionUpdate;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class AntiAddict extends JavaPlugin {

	private boolean status = true;
	private boolean limitall;
	private boolean UseMultiWorldSetup;
	private List<String> EWs;

	// Config
	private final double translationsVersion = 1.4;

	private final Config translationConfig = new Config(this, "",
			"translations.yml");
	private final Config mainConfig = new Config(this, "", "config.yml");
	private final Config groupConfig = new Config(this, "", "groups.yml");

	private VaultHandler vaultHandler;

	private LoadConfiguration loadConfiguration = new LoadConfiguration(this);
	private Methods methods = new Methods(this);
	private Listeners listeners = new Listeners(this);
	private GroupHandler groupHandler = new GroupHandler(this);
	private DatabaseConnector dCon = new DatabaseConnector(this);

	@Override
	public FileConfiguration getConfig() {
		return mainConfig.getConfig();
	}

	@Override
	public List<Class<?>> getDatabaseClasses() {
		final List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(SqLiteDatabase.class);
		return list;
	}

	public DatabaseConnector getdCon() {
		return dCon;
	}

	public List<String> getEWs() {
		return EWs;
	}

	public Config getGroupConfig() {
		return groupConfig;
	}

	public GroupHandler getGroupHandler() {
		return groupHandler;
	}

	public Listeners getListeners() {
		return listeners;
	}

	public LoadConfiguration getLoadConfiguration() {
		return loadConfiguration;
	}

	public Config getMainConfig() {
		return mainConfig;
	}

	public Methods getMethods() {
		return methods;
	}

	public Config getTranslationConfig() {
		return translationConfig;
	}

	public boolean isLimitall() {
		return limitall;
	}

	public boolean isStatus() {
		return status;
	}

	public boolean isUseMultiWorldSetup() {
		return UseMultiWorldSetup;
	}

	@Override
	public void onDisable() {

		// Reload files
		mainConfig.reloadConfig();
		translationConfig.reloadConfig();
		groupConfig.reloadConfig();
		
		// Save files
		mainConfig.saveConfig();
		translationConfig.saveConfig();
		groupConfig.saveConfig();

		getServer().getScheduler().cancelTasks(this);

		getLogger().info("AntiAddict has been disabled!");
	}

	@Override
	public void onEnable() {

		getLogger().info("AntiAddict v" + getDescription().getVersion() + " by Staartvin");

		// Create files
		translationConfig.createNewFile("Translation config loaded!",
				"Translations File v" + translationsVersion
						+ " ---- To change a message, change it here.");

		mainConfig.createNewFile("Main config loaded!", "AntiAddict v"
				+ getDescription().getVersion() + " Config");

		groupConfig
				.createNewFile(
						"Group config loaded!",
						"Group config"
								+ "\nTime limit is defined in minutes."
								+ "\nTo assign a player to a group, give them the permission with the group name."
								+ "\nExample: When you give Herobrine antiaddict.groups.Default it will assign Herobrine to the Default group."
								+ "\nNames are case-sensitive. So antiaddict.groups.default wouldn't work.");

		// Load files
		getLoadConfiguration().loadMainConfig();
		getLoadConfiguration().loadTranslationsConfig();
		getLoadConfiguration().loadGroupsConfig();

		// Check for vault
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			getLogger().severe("Could not find Vault! Will shutdown...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		} else {
			vaultHandler = new VaultHandler(this);

			if (vaultHandler.setupPermissions()) {
				getLogger().info("Vault is hooked!");
			} else {
				getLogger().severe(
						"Vault found but unable to hook! Will shutdown...");
				getServer().getPluginManager().disablePlugin(this);
				return;
			}
		}

		setupDatabase();

		// Register Events
		getServer().getPluginManager().registerEvents(new VersionUpdate(this),
				this);
		getServer().getPluginManager()
				.registerEvents(new Listeners(this), this);

		// Register Command Executor
		getCommand("antiaddict").setExecutor(new Commands(this));

		try {
			final Metrics metrics = new Metrics(this);
			metrics.start();
			System.out
					.print("[AntiAddict] Metrics has started. For info check: http://metrics.griefcraft.com/plugin/AntiAddict");
		} catch (final IOException e) {
			// Failed to submit the stats :-(
			getLogger().log(Level.WARNING,
					"[AntiAddict] Metrics could not get started!");
		}

		getLogger().info("AntiAddict has been enabled!");
	}

	@Override
	public void reloadConfig() {
		mainConfig.reloadConfig();
	}

	public void reloadConfigs() {
		// Reload main config
		reloadConfig();
		saveConfig();

		// Reload translation config
		translationConfig.reloadConfig();
		translationConfig.saveConfig();

		// Reload group config
		groupConfig.reloadConfig();
		groupConfig.saveConfig();
	}

	@Override
	public void saveConfig() {
		mainConfig.saveConfig();
	}

	public void setdCon(final DatabaseConnector dCon) {
		this.dCon = dCon;
	}

	public void setEWs(final List<String> eWs) {
		EWs = eWs;
	}

	public void setGroupHandler(final GroupHandler groupHandler) {
		this.groupHandler = groupHandler;
	}

	public void setLimitall(final boolean limitall) {
		this.limitall = limitall;
	}

	public void setListeners(final Listeners listeners) {
		this.listeners = listeners;
	}

	public void setLoadConfiguration(final LoadConfiguration loadConfiguration) {
		this.loadConfiguration = loadConfiguration;
	}

	public void setMethods(final Methods methods) {
		this.methods = methods;
	}

	public void setStatus(final boolean status) {
		this.status = status;
	}

	private void setupDatabase() {
		try {
			getDatabase().find(SqLiteDatabase.class).findRowCount();
		} catch (final Exception e) {
			System.out.print("Installing Database for the first time!");
			installDDL();
		}
	}

	public void setUseMultiWorldSetup(final boolean useMultiWorldSetup) {
		UseMultiWorldSetup = useMultiWorldSetup;
	}
}
