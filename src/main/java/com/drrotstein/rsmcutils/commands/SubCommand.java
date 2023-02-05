package com.drrotstein.rsmcutils.commands;

public abstract class SubCommand extends SubCommandParent {
	
	public abstract String getLabel();
	public abstract String getPermission();
	
	@Override
	public String getHelpMessage() {
		return null;
	}
	
}
