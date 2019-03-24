package me.rockinchaos.signutils;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import me.rockinchaos.signutils.handlers.ConfigHandler;
import me.rockinchaos.signutils.handlers.MemoryHandler;
import me.rockinchaos.signutils.handlers.PermissionsHandler;
import me.rockinchaos.signutils.handlers.PlayerHandler;
import me.rockinchaos.signutils.utils.Language;
import me.rockinchaos.signutils.utils.VaultAPI;

public class Commands implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (PermissionsHandler.isAuthorized(sender, "signutils.use")) {
				Language.dispatchMessage(sender, "&5SignUtils v" + SignUtils.getInstance().getDescription().getVersion() + "&d by RockinChaos");
				Language.dispatchMessage(sender, "&5Type &l/SignUtils Help &5for the help menu.");
			} else { Language.sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args.length == 1 && args[0].equalsIgnoreCase("help") || args.length == 2 && args[0].equalsIgnoreCase("help") && args[1].equalsIgnoreCase("1")) {
			if (PermissionsHandler.isAuthorized(sender, "signutils.use")) {
				Language.dispatchMessage(sender, "");
				Language.dispatchMessage(sender, "&d&l&m]------------------&d&l[&5 SignUtils &d&l]&d&l&m-----------------[");
				Language.dispatchMessage(sender, "&dSignUtils v" + SignUtils.getInstance().getDescription().getVersion() + "&d by RockinChaos");
				Language.dispatchMessage(sender, "&d&l/SignUtils Help &7- &dThis help menu.");
				Language.dispatchMessage(sender, "&d&l/SignUtils Reload &7- &dReloads the .yml files.");
				Language.dispatchMessage(sender, "&d&l/SignUtils Updates &7- &dChecks for plugin updates.");
				Language.dispatchMessage(sender, "&d&l/SignUtils AutoUpdate &7- &dUpdates to latest version.");
				Language.dispatchMessage(sender, "&dType &d&l/SignUtils Help 2 &dfor the next page.");
				Language.dispatchMessage(sender, "&d&l&m]----------------&d&l[&d Help Menu 1/2 &d&l]&d&l&m---------------[");
				Language.dispatchMessage(sender, "");
			} else { Language.sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args.length == 2 && args[0].equalsIgnoreCase("help") && args[1].equalsIgnoreCase("2")) {
			if (PermissionsHandler.isAuthorized(sender, "signutils.use")) {
				Language.dispatchMessage(sender, "");
				Language.dispatchMessage(sender, "&d&l&m]------------------&d&l[&5 SignUtils &d&l]&d&l&m-----------------[");
				Language.dispatchMessage(sender, "&d&l/SignUtils Rank &7- &dYour involved player group(s).");
				Language.dispatchMessage(sender, "&d&l/SignUtils Rank <Player> &7- &dTheir involved player group(s).");
				Language.dispatchMessage(sender, "&dFound a bug? Report it @");
				Language.dispatchMessage(sender, "&dhttps://github.com/RockinChaos/SignUtils/issues");
				Language.dispatchMessage(sender, "&d&l&m]----------------&d&l[&d Help Menu 2/2 &d&l]&d&l&m---------------[");
				Language.dispatchMessage(sender, "");
			} else { Language.sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
			if (PermissionsHandler.isAuthorized(sender, "signutils.reload")) {
				ConfigHandler.loadConfigs();
				MemoryHandler.generateData();
				Language.sendLangMessage("Commands.Default.configReload", sender);
			} else { Language.sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args[0].equalsIgnoreCase("rank") || args[0].equalsIgnoreCase("ranks")) {
			if (PermissionsHandler.isAuthorized(sender, "signutils.rank")) {
				if (args.length == 2 && PermissionsHandler.isAuthorized(sender, "signutils.rank.others")) {
					Player argsPlayer = PlayerHandler.getPlayerString(args[1]);
					if (argsPlayer == null) { String[] placeHolders = Language.newString(); placeHolders[4] = args[1]; Language.sendLangMessage("Commands.Default.targetNotFound", sender); return true; }
					String[] placeHolders = Language.newString(); placeHolders[2] = Integer.toString(VaultAPI.getGroups().getPlayerGroups(argsPlayer).length);
					Language.sendLangMessage("Signs.Rank.playerLine", sender, placeHolders);
					Language.sendLangMessage("Signs.Rank.groupSizeLine", sender, placeHolders);
					for (String groupName : VaultAPI.getGroups().getPlayerGroups(argsPlayer)) {
						placeHolders[3] = groupName;
						Language.sendLangMessage("Signs.Rank.groupListLine", sender, placeHolders);
					}
				} else if (sender instanceof Player && PermissionsHandler.isAuthorized(sender, "signutils.rank")) {
					String[] placeHolders = Language.newString(); placeHolders[2] = Integer.toString(VaultAPI.getGroups().getPlayerGroups((Player)sender).length);
					Language.sendLangMessage("Signs.Rank.playerLine", sender, placeHolders);
					Language.sendLangMessage("Signs.Rank.groupSizeLine", sender, placeHolders);
					for (String groupName : VaultAPI.getGroups().getPlayerGroups((Player)sender)) {
						placeHolders[3] = groupName;
						Language.sendLangMessage("Signs.Rank.groupListLine", sender, placeHolders);
					}
				} else { Language.sendLangMessage("Commands.Default.notPlayer", sender); }
			} else { Language.sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args[0].equalsIgnoreCase("updates") || args[0].equalsIgnoreCase("update")) {
			if (PermissionsHandler.isAuthorized(sender, "signutils.updates")) {
				Language.sendLangMessage("Commands.Updates.checking", sender);
				MemoryHandler.getUpdater().checkUpdates(sender);
			} else { Language.sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args[0].equalsIgnoreCase("AutoUpdate")) {
			if (PermissionsHandler.isAuthorized(sender, "signutils.autoupdate")) {
				Language.sendLangMessage("Commands.Updates.forcing", sender);
				MemoryHandler.getUpdater().forceUpdates(sender);
			} else { Language.sendLangMessage("Commands.Default.noPermission", sender); }
		} else { Language.sendLangMessage("Commands.Default.unknownCommand", sender); }
		return true;
	}
}