package me.RockinChaos.signutils.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;

public class EffectsAPI {
	
	public static void playEffect(Block block, Particle particle, Sound sound) {
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
