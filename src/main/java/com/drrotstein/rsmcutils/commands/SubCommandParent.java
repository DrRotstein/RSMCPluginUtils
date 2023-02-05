package com.drrotstein.rsmcutils.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public abstract class SubCommandParent implements CommandExecutor, TabCompleter {
	
	public abstract boolean command(CommandSender sender, Command command, String label, String[] args);
	public abstract List<String> tabComplete(CommandSender sender, Command command, String label, String[] args);
	public abstract SubCommandList getSubCommands();
	public abstract List<String> getHelpMessage();
	
	public CommandSender sender;
	public String[] args;
	
	public boolean checkForPlayer() {
		if(!(sender instanceof Player)) {
			sender.sendMessage("§cYou have to be a player to perform that command!");
			return false;
		}
		return true;
	}
	
	public boolean checkForArgsLengthEquals(int... lengths) {
		boolean isEquals = false;
		for(int length : lengths) if(args.length == length) isEquals = true;
		if(!isEquals) incorrectUsage();
		return isEquals;
	}
	
	public boolean checkForArgsLengthGreaterThan(int length) {
		if(args.length > length) {
			sender.sendMessage("§cYou entered too many arguments!");
			return false;
		}
		return true;
	}
	
	public boolean checkForArgsLengthLessThan(int length) {
		if(args.length < length) {
			sender.sendMessage("§cYou need to enter more arguments!");
			return false;
		}
		return true;
	}
	
	public void incorrectUsage() {
		sender.sendMessage("§cIncorrect usage!");
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		this.sender = arg0;
		this.args = arg3;
		if(args.length == 1 && args[0].equals("help") && hasHelpMessage()) {
			arg0.sendMessage("§bHere is a help message for §3§l/" + arg2 + "§b:");
			for(String help : getHelpMessage()) arg0.sendMessage("§7- §e" + help);
			return true;
		}
		if(hasSubCommands() && args.length > 0) return handleSubCommandsExecution(arg0, arg1, arg2, arg3);
		return command(arg0, arg1, arg2, arg3);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		this.sender = arg0;
		this.args = arg3;
		List<String> raw = new ArrayList<>();
		if(hasSubCommands()) raw.addAll(handleSubCommandsTabCompletion(arg0, arg1, arg2, arg3));
		List<String> tc = tabComplete(arg0, arg1, arg2, arg3);
		if(tc != null) raw.addAll(tc);
		if(arg3.length == 1) if(hasHelpMessage()) raw.add("help");
		List<String> opt = new ArrayList<>();
		for(String s : raw) if(s.startsWith(arg3[arg3.length - 1])) opt.add(s);
		return opt;
	}
	
	private boolean handleSubCommandsExecution(CommandSender sender, Command command, String label, String[] args) {
		if(args.length <= 0) {
			incorrectUsage();
			return false;
		}
		for(SubCommand sub : getSubCommands()) if(args[0].equals(sub.getLabel())) {
			if(!sub.getPermission().equals("") && !sender.hasPermission(sub.getPermission())) {
				sender.sendMessage("§cYou do not have permission to perform this command!");
				return false;
			}
			String[] newArgs = new String[args.length - 1];
			for(int i = 1; i < args.length; i++) newArgs[i - 1] = args[i];
			return sub.onCommand(sender, command, label, newArgs);
		}
		incorrectUsage();
		return false;
	}
	
	private List<String> handleSubCommandsTabCompletion(CommandSender sender, Command command, String label, String[] args) {
		List<String> opt = new ArrayList<>();
		for(SubCommand sub : getSubCommands()) {
			if(args.length == 1 && sub.getLabel().startsWith(args[args.length - 1])) {
				opt.add(sub.getLabel());
				continue;
			}
			if(sub.getLabel().equals(args[0])) {
				String[] newArgs = new String[args.length - 1];
				for(int i = 1; i < args.length; i++) newArgs[i - 1] = args[i];
				List<String> subTabComplete = sub.onTabComplete(sender, command, label, newArgs);
				if(subTabComplete == null) subTabComplete = new ArrayList<>();
				for(String complete : subTabComplete) if(complete.startsWith(args[args.length - 1])) opt.add(complete);
				return opt;
			}
			
		}
		return opt;
	}
	
	public boolean hasSubCommands() {
		return getSubCommands() != null && !getSubCommands().isEmpty();
	}
	
	public boolean hasHelpMessage() {
		return getHelpMessage() != null && !getHelpMessage().isEmpty();
	}
	
}
