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
package me.RockinChaos.signutils.signs;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.RockinChaos.signutils.handlers.PlayerHandler;
import me.RockinChaos.signutils.utils.api.DependAPI;
import me.RockinChaos.signutils.utils.api.LanguageAPI;
import net.milkbowl.vault.permission.Permission;

public class Ranks {
	
   /**
    * Checks for the associated player groups.
    * 
    * @param playerName - The player to have their rank checked.
    * @param otherPlayer - The other player to have their rank checked.
    */
	public static void signRank(CommandSender sender, String otherPlayer) {
		if (DependAPI.getDepends(false).getVault().vaultError(sender, true)) {
			if (otherPlayer != null && !otherPlayer.isEmpty() && PlayerHandler.getPlayerString(otherPlayer) == null) {
				String[] placeHolders = LanguageAPI.getLang(false).newString();
				placeHolders[4] = otherPlayer;
				LanguageAPI.getLang(false).sendLangMessage("commands.default.noTarget", sender, placeHolders);
			} else {
				Player player = null;
				if (otherPlayer != null && !otherPlayer.isEmpty()) {
					player = PlayerHandler.getPlayerString(otherPlayer);
				} else if (sender instanceof Player) { player = (Player) sender; }
				String[] placeHolders = LanguageAPI.getLang(false).newString();
				int groups = 0;
				Permission permissionGroups = DependAPI.getDepends(false).getVault().getPermissions();
				try { DependAPI.getDepends(false).getVault().getPermissions().getPlayerGroups(player); } catch (Exception e) { permissionGroups = null;}
				if (permissionGroups != null) {
					groups = DependAPI.getDepends(false).getVault().getPermissions().getPlayerGroups(player).length;
				}
				placeHolders[2] = Integer.toString(groups);
				placeHolders[6] = player.getName();
				LanguageAPI.getLang(false).sendLangMessage("signs.rank.playerLine", sender, placeHolders);
				LanguageAPI.getLang(false).sendLangMessage("signs.rank.groupSizeLine", sender, placeHolders);
				if (permissionGroups != null) {
					for (String groupName: DependAPI.getDepends(false).getVault().getPermissions().getPlayerGroups(player)) {
						placeHolders[3] = groupName;
						LanguageAPI.getLang(false).sendLangMessage("signs.rank.groupListLine", sender, placeHolders);
					}
				}
			}
		}
	}
}