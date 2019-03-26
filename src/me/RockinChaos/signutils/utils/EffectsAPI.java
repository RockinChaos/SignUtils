package me.RockinChaos.signutils.utils;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;

import me.RockinChaos.signutils.handlers.ServerHandler;

public class EffectsAPI {
	
	public static void playEffect(Block block, String sParticle, String sSound) {
		if (ServerHandler.hasCombatUpdate()) {
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
