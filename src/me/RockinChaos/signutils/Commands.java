package me.RockinChaos.signutils;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import me.RockinChaos.signutils.handlers.ConfigHandler;
import me.RockinChaos.signutils.handlers.PermissionsHandler;
import me.RockinChaos.signutils.utils.Language;
import me.RockinChaos.signutils.utils.Utility;

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
				Language.dispatchMessage(sender, "&d&l&m]----------------&d&l[&5 Help Menu 1/2 &d&l]&d&l&m---------------[");
				Language.dispatchMessage(sender, "");
			} else { Language.sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args.length == 2 && args[0].equalsIgnoreCase("help") && args[1].equalsIgnoreCase("2")) {
			if (PermissionsHandler.isAuthorized(sender, "signutils.use")) {
				Language.dispatchMessage(sender, "");
				Language.dispatchMessage(sender, "&d&l&m]------------------&d&l[&5 SignUtils &d&l]&d&l&m-----------------[");
				Language.dispatchMessage(sender, "&d&l/SignUtils Permissions &7- &dLists the permissions you have.");
				Language.dispatchMessage(sender, "&d&l/SignUtils Rank &7- &dYour involved player group(s).");
				Language.dispatchMessage(sender, "&d&l/SignUtils Rank <Player> &7- &dTheir involved player group(s).");
				Language.dispatchMessage(sender, "&dFound a bug? Report it @");
				Language.dispatchMessage(sender, "&dhttps://github.com/RockinChaos/SignUtils/issues");
				Language.dispatchMessage(sender, "&d&l&m]----------------&d&l[&5 Help Menu 2/2 &d&l]&d&l&m---------------[");
				Language.dispatchMessage(sender, "");
			} else { Language.sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
			if (PermissionsHandler.isAuthorized(sender, "signutils.reload")) {
		  		ConfigHandler.generateData(null);
				Language.sendLangMessage("Commands.Default.configReload", sender);
			} else { Language.sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args[0].equalsIgnoreCase("rank") || args[0].equalsIgnoreCase("ranks")) {
			if ((args.length == 2 && PermissionsHandler.isAuthorized(sender, "signutils.rank.others")) || (args.length != 2 && PermissionsHandler.isAuthorized(sender, "signutils.rank"))) {
				if (args.length == 2) {
					Utility.signRank(sender, args[1]);
					return true;
				} else if (sender instanceof Player) {
					Utility.signRank(sender, null);
					return true;
				} else { Language.sendLangMessage("Commands.Default.notPlayer", sender); }
			} else { Language.sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args.length == 1 && args[0].equalsIgnoreCase("permissions")) {
			if (PermissionsHandler.isAuthorized(sender, "itemjoin.permissions")) {
				if (!(sender instanceof ConsoleCommandSender)) {
					Language.dispatchMessage(sender, "&d&l&m]------------------&d&l[&5 SignUtils &d&l]&d&l&m-----------------[");
					if (PermissionsHandler.isAuthorized(sender, "signutils.*")) { Language.dispatchMessage(sender, "&a[\u2714] SignUtils.*"); } 
					else { Language.dispatchMessage(sender, "&c[\u2718] SignUtils.*"); }
					if (PermissionsHandler.isAuthorized(sender, "signutils.all")) { Language.dispatchMessage(sender, "&a[\u2714] SignUtils.All"); } 
					else { Language.dispatchMessage(sender, "&c[\u2718] SignUtils.All"); }
					if (PermissionsHandler.isAuthorized(sender, "signutils.use")) { Language.dispatchMessage(sender, "&a[\u2714] SignUtils.Use"); } 
					else { Language.dispatchMessage(sender, "&c[\u2718] SignUtils.Use"); }
					if (PermissionsHandler.isAuthorized(sender, "signutils.rank")) { Language.dispatchMessage(sender, "&a[\u2714] SignUtils.rank"); } 
					else { Language.dispatchMessage(sender, "&c[\u2718] SignUtils.rank"); }
					if (PermissionsHandler.isAuthorized(sender, "signutils.rank.others")) { Language.dispatchMessage(sender, "&a[\u2714] SignUtils.rank.others"); }
					else { Language.dispatchMessage(sender, "&c[\u2718] ItemJoin.get.others"); }
					if (PermissionsHandler.isAuthorized(sender, "signutils.reload")) { Language.dispatchMessage(sender, "&a[\u2714] SignUtils.Reload"); } 
					else { Language.dispatchMessage(sender, "&c[\u2718] SignUtils.Reload"); }
					if (PermissionsHandler.isAuthorized(sender, "signutils.updates")) { Language.dispatchMessage(sender, "&a[\u2714] SignUtils.Updates"); }
					else { Language.dispatchMessage(sender, "&c[\u2718] SignUtils.Updates"); }
					if (PermissionsHandler.isAuthorized(sender, "signutils.autoupdate")) { Language.dispatchMessage(sender, "&a[\u2714] SignUtils.AutoUpdate"); }
					else { Language.dispatchMessage(sender, "&c[\u2718] SignUtils.AutoUpdate"); }
					if (PermissionsHandler.isAuthorized(sender, "signutils.permissions")) { Language.dispatchMessage(sender, "&a[\u2714] SignUtils.permissions"); } 
					else { Language.dispatchMessage(sender, "&c[\u2718] SignUtils.permissions"); }
					Language.dispatchMessage(sender, "&d&l&m]------------&d&l[&5 Permissions Menu 1/1 &d&l]&d&l&m------------[");
				} else if (sender instanceof ConsoleCommandSender) { Language.sendLangMessage("Commands.Default.notPlayer", sender); }
			} else { Language.sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args[0].equalsIgnoreCase("updates") || args[0].equalsIgnoreCase("update")) {
			if (PermissionsHandler.isAuthorized(sender, "signutils.updates")) {
				Language.sendLangMessage("Commands.Updates.checking", sender);
				ConfigHandler.getUpdater().checkUpdates(sender, false);
			} else { Language.sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args[0].equalsIgnoreCase("AutoUpdate")) {
			if (PermissionsHandler.isAuthorized(sender, "signutils.autoupdate")) {
				Language.sendLangMessage("Commands.Updates.forcing", sender);
				ConfigHandler.getUpdater().forceUpdates(sender);
			} else { Language.sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else { Language.sendLangMessage("Commands.Default.unknownCommand", sender); }
		return true;
	}
}