package me.staartvin.antiaddict.config;

import java.util.Arrays;

import me.staartvin.antiaddict.AntiAddict;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class LoadConfiguration {

	public AntiAddict plugin;

	public LoadConfiguration(final AntiAddict plugin) {
		this.plugin = plugin;
	}

	public void changeLanguage(final CommandSender sender,
			final String wantedLanguage, final String player) {

		String playerName;
		if (player == null) {
			playerName = sender.getName();
		} else {
			playerName = player;
		}
		if (!plugin.getdCon().isInDatabase(playerName)) {
			sender.sendMessage(ChatColor.RED + "Player " + player
					+ " is not in the database!");
			return;
		}

		if (wantedLanguage.equalsIgnoreCase("en_us")) {
			sender.sendMessage(ChatColor.AQUA + "Language of " + playerName
					+ " has been changed to English!");
			plugin.getdCon().setLanguage(playerName, "en_US");
		} else if (wantedLanguage.equalsIgnoreCase("pt_br")) {
			sender.sendMessage(ChatColor.AQUA + "Language of " + playerName
					+ " has been changed to Português brasileiro!");
			plugin.getdCon().setLanguage(playerName, "pt_BR");
		} else if (wantedLanguage.equalsIgnoreCase("fr_fr")) {
			sender.sendMessage(ChatColor.AQUA + "Language of " + playerName
					+ " has been changed to le Français!");
			plugin.getdCon().setLanguage(playerName, "fr_FR");
		} else if (wantedLanguage.equalsIgnoreCase("de_de")) {
			sender.sendMessage(ChatColor.AQUA + "Language of " + playerName
					+ " has been changed to Deutsch!");
			plugin.getdCon().setLanguage(playerName, "de_DE");
		} else if (wantedLanguage.equalsIgnoreCase("it_it")) {
			sender.sendMessage(ChatColor.AQUA + "Language of " + playerName
					+ " has been changed to Italiano!");
			plugin.getdCon().setLanguage(playerName, "it_IT");
		} else if (wantedLanguage.equalsIgnoreCase("ja_jp")) {
			sender.sendMessage(ChatColor.AQUA + "Language of " + playerName
					+ " has been changed to Nihongo but is not yet supported!");
			sender.sendMessage(ChatColor.AQUA + "Using default language.");
			// Language = "ja_JP";
			plugin.getdCon().setLanguage(playerName, "en_US");
		} else if (wantedLanguage.equalsIgnoreCase("ko_kr")) {
			sender.sendMessage(ChatColor.AQUA + "Language of " + playerName
					+ " has been changed to Hangugeo but is not yet supported!");
			sender.sendMessage(ChatColor.AQUA + "Using default language.");
			// Language = "ko_KR";
			plugin.getdCon().setLanguage(playerName, "en_US");
		} else if (wantedLanguage.equalsIgnoreCase("es_mx")) {
			sender.sendMessage(ChatColor.AQUA
					+ "Language of "
					+ playerName
					+ " has been changed to Español americano but is not yet supported!");
			sender.sendMessage(ChatColor.AQUA + "Using default language.");
			// Language = "es_MX";
			plugin.getdCon().setLanguage(playerName, "en_US");
		} else if (wantedLanguage.equalsIgnoreCase("pl_pl")) {
			sender.sendMessage(ChatColor.AQUA + "Language of " + playerName
					+ " has been changed to polszczyzna!");
			plugin.getdCon().setLanguage(playerName, "pl_PL");
		} else if (wantedLanguage.equalsIgnoreCase("ru_ru")) {
			sender.sendMessage(ChatColor.AQUA
					+ "Language of "
					+ playerName
					+ " has been changed to russkiy yazyk but is not yet supported!");
			sender.sendMessage(ChatColor.AQUA + "Using default language.");
			// Language = "ru_RU";
			plugin.getdCon().setLanguage(playerName, "en_US");
		} else if (wantedLanguage.equalsIgnoreCase("zh_cn")) {
			sender.sendMessage(ChatColor.AQUA + "Language of " + playerName
					+ " has been changed to Fántizì but is not yet supported!");
			sender.sendMessage(ChatColor.AQUA + "Using default language.");
			// Language = "zh_CN";
			plugin.getdCon().setLanguage(playerName, "en_US");
		} else if (wantedLanguage.equalsIgnoreCase("es_es")) {
			sender.sendMessage(ChatColor.AQUA + "Language of " + playerName
					+ " has been changed to Español!");
			plugin.getdCon().setLanguage(playerName, "es_ES");
		} else if (wantedLanguage.equalsIgnoreCase("zh_tw")) {
			sender.sendMessage(ChatColor.AQUA + "Language of " + playerName
					+ " has been changed to Jiantizì but is not yet supported!");
			sender.sendMessage(ChatColor.AQUA + "Using default language.");
			// Language = "zh_TW";
			plugin.getdCon().setLanguage(playerName, "en_US");
		} else if (wantedLanguage.equalsIgnoreCase("nl_nl")) {
			plugin.getdCon().setLanguage(playerName, "nl_NL");
			sender.sendMessage(ChatColor.AQUA + "Language of " + playerName
					+ " has been changed to Nederlands!");
		} else if (wantedLanguage.equalsIgnoreCase("da_DK")) {
			plugin.getdCon().setLanguage(playerName, "da_DK");
			sender.sendMessage(ChatColor.AQUA
					+ "Your language has been changed to Danish!");
		} else {
			sender.sendMessage(ChatColor.AQUA
					+ " Can't recognize language. Using default language for "
					+ playerName);
			plugin.getdCon().setLanguage(playerName, "en_US");
		}
	}

	public void loadGroupsConfig() {

		final FileConfiguration groupConfig = plugin.getGroupConfig()
				.getConfig();

		groupConfig
				.options()
				.header("Group config"
						+ "\nTime limit is defined in minutes."
						+ "\nTo assign a player to a group, give them the permission with the group name."
						+ "\nExample: When you give Herobrine antiaddict.groups.Default it will assign Herobrine to the Default group."
						+ "\nNames are case-sensitive. So antiaddict.groups.default wouldn't work.");
		groupConfig.addDefault("Groups.Default.time limit", 300);
		groupConfig.addDefault("Groups.Guests.time limit", 60);

		// Save the changes
		groupConfig.options().copyDefaults(true);
		plugin.getGroupConfig().saveConfig();
	}

	public void loadMainConfig() {
		// config.yml loading

		plugin.getConfig().addDefault("AntiAddict.LimitAll", false);
		plugin.getConfig().addDefault("AntiAddict.ShowWhenAddictJoinsServer",
				true);
		plugin.getConfig().addDefault("AntiAddict.ReputationPointsBan", 4);
		plugin.getConfig().addDefault("AntiAddict.NoticeOnVersionUpdate", true);
		plugin.getConfig().addDefault("World.UseMultiWorldSetup", false);
		plugin.getConfig().addDefault("World.ExemptedWorlds",
				Arrays.asList("unusedworld"));
		
		plugin.setLimitall(plugin.getConfig().getBoolean("AntiAddict.LimitAll"));
		plugin.setUseMultiWorldSetup(plugin.getConfig().getBoolean(
				"World.UseMultiWorldSetup"));
		plugin.setEWs(plugin.getConfig().getStringList("World.ExemptedWorlds"));

		// config.yml
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}

	/*public void ConvertConfigto1_9() {
		// Checking if there is an old option
		if (plugin.getConfig().getString("AntiAddict.LimitKickMessage") != null
				|| plugin.getConfig().getString("JoinMessage.Part1") != null
				|| plugin.getConfig().getString("JoinMessage.Part2") != null
				|| plugin.getConfig().getString("KickMessage.Part1") != null
				|| plugin.getConfig().getString("KickMessage.Part2") != null
				|| plugin.getConfig().getString(
						"AntiAddict.KickMessageWhenTryingToLoginAgain") != null) {

			// Noticing Server Operator about action.
			System.out
					.println("[AntiAddict] Convertion old config to new format.");

			// Placing old messages to the new ones in translations.yml
			if (plugin.getConfig().getString("AntiAddict.LimitKickMessage") != null) {
				translationConfig.set("en_US.LimitKickMessage", plugin.getConfig()
						.getString("AntiAddict.LimitKickMessage"));
			}
			if (plugin.getConfig().getString(
					"AntiAddict.KickMessageWhenTryingToLoginAgain") != null) {
				translationConfig
						.set("en_US.KickMessageWhenTryingToLoginAgain",
								plugin.getConfig()
										.getString(
												"AntiAddict.KickMessageWhenTryingToLoginAgain"));
			}
			if (plugin.getConfig().getString("JoinMessage.Part1") != null) {
				translationConfig.set("en_US.JoinMessage.Part1", plugin.getConfig()
						.getString("JoinMessage.Part1"));
				if (plugin.getConfig().getString("JoinMessage.Part2") != null) {
					translationConfig.set("en_US.JoinMessage.Part2",
							plugin.getConfig().getString("JoinMessage.Part2"));
				}
			}
			if (plugin.getConfig().getString("KickMessage.Part1") != null) {
				translationConfig.set("en_US.AlmostKickedMessage.Part1", plugin.getConfig()
						.getString("KickMessage.Part1"));
				if (plugin.getConfig().getString("KickMessage.Part2") != null) {
					translationConfig.set("en_US.AlmostKickedMessage.Part2",
							plugin.getConfig().getString("KickMessage.Part2"));
				}
			}

			// Deleting old options in config.yml
			plugin.getConfig().set("AntiAddict.LimitKickMessage", null);
			plugin.getConfig().set("JoinMessage.Part1", null);
			plugin.getConfig().set("JoinMessage.Part2", null);
			plugin.getConfig().set("JoinMessage", null);
			plugin.getConfig().set("KickMessage.Part1", null);
			plugin.getConfig().set("KickMessage.Part2", null);
			plugin.getConfig().set("KickMessage", null);
			plugin.getConfig().set("AntiAddict.KickMessageWhenTryingToLoginAgain",
					null);

			// Save all the work we did!
			translationConfig.options().copyDefaults(true);
			saveTranslationConfig();
			plugin.getConfig().options().copyDefaults(true);
			plugin.saveConfig();

			// Done!
			System.out
					.println("[AntiAddict] Convertion is done. No errors found.");
		}
	} */

	public void loadTranslationsConfig() {

		final FileConfiguration translationConfig = plugin
				.getTranslationConfig().getConfig();

		// English US Translation
		translationConfig.addDefault("en_US.LimitKickMessage",
				"You should spend more time in real-life!");
		translationConfig.addDefault("en_US.KickMessageWhenTryingToLoginAgain",
				"Sorry but your playtime here is over!");
		translationConfig
				.addDefault(
						"en_US.JoinMessage",
						"You were marked as addicted, so your playtime is limited to %minutes% minutes per day.");
		translationConfig.addDefault("en_US.AlmostKickedMessage.Part1",
				"You have hardly any time left! Prepare to be kicked.");
		translationConfig.addDefault("en_US.AlmostKickedMessage.Part2",
				"See you next time!");

		// Dutch Translation		
		translationConfig.addDefault("nl_NL.LimitKickMessage",
				"Je moet eens van je computer vandaan gaan!");
		translationConfig.addDefault("nl_NL.KickMessageWhenTryingToLoginAgain",
				"Sorry, maar je hebt voor nu genoeg gespeeld!");
		translationConfig
				.addDefault(
						"nl_NL.JoinMessage",
						"Jij bent als een verslaafde aangewezen, dus je speeltijd is verkort naar %minutes% minuten per dag.");
		translationConfig
				.addDefault("nl_NL.AlmostKickedMessage.Part1",
						"Jouw tijd is bijna op! Wees voorbereid om gekicked te worden.");
		translationConfig.addDefault("nl_NL.AlmostKickedMessage.Part2",
				"Tot de volgende keer!");

		// German Translation		
		translationConfig.addDefault("de_DE.LimitKickMessage",
				"Du solltest wirklich mehr Zeit im Real-Life verbringen!");
		translationConfig.addDefault("de_DE.KickMessageWhenTryingToLoginAgain",
				"Deine Spielzeit für heute ist leider vorbei.");
		translationConfig
				.addDefault(
						"de_DE.JoinMessage",
						"Du wurdest als süchtig markiert, deine Spielzeit pro Tag beträgt %minutes% Minuten.");
		translationConfig.addDefault("de_DE.AlmostKickedMessage.Part1",
				"Du hast kaum noch Zeit, du wirst bald gekickt.");
		translationConfig.addDefault("de_DE.AlmostKickedMessage.Part2",
				"Bis bald!");

		// Italian Translation
		translationConfig.addDefault("it_IT.LimitKickMessage",
				"Devi trascorrere più tempo nella vita reale!");
		translationConfig.addDefault("it_IT.KickMessageWhenTryingToLoginAgain",
				"Scusa ma il tuo tempo di gioco è finito qui!");
		translationConfig
				.addDefault(
						"it_IT.JoinMessage",
						"Sei stato segnato come dipendente, quindi il tuo tempo di gioco è limitato a %minutes% per giorno.");
		translationConfig.addDefault("it_IT.AlmostKickedMessage.Part1",
				"Hai pochissimo tempo rimasto! Preparati ad essere kickato.");
		translationConfig.addDefault("it_IT.AlmostKickedMessage.Part2",
				"Ci vediamo la prossima volta!");

		// Polish Translation
		translationConfig.addDefault("pl_PL.LimitKickMessage",
				"Powinienes spedzic wiecej czasu w prawdziwym zyciu!");
		translationConfig.addDefault("pl_PL.KickMessageWhenTryingToLoginAgain",
				"Niestety, Twoja Czas tutaj sie skonczyl!");
		translationConfig
				.addDefault(
						"pl_PL.JoinMessage",
						"Jestes zostaly oznaczone jako uzalezniony, wiec twój Czas jest ograniczony do %minutes% minut dziennie.");
		translationConfig.addDefault("pl_PL.AlmostKickedMessage.Part1",
				"Twój czas ucieka... Przygotuj sie na kick.");
		translationConfig.addDefault("pl_PL.AlmostKickedMessage.Part2",
				"Do zobaczenia nastepnym razem!");

		// Spanish Translation
		translationConfig.addDefault("es_ES.LimitKickMessage",
				"¡Deberías invertir más tiempo en la vida real!");
		translationConfig.addDefault("es_ES.KickMessageWhenTryingToLoginAgain",
				"Lo sentimos, pero su tiempo de juego ha terminado.");
		translationConfig
				.addDefault(
						"es_ES.JoinMessage",
						"Has sido marcado como adicto, por lo que tu tiempo de juego ha sido limitado a %minutes% minutos por día.");
		translationConfig
				.addDefault("es_ES.AlmostKickedMessage.Part1",
						"¡Usted tiene apenas tiempo restante! Prepárese para ser forzado a salir.");
		translationConfig.addDefault("es_ES.AlmostKickedMessage.Part2",
				"¡Hasta la proxima!");

		// Brazilian Portuguese Translation
		translationConfig.addDefault("pt_BR.LimitKickMessage",
				"Você deve gastar mais tempo na vida de realidade!");
		translationConfig.addDefault("pt_BR.KickMessageWhenTryingToLoginAgain",
				"Desculpe, mas o seu tempo de jogar aqui terminou!");
		translationConfig
				.addDefault(
						"pt_BR.JoinMessage",
						"Você foi marcado como um viciado, assim o seu tempo de jogar é limitado à %minutes% minutos por cada dia.");
		translationConfig
				.addDefault("pt_BR.AlmostKickedMessage.Part1",
						"Você têm apenas nenhum tempo restantes! Prepare-se para ser forçado a sair!");
		translationConfig.addDefault("pt_BR.AlmostKickedMessage.Part2",
				"See you next time!");

		// French Translation
		translationConfig.addDefault("fr_FR.LimitKickMessage",
				"Vous devez passer plus de temps dans la vraie vie!");
		translationConfig.addDefault("fr_FR.KickMessageWhenTryingToLoginAgain",
				"Désolé, mais votre temps de jeux est dépassé!");
		translationConfig
				.addDefault(
						"fr_FR.JoinMessage",
						"Vous avez été repéré comme accro, votre temps de jeux a donc été limité à %minutes% minutes par jour.");
		translationConfig.addDefault("fr_FR.AlmostKickedMessage.Part1",
				"Vous avez suffisamment joué! Préparez vous à être éjecté.");
		translationConfig.addDefault("fr_FR.AlmostKickedMessage.Part2",
				"Au plaisir de vous revoir!");

		// Danish Translation
		translationConfig.addDefault("da_DK.LimitKickMessage",
				"Du burde bruge mere tid i den virkelige verden!");
		translationConfig.addDefault("da_DK.KickMessageWhenTryingToLoginAgain",
				"Undskyld, men din spilletid er ovre!");
		translationConfig
				.addDefault(
						"da_DK.JoinMessage",
						"Du er blevet markeret som afhængig, så din spilletid er blevet begrænset til %minutes% minutter per dag.");
		translationConfig
				.addDefault("da_DK.AlmostKickedMessage.Part1",
						"Du har næsten ingen spilletid tilbage! Forbered dig på at blive smidt ud!");
		translationConfig.addDefault("da_DK.AlmostKickedMessage.Part2",
				"Ses næste gang!");

		// Save changes
		translationConfig.options().copyDefaults(true);
		plugin.getTranslationConfig().saveConfig();
		//ConvertConfigto1_9();
	}
}
