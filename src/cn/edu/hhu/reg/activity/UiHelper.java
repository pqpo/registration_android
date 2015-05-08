package cn.edu.hhu.reg.activity;

import android.app.Activity;
import android.content.Intent;

public class UiHelper {

	/**
	 * 主页面
	 * @param ac
	 */
	public static void home(Activity ac){
		Intent intent = new Intent();
		intent.setClass(ac, HomeActivity.class);
		ac.startActivity(intent);
	}
}
