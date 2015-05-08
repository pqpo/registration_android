package cn.edu.hhu.reg;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 应用程序配置类：用于保存用户相关信息及配
 * @author qlm
 *
 */
public class AppConfig {
	
	private static AppConfig instance = new AppConfig();
	
	private AppConfig(){};
	
	public static AppConfig getAppConfig(){
		return instance;
	}
	
	/**
	 * 获取SharedPreferences
	 * @param context
	 * @return
	 */
	public static SharedPreferences getSharedPreferences(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	
}
