package cn.edu.hhu.reg;

import cn.edu.hhu.reg.entity.UserLogin;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
	
	public boolean getLoginTag(Context context){
		SharedPreferences sp = AppConfig.getSharedPreferences(context);
		return sp.getBoolean("isLogin", false);
	}
	
	public void setLoginTag(Context context,boolean isLogin){
		SharedPreferences sp = AppConfig.getSharedPreferences(context);
		Editor edit = sp.edit();
		edit.putBoolean("isLogin", isLogin);
		edit.commit();
	}
	
	public UserLogin getLoginInfo(Context context){
		SharedPreferences sp = AppConfig.getSharedPreferences(context);
		UserLogin userLogin = new UserLogin();
		String uid = sp.getString("uid", "");
		String email = sp.getString("email", "");
		userLogin.setUid(uid);
		userLogin.setEmail(email);
		return userLogin;
	}
	
	public void setLoginInfo(Context context,UserLogin userLogin){
		SharedPreferences sp = AppConfig.getSharedPreferences(context);
		Editor edit = sp.edit();
		edit.putString("uid", userLogin.getUid());
		edit.putString("email", userLogin.getEmail());
		edit.commit();
	}
}
