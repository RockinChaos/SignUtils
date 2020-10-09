/*
 * SignUtils
 * Copyright (C) CraftationGaming <https://www.craftationgaming.com/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.RockinChaos.signutils;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import me.RockinChaos.signutils.handlers.ConfigHandler;
import me.RockinChaos.signutils.handlers.PermissionsHandler;
import me.RockinChaos.signutils.handlers.UpdateHandler;
import me.RockinChaos.signutils.signs.Ranks;
import me.RockinChaos.signutils.utils.LanguageAPI;

public class ChatExecutor implements CommandExecutor {
	
   /**
	* Called when the CommandSender executes a command.
    * @param sender - Source of the command.
    * @param command - Command which was executed.
    * @param label - Alias of the command which was used.
    * @param args - Passed command arguments.
    * @return true if the command is valid.
	*/
	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (args.length == 0) {
			if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.use")) {
				LanguageAPI.getLang(false).dispatchMessage(sender, "&5SignUtils v" + SignUtils.getInstance().getDescription().getVersion() + "&d by RockinChaos");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&5Type &l/SignUtils Help &5for the help menu.");
			} else { LanguageAPI.getLang(false).sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args.length == 1 && args[0].equalsIgnoreCase("help") || args.length == 2 && args[0].equalsIgnoreCase("help") && args[1].equalsIgnoreCase("1")) {
			if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.use")) {
				LanguageAPI.getLang(false).dispatchMessage(sender, "");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l&m]------------------&d&l[&5 SignUtils &d&l]&d&l&m-----------------[");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&dSignUtils v" + SignUtils.getInstance().getDescription().getVersion() + "&d by RockinChaos");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l/SignUtils Help &7- &dThis help menu.");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l/SignUtils Reload &7- &dReloads the .yml files.");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l/SignUtils Updates &7- &dChecks for plugin updates.");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l/SignUtils AutoUpdate &7- &dUpdates to latest version.");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&dType &d&l/SignUtils Help 2 &dfor the next page.");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l&m]----------------&d&l[&5 Help Menu 1/2 &d&l]&d&l&m---------------[");
				LanguageAPI.getLang(false).dispatchMessage(sender, "");
			} else { LanguageAPI.getLang(false).sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args.length == 2 && args[0].equalsIgnoreCase("help") && args[1].equalsIgnoreCase("2")) {
			if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.use")) {
				LanguageAPI.getLang(false).dispatchMessage(sender, "");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l&m]------------------&d&l[&5 SignUtils &d&l]&d&l&m-----------------[");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l/SignUtils Permissions &7- &dLists the permissions you have.");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l/SignUtils Rank &7- &dYour involved player group(s).");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l/SignUtils Rank <Player> &7- &dTheir involved player group(s).");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&dFound a bug? Report it @");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&dhttps://github.com/RockinChaos/SignUtils/issues");
				LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l&m]----------------&d&l[&5 Help Menu 2/2 &d&l]&d&l&m---------------[");
				LanguageAPI.getLang(false).dispatchMessage(sender, "");
			} else { LanguageAPI.getLang(false).sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
			if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.reload")) {
				ConfigHandler.getConfig(false);
				LanguageAPI.getLang(false).sendLangMessage("Commands.Default.configReload", sender);
			} else { LanguageAPI.getLang(false).sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args[0].equalsIgnoreCase("rank") || args[0].equalsIgnoreCase("ranks")) {
			if ((args.length == 2 && PermissionsHandler.getPermissions().hasPermission(sender, "signutils.rank.others")) || (args.length != 2 && PermissionsHandler.getPermissions().hasPermission(sender, "signutils.rank"))) {
				if (args.length == 2) {
					Ranks.getRank().signRank(sender, args[1]);
					return true;
				} else if (sender instanceof Player) {
					Ranks.getRank().signRank(sender, null);
					return true;
				} else { LanguageAPI.getLang(false).sendLangMessage("Commands.Default.notPlayer", sender); }
			} else { LanguageAPI.getLang(false).sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args.length == 1 && args[0].equalsIgnoreCase("permissions")) {
			if (PermissionsHandler.getPermissions().hasPermission(sender, "itemjoin.permissions")) {
				if (!(sender instanceof ConsoleCommandSender)) {
					LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l&m]------------------&d&l[&5 SignUtils &d&l]&d&l&m-----------------[");
					if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.*")) { LanguageAPI.getLang(false).dispatchMessage(sender, "&a[\u2714] SignUtils.*"); } 
					else { LanguageAPI.getLang(false).dispatchMessage(sender, "&c[\u2718] SignUtils.*"); }
					if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.all")) { LanguageAPI.getLang(false).dispatchMessage(sender, "&a[\u2714] SignUtils.All"); } 
					else { LanguageAPI.getLang(false).dispatchMessage(sender, "&c[\u2718] SignUtils.All"); }
					if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.use")) { LanguageAPI.getLang(false).dispatchMessage(sender, "&a[\u2714] SignUtils.Use"); } 
					else { LanguageAPI.getLang(false).dispatchMessage(sender, "&c[\u2718] SignUtils.Use"); }
					if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.rank")) { LanguageAPI.getLang(false).dispatchMessage(sender, "&a[\u2714] SignUtils.rank"); } 
					else { LanguageAPI.getLang(false).dispatchMessage(sender, "&c[\u2718] SignUtils.rank"); }
					if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.rank.others")) { LanguageAPI.getLang(false).dispatchMessage(sender, "&a[\u2714] SignUtils.rank.others"); }
					else { LanguageAPI.getLang(false).dispatchMessage(sender, "&c[\u2718] ItemJoin.get.others"); }
					if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.reload")) { LanguageAPI.getLang(false).dispatchMessage(sender, "&a[\u2714] SignUtils.Reload"); } 
					else { LanguageAPI.getLang(false).dispatchMessage(sender, "&c[\u2718] SignUtils.Reload"); }
					if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.updates")) { LanguageAPI.getLang(false).dispatchMessage(sender, "&a[\u2714] SignUtils.Updates"); }
					else { LanguageAPI.getLang(false).dispatchMessage(sender, "&c[\u2718] SignUtils.Updates"); }
					if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.autoupdate")) { LanguageAPI.getLang(false).dispatchMessage(sender, "&a[\u2714] SignUtils.AutoUpdate"); }
					else { LanguageAPI.getLang(false).dispatchMessage(sender, "&c[\u2718] SignUtils.AutoUpdate"); }
					if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.permissions")) { LanguageAPI.getLang(false).dispatchMessage(sender, "&a[\u2714] SignUtils.permissions"); } 
					else { LanguageAPI.getLang(false).dispatchMessage(sender, "&c[\u2718] SignUtils.permissions"); }
					LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l&m]------------&d&l[&5 Permissions Menu 1/1 &d&l]&d&l&m------------[");
				} else if (sender instanceof ConsoleCommandSender) { LanguageAPI.getLang(false).sendLangMessage("Commands.Default.notPlayer", sender); }
			} else { LanguageAPI.getLang(false).sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args[0].equalsIgnoreCase("updates") || args[0].equalsIgnoreCase("update")) {
			if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.updates")) {
				LanguageAPI.getLang(false).sendLangMessage("Commands.Updates.checking", sender);
				UpdateHandler.getUpdater(false).checkUpdates(sender, false);
			} else { LanguageAPI.getLang(false).sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else if (args[0].equalsIgnoreCase("AutoUpdate")) {
			if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.autoupdate")) {
				LanguageAPI.getLang(false).sendLangMessage("Commands.Updates.forcing", sender);
				UpdateHandler.getUpdater(false).forceUpdates(sender);
			} else { LanguageAPI.getLang(false).sendLangMessage("Commands.Default.noPermission", sender); }
			return true;
		} else { LanguageAPI.getLang(false).sendLangMessage("Commands.Default.unknownCommand", sender); }
		return true;
	}
}