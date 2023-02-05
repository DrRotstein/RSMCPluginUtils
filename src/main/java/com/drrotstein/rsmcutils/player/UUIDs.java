package com.drrotstein.rsmcutils.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class UUIDs {
	
	public static String trimUUID(UUID uuid) {
		return uuid.toString().replaceAll("-", "");
	}
	
	public static class PlayerList extends ArrayList<UUID> {
		
		private static final long serialVersionUID = 1L;

		public boolean add(OfflinePlayer player) {
			return add(player.getUniqueId());
		}
		
		public boolean remove(OfflinePlayer player) {
			return remove(player.getUniqueId());
		}
		
		public boolean contains(OfflinePlayer player) {
			return contains(player.getUniqueId());
		}
		
		public List<OfflinePlayer> players() {
			List<OfflinePlayer> players = new ArrayList<>();
			for(UUID uuid : this) players.add(Bukkit.getOfflinePlayer(uuid));
			return players;
		}
		
	}
	
	public static class PlayerMap<V> extends HashMap<UUID, V> {
		
		private static final long serialVersionUID = 1L;

		public V put(OfflinePlayer player, V value) {
			return put(player.getUniqueId(), value);
		}
		
		public V remove(OfflinePlayer player) {
			return remove(player.getUniqueId());
		}
		
		public V get(OfflinePlayer player) {
			return super.get(player.getUniqueId());
		}
		
		public V getOrDefault(OfflinePlayer player, V defaultValue) {
			return super.getOrDefault(player.getUniqueId(), defaultValue);
		}
		
		public V getOrPut(OfflinePlayer player, V defaultValue) {
			return getOrPut(player.getUniqueId(), defaultValue);
		}
		
		public V getOrPut(UUID player, V defaultValue) {
			if(!containsKey(player)) put(player, defaultValue);
			return get(player);
		}
		
		public boolean containsKey(OfflinePlayer player) {
			return containsKey(player.getUniqueId());
		}
		
		public List<OfflinePlayer> playerKeys() {
			List<OfflinePlayer> players = new ArrayList<>();
			for(UUID uuid : keySet()) players.add(Bukkit.getOfflinePlayer(uuid));
			return players;
		}
		
	}
	
}
