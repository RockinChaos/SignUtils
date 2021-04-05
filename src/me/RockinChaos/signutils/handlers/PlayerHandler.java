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

import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.domedd.betternick.BetterNick;
import me.RockinChaos.signutils.utils.ServerUtils;
import me.RockinChaos.signutils.utils.api.DependAPI;
import me.RockinChaos.signutils.utils.api.LegacyAPI;

public class PlayerHandler {

   /**
    * Gets the Player instance from their String name.
    * 
    * @param playerName - The player name to be transformed.
    * @return The fetched Player instance.
    */
	public static Player getPlayerString(final String playerName) {
		Player args = null;
		try { args = Bukkit.getPlayer(UUID.fromString(playerName)); } catch (Exception e) {}
		if (playerName != null && DependAPI.getDepends(false).nickEnabled()) {
			try { 
				de.domedd.betternick.api.nickedplayer.NickedPlayer np = new de.domedd.betternick.api.nickedplayer.NickedPlayer(LegacyAPI.getPlayer(playerName));
				if (np.isNicked()) { return LegacyAPI.getPlayer(np.getRealName()); }
				else { return LegacyAPI.getPlayer(playerName); }
			} catch (NoClassDefFoundError e) {
				if (BetterNick.getApi().isPlayerNicked(LegacyAPI.getPlayer(playerName))) { return LegacyAPI.getPlayer(BetterNick.getApi().getRealName(LegacyAPI.getPlayer(playerName))); }
				else { return LegacyAPI.getPlayer(playerName); }
			}
		} else if (args == null) { return LegacyAPI.getPlayer(playerName); }
		return args;
	}

   /**
    * Executes an input of methods for the currently online players.
    * 
    * @param input - The methods to be executed.
    */
    public static void forOnlinePlayers(final Consumer<Player> input) {
		try {
		  /** New method for getting the current online players.
			* This is for MC 1.12+
			*/
			if (Bukkit.class.getMethod("getOnlinePlayers", new Class < ? > [0]).getReturnType() == Collection.class) {
				for (Object objPlayer: ((Collection < ? > ) Bukkit.class.getMethod("getOnlinePlayers", new Class < ? > [0]).invoke(null, new Object[0]))) { 
					input.accept(((Player) objPlayer));
				}
			} 
		  /** New old for getting the current online players.
			* This is for MC versions below 1.12.
			* 
			* @deprecated Legacy version of getting online players.
			*/
			else {
				for (Player player: ((Player[]) Bukkit.class.getMethod("getOnlinePlayers", new Class < ? > [0]).invoke(null, new Object[0]))) {
					input.accept(player);
				}
			}
		} catch (Exception e) { ServerUtils.sendDebugTrace(e); }
	}
}