package cn.edu.hhu.reg.common;

import java.util.regex.Pattern;

public class RexUtils {
	private static Pattern emailPattern =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	private static Pattern passwordPattern = Pattern.compile("[\\w\\.\\*\\.\\?\\+\\$\\^\\[\\]\\(\\)\\{\\}\\|\\\\/~!@#%&-_=\"\';:,<>`]{6,22}");

	public static boolean isEmail(String email){
		return emailPattern.matcher(email).matches();
	}
	
	public static boolean isPasswordValidate(String password){
		return passwordPattern.matcher(password).matches();
	}
}
