package com.drrotstein.rsmcutils.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class LocationUtils {
	
	/**
	 * Returns a list of coordinates for tab-completion, when the player is looking at a block
	 * @param player The player that is tab-completing
	 * @param args The arguments of the tab-completion
	 * @return A list of coordinates for tab-completion, when the player is looking at a block
	 */
	public static List<String> lookingAtTabCompletion(Player player, String[] args) {
		List<String> opt = new ArrayList<>();
		//Tabcomplete existing worlds
		if(args.length == 1) for(World world : Bukkit.getWorlds()) opt.add(world.getName());
		if(player != null) {
			//Get the location the player is looking at and get xyz into strings
			Location lookingAt = player != null ? player.getTargetBlock((Set<Material>) null, 10).getLocation() : null;
			String x = lookingAt.getBlockX() + "", y = lookingAt.getBlockY() + "", z = lookingAt.getBlockZ() + "";
			//If looking at block is too far away, complete with '~'
			if(player.getLocation().distance(lookingAt) > 4) {
				x = y = z = "~";
			}
			//Get the args-index of where the coordinates start
			int coordsStartIndex = args.length >= 1 && Bukkit.getWorld(args[0]) != null ? 1 : 0;
			//Tabcomplete coordinates of the location the player is looking at
			if(args.length == coordsStartIndex + 1 || args.length == coordsStartIndex + 4) {
				opt.add(x);
				opt.add(x + " " + y);
				opt.add(x + " " + y + " " + z);
			}
			if(args.length == coordsStartIndex + 2 || args.length == coordsStartIndex + 5) {
				opt.add(y);
				opt.add(y + " " + z);
			}
			if(args.length == coordsStartIndex + 3 || args.length == coordsStartIndex + 6) {
				opt.add(z);
			}
		}
		return opt;
	}
	
	/**
	 * Convert a location into readable text.
	 * @param loc The location
	 * @param separator The seperator to be used, e.g 1,1,1 or "1 1 1"
	 * @return The {@link loc} in form of a readable text
	 */
	public static String display(Location loc, String separator) {
		return display(loc, separator, false);
	}
	
	/**
	 * Convert a location into readable text.
	 * @param loc The location
	 * @param separator The seperator to be used, e.g 1,1,1 or "1 1 1"
	 * @param showYawPitch Whether or not the yaw and pitch values should be added to the result or not
	 * @return The {@link loc} in form of a readable text
	 */
	public static String display(Location loc, String separator, boolean showYawPitch) {
		return loc.getWorld().getName() + separator + ifDecimal(loc.getX()) + separator + ifDecimal(loc.getY()) + separator + ifDecimal(loc.getZ()) + (showYawPitch ? separator + ifDecimal(loc.getYaw()) + separator + ifDecimal(loc.getPitch()) : "");
	}
	
	/**
	 * Returns {@link in} as string. Automatically removes decimals if there are none
	 * @param in
	 * @return {@link in} as string. Automatically removes decimals if there are none
	 */
	private static String ifDecimal(double in) {
		return String.valueOf(in % 1 == 0 ? ((int) in + "") : in);
	}
	
	/**
	 * Returns {@link in} as string. Automatically removes decimals if there are none
	 * @param in
	 * @return {@link in} as string. Automatically removes decimals if there are none
	 */
	private static String ifDecimal(float in) {
		return String.valueOf(in % 1 == 0 ? ((int) in + "") : in);
	}
	
	/**
	 * Decodes a location from string, e.g. world,123,53,531
	 * @param location
	 * @param separator
	 * @return The from string decoded location.
	 */
	public static Location undisplay(String location, String separator) {
		try {
			String[] parts = location.split(separator);
			if(Bukkit.getWorld(parts[0]) == null) return null;
			Double[] doubleParts = new Double[4];
			for(int i = 1; i < 4; i++) doubleParts[i] = Double.parseDouble(parts[i] + "D");
			float yaw = 0f;
			float pitch = 0f;
			if(parts.length == 6) {
				yaw = Float.parseFloat(parts[4]);
				pitch = Float.parseFloat(parts[5]);
			}
			return new Location(Bukkit.getWorld(parts[0]), doubleParts[1], doubleParts[2], doubleParts[3], yaw, pitch);
		} catch(NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * Decodes a location from string and player, e.g. world,123,53,531 or 53-214-76. The main difference to
	 * {@link #undisplay(String, String)} is, that, when a player is given, you can use '~' or '^' to imply relative coordinates,
	 * e.g. "world ~ ~ ~"
	 * @param player
	 * @param location
	 * @param separator
	 * @return The from string decoded location.
	 */
	public static Location undisplay(Player player, String location, String separator) {
		try {
			Location pl = player.getLocation();
			String[] parts = location.split(separator);
			
			switch(parts.length) {
			case 3:
				String[] copy = parts.clone();
				parts = new String[4];
				for(int i = 0; i < 3; i++) parts[i + 1] = copy[i];
				if(parts[1].equals("~") || parts[1].equals("^")) parts[1] = pl.getX() + "";
				parts[0] = pl.getWorld().getName();
				if(parts[2].equals("~") || parts[2].equals("^")) parts[2] = pl.getY() + "";
				if(parts[3].equals("~") || parts[3].equals("^")) parts[3] = pl.getZ() + "";
				break;
				
			case 6:
				if(parts[5].equals("~") || parts[5].equals("^")) parts[5] = pl.getPitch() + "";
				//No break, because the code below must be executed as well for this to work
			case 5:
				if(parts[4].equals("~") || parts[4].equals("^")) parts[4] = pl.getYaw() + "";
			case 4:
				if(parts[0].equals("~") || parts[0].equals("^")) parts[0] = pl.getWorld().getName();
				if(parts[1].equals("~") || parts[1].equals("^")) parts[1] = pl.getX() + "";
				if(parts[2].equals("~") || parts[2].equals("^")) parts[2] = pl.getY() + "";
				if(parts[3].equals("~") || parts[3].equals("^")) parts[3] = pl.getZ() + "";
			}
			if(Bukkit.getWorld(parts[0]) == null) return null;
			Double[] doubleParts = new Double[4];
			for(int i = 1; i < 4; i++) doubleParts[i] = (Math.round(Double.parseDouble(parts[i] + "D") * 100) / 100D);
			float yaw = 0f;
			float pitch = 0f;
			if(parts.length == 6) {
				yaw = (Math.round(Float.parseFloat(parts[4]) * 100) / 100f);
				pitch = (Math.round(Float.parseFloat(parts[5]) * 100) / 100f);
			}
			return new Location(Bukkit.getWorld(parts[0]), doubleParts[1], doubleParts[2], doubleParts[3], yaw, pitch);
		} catch(NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * Returns the location input with x, y, z set to their block positions (basically casts all x, y, z to integers).
	 * @param in The location
	 * @return The location input with x, y, z set to their block positions (basically casts all x, y, z to integers).
	 */
	public static Location toBlockPos(Location in) {
		return new Location(in.getWorld(), in.getBlockX(), in.getBlockY(), in.getBlockZ());
	}
	
}
