package com.drrotstein.rsmcutils.commands;

import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand extends SubCommandParent {
	
	public abstract String getLabel();
	public abstract String getPermission();
	
	@Override
	public List<String> getHelpMessage() {
		return new ArrayList<>();
	}
	
}
