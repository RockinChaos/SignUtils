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
package me.RockinChaos.signutils.utils.api;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;

import me.RockinChaos.signutils.utils.ServerUtils;

public class EffectAPI {
	
   /**
    * Attempts to play the specified particle and sound at the block location.
    * 
    * @param block - The Block to be located.
    * @param sParticle - The String name of the Particle.
    * @param sSound - The String name of the Sound.
    */
	public static void playEffect(Block block, String sParticle, String sSound) {
		if (ServerUtils.hasSpecificUpdate("1_9")) {
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
}