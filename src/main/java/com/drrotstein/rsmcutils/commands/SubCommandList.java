package com.drrotstein.rsmcutils.commands;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class SubCommandList extends ArrayList<SubCommand> {
	
	public SubCommandList(SubCommand... commands) {
		for(SubCommand command : commands) add(command);
	}
	
	public SubCommand get(String label) {
		for(SubCommand sc : this) if(sc.getLabel().equals(label)) return sc;
		return null;
	}
	
}
