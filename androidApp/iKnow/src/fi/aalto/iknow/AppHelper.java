package fi.aalto.iknow;

import android.app.Activity;
import android.content.SharedPreferences;

public final class AppHelper {
	private static final String SETTING_INFOS = "SETTING_Infos";
	private static final String USERNAME_KEY = "IKNOW_USERNAME";
	private static final String PASSWORD_KEY = "IKNOW_PASSWORD";
	
	public static String getUsername(final Activity activity){
		String username = null;
//		SharedPreferences settings = activity.getSharedPreferences(SETTING_INFOS, 0);
//		username = settings.getString(USERNAME_KEY, "");
		return username;
	}
	public static String getPassword(final Activity activity){
		String password = null;
//		SharedPreferences settings = activity.getSharedPreferences(SETTING_INFOS, 0);
//		password = settings.getString(PASSWORD_KEY, "");
		return password;
	}
}
