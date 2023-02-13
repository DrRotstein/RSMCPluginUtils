package com.drrotstein.rsmcutils.server;

import org.bukkit.Bukkit;

public class ServerUtils {
	
	public static ServerVersion getServerVersion() {
		return ServerVersion.fromString(Bukkit.getVersion());
	}
	
}
