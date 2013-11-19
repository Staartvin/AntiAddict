package me.staartvin.antiaddict.database;

import me.staartvin.antiaddict.AntiAddict;

import org.bukkit.entity.Player;

public class DatabaseConnector {

	AntiAddict plugin;

	public DatabaseConnector(final AntiAddict instance) {
		plugin = instance;
	}

	public void adjustDate(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return;

		entry.setLatestCheck(plugin.getMethods().getDate());
		entry.setCurrentDatePlayer(plugin.getMethods().getDate());

		plugin.getDatabase().save(entry);
	}

	public long calculateRestTime(final Player player) {
		final String playerName = player.getName();
		SqLiteDatabase entry = plugin.getDatabase().find(SqLiteDatabase.class)
				.where().ieq("playerName", playerName).findUnique();

		if (entry == null) {
			createNewEntry(player);

			entry = getEntry(playerName);
		}

		final long timeLimitMil = plugin.getGroupHandler().getTimeLimitMil(
				plugin.getGroupHandler().getGroup(player));
		final long currentTime = System.currentTimeMillis();
		final long joinTime = getJoinTime(playerName);
		final long playTimeOld = getPlayTime(playerName);
		final long differenceBetween = getLastDifference(playerName);

		final long playTime = playTimeOld
				+ ((currentTime - joinTime) - differenceBetween);

		setLastDifference(playerName, currentTime - joinTime);

		final long restTime = timeLimitMil - playTime;

		setPlayTime(playerName, playTime);
		setRestTime(playerName, restTime);

		return restTime;
	}

	public void createNewEntry(final Player player) {
		SqLiteDatabase entry = plugin.getDatabase().find(SqLiteDatabase.class)
				.where().ieq("playerName", player.getName()).findUnique();

		if (entry != null)
			return;

		final String group = plugin.getGroupHandler().getGroup(player);
		final long timeLimitMil = plugin.getGroupHandler().getTimeLimitMil(
				group);

		entry = new SqLiteDatabase();
		entry.setLanguage("en_US");
		entry.setPlayerName(player.getName());
		entry.setReputation(10);
		entry.setJoinTime(System.currentTimeMillis());
		entry.setPlayTime(0L);
		entry.setRestTime(timeLimitMil);
		entry.setLastDifference(0L);
		entry.setPlayerBanned("false");
		entry.setNotDone("true");
		entry.setWarnMessage("false");

		entry.setLatestCheck(plugin.getMethods().getDate());
		entry.setCurrentDatePlayer(plugin.getMethods().getDate());

		plugin.getDatabase().save(entry);
	}

	public String getCorrectName(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return null;

		return entry.getPlayerName();
	}

	public long getCurrentDatePlayer(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return -1;

		return entry.getCurrentDatePlayer();
	}

	public SqLiteDatabase getEntry(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return null;

		return entry;
	}

	public long getJoinTime(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return -1;

		return entry.getJoinTime();
	}

	public String getLanguage(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return null;

		return entry.getLanguage();
	}

	public long getLastDifference(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return -1;

		return entry.getLastDifference();
	}

	public long getPlayTime(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return -1;

		return entry.getPlayTime();
	}

	public long getPlayTimeInMinutes(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return -1;

		return entry.getPlayTime() / 60000;
	}

	public int getReputation(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return -1;

		return entry.getReputation();
	}

	public long getRestTime(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return -1;

		return entry.getRestTime();
	}

	public long getRestTimeInMinutes(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return -1;

		return entry.getRestTime() / 60000;
	}

	public String isBanned(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return "false";

		return entry.getPlayerBanned();
	}

	public void isBanned(final String playerName, final String isBanned) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return;

		entry.setPlayerBanned(isBanned);
		plugin.getDatabase().save(entry);
	}

	public boolean isInDatabase(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return false;
		else
			return true;
	}

	public boolean isItANewDay(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return false;

		final long currentDate = entry.getCurrentDatePlayer();
		final long latestCheck = entry.getLatestCheck();

		if (currentDate != latestCheck)
			return true;
		else
			return false;
	}

	public String isNotDone(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return "true";

		return entry.getNotDone();
	}

	public void isNotDone(final String playerName, final String notDone) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return;

		entry.setNotDone(notDone);
		plugin.getDatabase().save(entry);
	}

	public String isWarnMessage(final String playerName) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return "false";

		return entry.getWarnMessage();
	}

	public void isWarnMessage(final String playerName, final String warnMessage) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return;

		entry.setWarnMessage(warnMessage);
		plugin.getDatabase().save(entry);
	}

	public void resetData(final Player player) {
		final String playerName = player.getName();
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return;

		final String group = plugin.getGroupHandler().getGroup(player);
		final long timeLimitMil = plugin.getGroupHandler().getTimeLimitMil(
				group);
		entry.setJoinTime(entry.getJoinTime());
		entry.setPlayTime(0L);
		entry.setRestTime(timeLimitMil);
		entry.setLastDifference(0L);
		entry.setPlayerBanned("false");
		entry.setNotDone("true");
		entry.setWarnMessage("false");

		entry.setLatestCheck(plugin.getMethods().getDate());
		entry.setCurrentDatePlayer(plugin.getMethods().getDate());

		plugin.getDatabase().save(entry);
	}

	public void setJoinTime(final String playerName, final long joinTime) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return;

		entry.setJoinTime(joinTime);
		plugin.getDatabase().save(entry);
	}

	public void setLanguage(final String playerName, final String language) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return;

		entry.setLanguage(language);
		plugin.getDatabase().save(entry);
	}

	public void setLastDifference(final String playerName,
			final long lastDifference) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return;

		entry.setLastDifference(lastDifference);
		plugin.getDatabase().save(entry);
	}

	public void setPlayTime(final String playerName, final long playTime) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return;

		entry.setPlayTime(playTime);
		plugin.getDatabase().save(entry);
	}

	public void setReputation(final String playerName, final int reputation) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return;

		entry.setReputation(reputation);
		plugin.getDatabase().save(entry);
	}

	public void setRestTime(final String playerName, final long restTime) {
		final SqLiteDatabase entry = plugin.getDatabase()
				.find(SqLiteDatabase.class).where()
				.ieq("playerName", playerName).findUnique();

		if (entry == null)
			return;

		entry.setRestTime(restTime);
		plugin.getDatabase().save(entry);
	}
}
