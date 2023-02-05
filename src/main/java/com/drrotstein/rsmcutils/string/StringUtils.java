package com.drrotstein.rsmcutils.string;

public class StringUtils {
	
	public static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz",
								UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
								LETTERS = LOWERCASE_LETTERS + UPPERCASE_LETTERS,
								NUMBERS = "0123456789",
								LETTERS_NUMBERS = LETTERS + NUMBERS,
								MC_NAMING = LETTERS_NUMBERS + "_";
	
	/**
	 * Returns true, if all the characters of {@link in} are contained in {@link allowed}
	 * @param in The string to be validated
	 * @param allowed The allowed characters
	 * @return True, if all the characters of {@link in} are contained in {@link allowed}
	 */
	public static boolean validateName(String in, String allowed) {
		for(char c : in.toCharArray()) if(!allowed.contains(c + "")) return false;
		return true;
	}
	
}
