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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import me.RockinChaos.signutils.handlers.PermissionsHandler;
import me.RockinChaos.signutils.handlers.ServerHandler;

public class ChatTab implements TabCompleter {
	
   /**
	* Called when a Player tries to TabComplete.
    * @param sender - Source of the command.
    * @param command - Command which was executed.
    * @param label - Alias of the command which was used.
    * @param args - Passed command arguments.
    * @return The String List of TabComplete commands.
	*/
	@Override
	public List < String > onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
    	final List < String > completions = new ArrayList < > ();
    	final List < String > commands = new ArrayList < > ();
    	Collection < ? > playersOnlineNew = null;
    	Player[] playersOnlineOld;
    	if (args.length == 2 && args[0].equalsIgnoreCase("help") && PermissionsHandler.getPermissions().hasPermission(sender, "singutils.use")) {
    		commands.add("2");
    	} else if (args.length == 2 && args[0].equalsIgnoreCase("rank") && PermissionsHandler.getPermissions().hasPermission(sender, "signutils.rank.others")) {
    		try {
    			if (Bukkit.class.getMethod("getOnlinePlayers", new Class < ? > [0]).getReturnType() == Collection.class) {
    				if (Bukkit.class.getMethod("getOnlinePlayers", new Class < ? > [0]).getReturnType() == Collection.class) {
    					playersOnlineNew = ((Collection < ? > ) Bukkit.class.getMethod("getOnlinePlayers", new Class < ? > [0]).invoke(null, new Object[0]));
    					for (Object objPlayer: playersOnlineNew) {
    						commands.add(((Player) objPlayer).getName());
    					}
    				}
    			} else {
    				playersOnlineOld = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers", new Class < ? > [0]).invoke(null, new Object[0]));
    				for (Player player: playersOnlineOld) {
    					commands.add(player.getName());
    				}
    			}
    		} catch (Exception e) {
    			ServerHandler.getServer().sendDebugTrace(e);
    		}
    	} else if (args.length == 1) {
    		if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.use")) { commands.add("help"); }
    		if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.permissions")) { commands.add("permissions"); }
    		if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.rank")) { commands.add("rank"); }
    		if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.reload")) { commands.add("reload"); }
    		if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.updates")) { commands.add("updates"); }
    		if (PermissionsHandler.getPermissions().hasPermission(sender, "signutils.autoupdate")) { commands.add("autoupdate"); }
    	}
    	StringUtil.copyPartialMatches(args[(args.length - 1)], commands, completions);
    	Collections.sort(completions);
    	return completions;
    }
}