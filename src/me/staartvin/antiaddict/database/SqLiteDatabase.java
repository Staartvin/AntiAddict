package me.staartvin.antiaddict.database;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "AntiAddict_data")
public class SqLiteDatabase {

	@Id
	private int id;

	@NotNull
	private int reputation;

	@NotNull
	private String playerName;

	@NotNull
	private String language;

	@NotNull
	private long joinTime;

	@NotNull
	private long playTime;

	@NotNull
	private long restTime;

	@NotNull
	private long latestCheck;

	@NotNull
	private String playerBanned;

	@NotNull
	private long currentDatePlayer;

	@NotNull
	private String notDone;

	@NotNull
	private String warnMessage;

	@Version
	private Timestamp lastUpdate;

	@NotNull
	private long lastDifference;

	public long getCurrentDatePlayer() {
		return currentDatePlayer;
	}

	public int getId() {
		return id;
	}

	public long getJoinTime() {
		return joinTime;
	}

	public String getLanguage() {
		return language;
	}

	public long getLastDifference() {
		return lastDifference;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public long getLatestCheck() {
		return latestCheck;
	}

	public String getNotDone() {
		return notDone;
	}

	public String getPlayerBanned() {
		return playerBanned;
	}

	public String getPlayerName() {
		return playerName;
	}

	public long getPlayTime() {
		return playTime;
	}

	public int getReputation() {
		return reputation;
	}

	public long getRestTime() {
		return restTime;
	}

	public String getWarnMessage() {
		return warnMessage;
	}

	public void setCurrentDatePlayer(final long currentDatePlayer) {
		this.currentDatePlayer = currentDatePlayer;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setJoinTime(final long joinTime) {
		this.joinTime = joinTime;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setLastDifference(final long lastDifference) {
		this.lastDifference = lastDifference;
	}

	public void setLastUpdate(final Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setLatestCheck(final long latestCheck) {
		this.latestCheck = latestCheck;
	}

	public void setNotDone(final String notDone) {
		this.notDone = notDone;
	}

	public void setPlayerBanned(final String playerBanned) {
		this.playerBanned = playerBanned;
	}

	public void setPlayerName(final String playerName) {
		this.playerName = playerName;
	}

	public void setPlayTime(final long playTime) {
		this.playTime = playTime;
	}

	public void setReputation(final int reputation) {
		this.reputation = reputation;
	}

	public void setRestTime(final long restTime) {
		this.restTime = restTime;
	}

	public void setWarnMessage(final String warnMessage) {
		this.warnMessage = warnMessage;
	}
}