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

import me.RockinChaos.signutils.ChatComponent.ClickAction;
import me.RockinChaos.signutils.handlers.ConfigHandler;
import me.RockinChaos.signutils.handlers.PermissionsHandler;
import me.RockinChaos.signutils.handlers.UpdateHandler;
import me.RockinChaos.signutils.signs.Ranks;
import me.RockinChaos.signutils.utils.SchedulerUtils;
import me.RockinChaos.signutils.utils.StringUtils;
import me.RockinChaos.signutils.utils.api.LanguageAPI;

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
		if (Execute.DEFAULT.accept(sender, args, 0)) {
			LanguageAPI.getLang(false).dispatchMessage(sender, ("&5SignUtils v" + SignUtils.getInstance().getDescription().getVersion() + "&d by RockinChaos"), "&bThis should be the version submitted to the developer \n&bwhen submitting a bug or feature request.", "https://github.com/RockinChaos/SignUtils/issues", ClickAction.OPEN_URL);
			LanguageAPI.getLang(false).dispatchMessage(sender, "&5Type &l/SignUtils Help &5for the help menu.", "&eClick to View the Help Menu.", "/signutils help", ClickAction.RUN_COMMAND);
		} else if (Execute.HELP.accept(sender, args, 1)) {
			LanguageAPI.getLang(false).dispatchMessage(sender, "");
			LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l&m]------------------&d&l[&5 SignUtils &d&l]&d&l&m-----------------[");
			LanguageAPI.getLang(false).dispatchMessage(sender, ("&5SignUtils v" + SignUtils.getInstance().getDescription().getVersion() + "&d by RockinChaos"), "&bThis should be the version submitted to the developer \n&bwhen submitting a bug or feature request.", "https://github.com/RockinChaos/SignUtils/issues", ClickAction.OPEN_URL);
			LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l/SignUtils Help &7- &dThis help menu.", "&aExecuting this command shows this help menu!", "/signutils help", ClickAction.SUGGEST_COMMAND);
			LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l/SignUtils Reload &7- &dReloads the .yml files.", "&aFully reloads the plugin, fetching \n&aany changes made to the .yml files. \n\n&aBe sure to save changes made to your .yml files!", "/signutils reload", ClickAction.SUGGEST_COMMAND);
			LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l/SignUtils Updates &7- &dChecks for plugin updates.", "&aChecks to see if there are any updates available for this plugin.", "/signutils updates", ClickAction.SUGGEST_COMMAND);
			LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l/SignUtils Upgrade &7- &dUpdates to latest version.", "&aAttempts to Upgrade this plugin to the latest version. \n&aYou will need to restart the server for this process to complete.", "/signutils upgrade", ClickAction.SUGGEST_COMMAND);
			LanguageAPI.getLang(false).dispatchMessage(sender, "&dType &d&l/SignUtils Help 2 &dfor the next page.", "&eClick to View the Next Page.", "/signutils help 2", ClickAction.RUN_COMMAND);
			LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l&m]----------------&d&l[&5 Help Menu 1/2 &d&l]&d&l&m---------------[");
			LanguageAPI.getLang(false).dispatchMessage(sender, "");
		} else if (Execute.HELP.accept(sender, args, 2)) {
			LanguageAPI.getLang(false).dispatchMessage(sender, "");
			LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l&m]------------------&d&l[&5 SignUtils &d&l]&d&l&m-----------------[");
			LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l/SignUtils Permissions &7- &dLists the permissions you have.", "&aLists the Permissions for your Player. \n\n&aGreen &bmeans you have permission whereas \n&cRed &bmeans you do not have permission.", "/signutils permissions", ClickAction.SUGGEST_COMMAND);
			LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l/SignUtils Rank &7- &dYour involved player group(s).", "&aLists the Group(s) your Player is involved in.", "/signutils rank", ClickAction.SUGGEST_COMMAND);
			LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l/SignUtils Rank <User> &7- &dTheir involved player group(s).", "&aLists the Group(s) the specified Player is involved in.", "/signutils rank ", ClickAction.SUGGEST_COMMAND);
			LanguageAPI.getLang(false).dispatchMessage(sender, "&dFound a bug? Report it @");
			LanguageAPI.getLang(false).dispatchMessage(sender, "&dhttps://github.com/RockinChaos/SignUtils/issues", "&eClick to Submit a Bug or Feature Request.", "https://github.com/RockinChaos/SignUtils/issues", ClickAction.OPEN_URL);
			LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l&m]----------------&d&l[&5 Help Menu 2/2 &d&l]&d&l&m---------------[");
			LanguageAPI.getLang(false).dispatchMessage(sender, "");
		} else if (Execute.RELOAD.accept(sender, args, 0)) {
			ConfigHandler.getConfig().reloadConfigs(false);
			LanguageAPI.getLang(false).sendLangMessage("commands.default.configReload", sender);
		} else if (Execute.RANK.accept(sender, args, 0)) {
			this.rank(sender, args);
		} else if (Execute.PERMISSIONS.accept(sender, args, 1)) {
			this.permissions(sender);
		} else if (Execute.UPDATE.accept(sender, args, 0)) {
			LanguageAPI.getLang(false).sendLangMessage("commands.updates.checkRequest", sender);
			SchedulerUtils.runAsync(() -> {
				UpdateHandler.getUpdater(false).checkUpdates(sender, false); 
			});
		} else if (Execute.UPGRADE.accept(sender, args, 0)) {
			LanguageAPI.getLang(false).sendLangMessage("commands.updates.updateRequest", sender);
			SchedulerUtils.runAsync(() -> {
				UpdateHandler.getUpdater(false).forceUpdates(sender); 
			});
		} else if (this.matchExecutor(args) == null) {
			LanguageAPI.getLang(false).sendLangMessage("commands.default.unknownCommand", sender);
		} else if (!this.matchExecutor(args).playerRequired(sender, args)) {
			LanguageAPI.getLang(false).sendLangMessage("commands.default.noPlayer", sender);
			Execute executor = this.matchExecutor(args);
			if (executor.equals(Execute.RANK))            { LanguageAPI.getLang(false).sendLangMessage("commands.get.usageSyntax", sender); } 
		} else if (!this.matchExecutor(args).hasSyntax(args, 0)) {
			Execute executor = this.matchExecutor(args);
			if (executor.equals(Execute.RANK))               { LanguageAPI.getLang(false).sendLangMessage("commands.get.badSyntax", sender); } 
		} else if (!this.matchExecutor(args).hasPermission(sender, args)) {
			LanguageAPI.getLang(false).sendLangMessage("commands.default.noPermission", sender);
		}
		return true;
	}
	
   /**
	* Called when the CommandSender fails to execute a command.
	* @param args - Passed command arguments.
	* @return The found Executor.
	* 
	*/
	private Execute matchExecutor(final String[] args) {
		for (Execute command : Execute.values()) {
			if (command.acceptArgs(args)) {
				return command;
			}
		}
		return null;
	}
	
   /**
	* Called when the CommandSender executes the Permisisons command.
	* @param sender - Source of the command.
    * @param args - The arguments.
	* 
	*/
	private void rank(final CommandSender sender, final String[] args) {
		if (args.length == 2) {
			Ranks.signRank(sender, args[1]);
		} else if (sender instanceof Player) {
			Ranks.signRank(sender, null);
		}
	}
	
   /**
	* Called when the CommandSender executes the Permisisons command.
	* @param sender - Source of the command.
	* 
	*/
	private void permissions(final CommandSender sender) {
		LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l&m]------------------&d&l[&5 SignUtils &d&l]&d&l&m-----------------[");
		LanguageAPI.getLang(false).dispatchMessage(sender, (PermissionsHandler.hasPermission(sender, "signutils.*") ? "&a[\u2714]" : "&c[\u2718]") + " SignUtils.*");
		LanguageAPI.getLang(false).dispatchMessage(sender, (PermissionsHandler.hasPermission(sender, "signutils.all") ? "&a[\u2714]" : "&c[\u2718]") + " SignUtils.All");
		LanguageAPI.getLang(false).dispatchMessage(sender, (PermissionsHandler.hasPermission(sender, "signutils.use") ? "&a[\u2714]" : "&c[\u2718]") + " SignUtils.Use");
		LanguageAPI.getLang(false).dispatchMessage(sender, (PermissionsHandler.hasPermission(sender, "signutils.reload") ? "&a[\u2714]" : "&c[\u2718]") + " SignUtils.Reload");
		LanguageAPI.getLang(false).dispatchMessage(sender, (PermissionsHandler.hasPermission(sender, "signutils.updates") ? "&a[\u2714]" : "&c[\u2718]") + " SignUtils.Updates");
		LanguageAPI.getLang(false).dispatchMessage(sender, (PermissionsHandler.hasPermission(sender, "signutils.upgrade") ? "&a[\u2714]" : "&c[\u2718]") + " SignUtils.Upgrade");
		LanguageAPI.getLang(false).dispatchMessage(sender, (PermissionsHandler.hasPermission(sender, "signutils.permissions") ? "&a[\u2714]" : "&c[\u2718]") + " SignUtils.Permissions");
		LanguageAPI.getLang(false).dispatchMessage(sender, (PermissionsHandler.hasPermission(sender, "signutils.rank") ? "&a[\u2714]" : "&c[\u2718]") + " SignUtils.Rank");
		LanguageAPI.getLang(false).dispatchMessage(sender, (PermissionsHandler.hasPermission(sender, "signutils.rank.others") ? "&a[\u2714]" : "&c[\u2718]") + " SignUtils.Rank.Others");
		LanguageAPI.getLang(false).dispatchMessage(sender, "&d&l&m]------------&d&l[&5 Permissions Menu 1/1 &d&l]&d&l&m-----------[");
	}
	
   /**
	* Defines the config Command type for the command.
	* 
	*/
	public enum Execute {
		DEFAULT("", "signutils.use", false),
		HELP("help", "signutils.use", false),
		RELOAD("rl, reload", "signutils.reload", false),
		PERMISSIONS("permission, permissions", "signutils.permissions", true),
		RANK("rank, ranks", "signutils.rank, signutils.rank.others", true),
		UPDATE("update, updates", "signutils.updates", false),
		UPGRADE("upgrade", "signutils.upgrade", false);
		private final String command;
		private final String permission;
		private final boolean player;
		
       /**
	    * Creates a new Execute instance.
	    * @param command - The expected command argument. 
	    * @param permission - The expected command permission requirement.
	    * @param player - If the command is specific to a player instance, cannot be executed by console.
	    * 
	    */
		private Execute(final String command, final String permission, final boolean player) { 
			this.command = command; this.permission = permission; this.player = player; 
		}
		
       /**
	    * Called when the CommandSender executes a command.
	    * @param sender - Source of the command. 
	    * @param args - Passed command arguments.
	    * @param page - The page number to be expected.
	    * 
	    */
		public boolean accept(final CommandSender sender, final String[] args, final int page) {
			return (args.length == 0 || (StringUtils.splitIgnoreCase(this.command, args[0], ",") 
			  && this.hasSyntax(args, page)))
			  && this.playerRequired(sender, args)
			  && this.hasPermission(sender, args); 
		}
		
       /**
	    * Checks if the executed command is the same as the executor.
	    * @param sender - Source of the command. 
	    * @param args - Passed command arguments.
	    * @param page - The page number to be expected.
	    * 
	    */
		public boolean acceptArgs(final String[] args) {
			return StringUtils.splitIgnoreCase(this.command, args[0], ",");
		}
		
       /**
	    * Checks if the Command being executed has the proper formatting or syntax.
	    * @param args - Passed command arguments.
	    * @param page - The page number to be expected.
	    * 
	    */
		private boolean hasSyntax(final String[] args, final int page) {
			return ((args.length >= 2 && args[1].equalsIgnoreCase(String.valueOf(page))) || !(args.length >= 2) || this.equals(Execute.RANK));
		}
		
       /**
	    * Checks if the Player has permission to execute the Command.
	    * @param sender - Source of the command. 
	    * @param args - Passed command arguments.
	    * 
	    */
		public boolean hasPermission(final CommandSender sender, final String[] args) {
			String[] permissions = this.permission.replace(" ", "").split(",");
			boolean multiPerms = this.permission.contains(",");
			return (multiPerms && (this.equals(Execute.RANK) && ((args.length == 1 && PermissionsHandler.hasPermission(sender, permissions[0])) || (args.length >= 2 && PermissionsHandler.hasPermission(sender, permissions[1]))))
		       || (!multiPerms && PermissionsHandler.hasPermission(sender, this.permission)));
		}
		
       /**
	    * Checks if the Command requires the instance to be a Player.
	    * @param sender - Source of the command. 
	    * @param args - Passed command arguments.
	    * 
	    */
		public boolean playerRequired(final CommandSender sender, final String[] args) {
			return (!this.player || (!(sender instanceof ConsoleCommandSender)) 
				 || (this.equals(Execute.RANK) && args.length == 2));
		}
	}
}