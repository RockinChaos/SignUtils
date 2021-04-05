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
package me.RockinChaos.signutils.handlers;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PermissionsHandler {
	
   /**
    * Checks if the sender has permission.
    * 
    * @param sender - The entity that is having their permissions checked.
    * @param permission - The permission the sender is expected to have.
    * @return If the entity has the proper permission.
    */
	public static boolean hasPermission(final CommandSender sender, final String permission) {
		if (sender.hasPermission(permission) || sender.hasPermission("signutils.*") || sender.hasPermission("signutils.all") || isDeveloper(sender) || (sender instanceof ConsoleCommandSender)) {
			return true;
		} else if (!ConfigHandler.getConfig().getFile("config.yml").getBoolean("Permissions.Commands-OP") && sender.isOp()) {
			if (permission.equalsIgnoreCase("signutils.use") || permission.equalsIgnoreCase("signutils.reload") || permission.equalsIgnoreCase("signutils.updates")
					|| permission.equalsIgnoreCase("signutils.upgrade") || permission.equalsIgnoreCase("signutils.create") || permission.equalsIgnoreCase("signutils.rank") 
					|| permission.equalsIgnoreCase("signutils.rank.others") || isDeveloper(sender)) {
				return true;
			}
		}
		return false;
	}
	
   /**
    * If Debugging Mode is enabled, the plugin developer will be allowed to execute ONLY this plugins commands for help and support purposes.
    * 
    * @param sender - The entity executing the plugin command.
    * @return If the command sender is the developer of the plugin.
    */
	private static boolean isDeveloper(final CommandSender sender) {
		if (ConfigHandler.getConfig().debugEnabled()) {
			if (sender instanceof Player) {
				try { 
					if (((Player)sender).getUniqueId().toString().equalsIgnoreCase("ad6e8c0e-6c47-4e7a-a23d-8a2266d7baee")) { return true; }
				} catch (Exception e) { if (sender.getName().equalsIgnoreCase("RockinChaos")) { return true; } }
			}
		}
		return false;
	}
}