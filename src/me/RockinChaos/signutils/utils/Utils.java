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
package me.RockinChaos.signutils.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.domedd.betternick.BetterNick;
import me.RockinChaos.signutils.handlers.ServerHandler;
import me.clip.placeholderapi.PlaceholderAPI;

public class Utils {
	
	private static Utils util;
	
   /**
    * Attempts to play the specified particle and sound at the block location.
    * 
    * @param block - The Block to be located.
    * @param sParticle - The String name of the Particle.
    * @param sSound - The String name of the Sound.
    */
	public void playEffect(Block block, String sParticle, String sSound) {
		if (ServerHandler.getServer().hasSpecificUpdate("1_9")) {
			org.bukkit.Particle particle = org.bukkit.Particle.valueOf(sParticle);
			Sound sound = Sound.valueOf(sSound);
			World world = block.getLocation().getWorld();
			Location location = new Location(world, block.getLocation().getX() + 0.5D, block.getLocation().getY() + 0.5D, block.getLocation().getZ() + 0.5D);
			Location location1 = new Location(world, block.getLocation().getX() + 1D, block.getLocation().getY() + 1D, block.getLocation().getZ() + 0.5D);
			Location location2 = new Location(world, block.getLocation().getX() + 0.5D, block.getLocation().getY() + 0.5D, block.getLocation().getZ() + 1D);
			Location location3 = new Location(world, block.getLocation().getX() + 0.5D, block.getLocation().getY() + 1D, block.getLocation().getZ() + 0.5D);
			world.spawnParticle(particle, location, 1);
			world.spawnParticle(particle, location1, 1);
			world.spawnParticle(particle, location2, 1);
			world.spawnParticle(particle, location3, 1);
			world.playSound(location, sound, 1.0F, 0.9F);
		}
	}

   /**
    * Checks if string1 contains string2.
    * 
    * @param string1 - The String to be checked if it contains string2.
    * @param string2- The String that should be inside string1.
    * @return If string1 contains string2.
    */
	public boolean containsIgnoreCase(final String string1, final String string2) {
		if (string1 != null && string2 != null && string1.toLowerCase().contains(string2.toLowerCase())) {
			return true;
		}
		return false;
	}
	
   /**
    * Gets a random Integer between the upper and lower limits.
    * 
    * @param lower - The lower limit.
    * @param upper - The upper limit.
    * @return The randomly selected Integer between the limits.
    */
	public int getRandom(final int lower, final int upper) {
		return new Random().nextInt((upper - lower) + 1) + lower;
	}
	
   /**
    * Translated the specified String by formatting its hex color codes.
    * 
    * @param str - The String to have its Color Codes properly Converted to Mojang Hex Colors.
    * @return The translated string.
    */
    public String translateHexColorCodes(final String str) {
    	final char COLOR_CHAR = ChatColor.COLOR_CHAR;
    	Matcher matcher = Pattern.compile("&#([A-Fa-f0-9]{6})").matcher(str);
    	StringBuffer buffer = new StringBuffer(str.length() + 4 * 8);
    	while (matcher.find()) {
    		String group = matcher.group(1);
    		matcher.appendReplacement(buffer, COLOR_CHAR + "x" + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1) + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3) + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5));
    	}
    	return matcher.appendTail(buffer).toString();
    }
	
   /**
    * Formats any color codes found in the String to Bukkit Colors so the
    * text will be colorfully formatted.
    * 
    * @param str - The String to have its Color Codes properly Converted to Bukkit Colors.
    * @return The newly formatted String.
    */
	public String colorFormat(final String str) {
		return ChatColor.translateAlternateColorCodes('&', this.translateHexColorCodes(str));
	}
	
   /**
    * Translates the specified String by foramtting its color codes and replacing placeholders.
    * 
    * @param str - The String being translated.
    * @param player - The Player having their String translated.
    * @param placeHolder - The placeholders to be replaced into the String.
    * @return The newly translated String.
    */
	public String translateLayout(String str, final Player player, final String...placeHolder) {
		String playerName = "EXEMPT";
		
		if (player != null && DependAPI.getDepends(false).nickEnabled()) {
			try {
				de.domedd.betternick.api.nickedplayer.NickedPlayer np = new de.domedd.betternick.api.nickedplayer.NickedPlayer(player);
				if (np.isNicked()) {
					playerName = np.getRealName();
				} else { playerName = player.getName(); }
			} catch (NoClassDefFoundError e) {
				try {
					if (BetterNick.getApi().isPlayerNicked(player)) {
						playerName = BetterNick.getApi().getRealName(player);
					} else { playerName = player.getName(); }	
				} catch (NullPointerException e2) { playerName = player.getName(); }
			}
		} else if (player != null) { playerName = player.getName(); }
		
		if (playerName != null && player != null && !(player instanceof ConsoleCommandSender)) {
			try { str = str.replace("%player%", playerName); } catch (Exception e) { ServerHandler.getServer().sendDebugTrace(e); }
			try { str = str.replace("%mob_kills%", String.valueOf(player.getStatistic(Statistic.MOB_KILLS))); } catch (Exception e) { ServerHandler.getServer().sendDebugTrace(e); }
			try { str = str.replace("%player_kills%", String.valueOf(player.getStatistic(Statistic.PLAYER_KILLS))); } catch (Exception e) { ServerHandler.getServer().sendDebugTrace(e); }
			try { str = str.replace("%player_deaths%", String.valueOf(player.getStatistic(Statistic.DEATHS))); } catch (Exception e) { ServerHandler.getServer().sendDebugTrace(e); }
			try { str = str.replace("%player_food%", String.valueOf(player.getFoodLevel())); } catch (Exception e) { ServerHandler.getServer().sendDebugTrace(e); }
			try { str = str.replace("%player_health%", String.valueOf(player.getHealth())); } catch (Exception e) { ServerHandler.getServer().sendDebugTrace(e); }
			try { str = str.replace("%player_location%", player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ()); } catch (Exception e) { ServerHandler.getServer().sendDebugTrace(e); } }
		if (player == null) { try { str = str.replace("%player%", "CONSOLE"); } catch (Exception e) { ServerHandler.getServer().sendDebugTrace(e); } }
		str = ChatColor.translateAlternateColorCodes('&', this.translateHexColorCodes(str));
		if (DependAPI.getDepends(false).placeHolderEnabled()) {
			try { try { return PlaceholderAPI.setPlaceholders(player, str); } 
			catch (NoSuchFieldError e) { ServerHandler.getServer().logWarn("An error has occured when setting the PlaceHolder " + e.getMessage() + ", if this issue persits contact the developer of PlaceholderAPI."); return str; }
			} catch (Exception e) { }
		}
		return str;
	}

   /**
    * Gets the instance of the Utils.
    * 
    * @return The Utils instance.
    */
    public static Utils getUtils() { 
        if (util == null) { util = new Utils(); }
        return util; 
    } 
}