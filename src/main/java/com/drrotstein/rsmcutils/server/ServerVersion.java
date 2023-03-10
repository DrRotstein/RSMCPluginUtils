package com.drrotstein.rsmcutils.server;

public enum ServerVersion {
	
	V1_7_10,
	V1_8,
	V1_8_3,
	V1_8_4,
	V1_8_5,
	V1_8_6,
	V1_8_7,
	V1_8_8,
	V1_9,
	V1_9_2,
	V1_9_4,
	V1_10,
	V1_10_2,
	V1_11,
	V1_11_1,
	V1_11_2,
	V1_12,
	V1_12_1,
	V1_12_2,
	V1_13,
	V1_13_1,
	V1_13_2,
	V1_14,
	V1_14_1,
	V1_14_2,
	V1_14_3,
	V1_14_4,
	V1_15,
	V1_15_1,
	V1_15_2,
	V1_16_1,
	V1_16_2,
	V1_16_3,
	V1_16_4,
	V1_16_5,
	V1_17,
	V1_17_1,
	V1_18,
	V1_18_1,
	V1_18_2,
	V1_19,
	V1_19_1,
	V1_19_2,
	V1_19_3;
	
	
	public boolean newerThan(ServerVersion compare) {
		int[] v = versions();
		int[] vComp = compare.versions();
		return v[0] > vComp[0] || (v[0] == vComp[0] && v[1] > vComp[1]) || (v[0] == vComp[0] && v[1] == vComp[1] && v[2] > vComp[2]);
	}
	
	public boolean olderThan(ServerVersion compare) {
		return this != compare && !newerThan(compare);
	}
	
	public boolean newerEquals(ServerVersion compare) {
		return this == compare || newerThan(compare);
	}
	
	public boolean olderEquals(ServerVersion compare) {
		return !newerThan(compare);
	}
	
	public String toVersionString() {
		int[] versions = versions();
		return versions[0] + "." + versions[1] + "." + versions[2];
	}
	
	public int[] versions() {
		String[] v = name().split("_");
		return new int[] {Integer.parseInt(v[0].substring(1)), Integer.parseInt(v[1]), Integer.parseInt(v[2])};
	}
	
	
	public static ServerVersion fromString(String string) {
		String[] v = string.split("\\.");
		if(v.length != 3) return null;
		for(ServerVersion sv : values()) if(sv.name().equals("V" + v[0] + "_" + v[1] + "_" + v[2])) return sv;
		return null;
	}
	
}
