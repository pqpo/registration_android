package cn.edu.hhu.reg.activity;

import cn.edu.hhu.reg.AppContext;
import cn.edu.hhu.reg.R;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class UiHelper {
	
	public static void goBack(final Activity ac){
		ac.findViewById(R.id.iv_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.finish();
			}
		});
	}

	/**
	 * 主页面
	 * @param ac
	 */
	public static void home(Activity ac){
		Intent intent = new Intent();
		intent.setClass(ac, HomeActivity.class);
		ac.startActivity(intent);
	}
	
	/**
	 * 用户信息
	 * @param ac
	 */
	public static void profile(Activity ac){
		if(!AppContext.isLoginSuccess){
			login(ac);
			return;
		}
		Intent intent = new Intent();
		intent.setClass(ac, ProfileActivity.class);
		ac.startActivity(intent);
	}
	
	/**
	 * 登录
	 * @param ac
	 */
	public static void login(Activity ac){
		Intent intent = new Intent();
		intent.setClass(ac, LoginActicity.class);
		ac.startActivity(intent);
	}
	
	/**
	 * 注册
	 * @param ac
	 */
	public static void register(Activity ac){
		Intent intent = new Intent();
		intent.setClass(ac, RegisterActivity.class);
		ac.startActivity(intent);
	}
	
	/**
	 * 网页浏览
	 * @param ac
	 * @param title
	 * @param url
	 */
	public static void web(Activity ac,String title,String url){
		Intent intent = new Intent();
		intent.putExtra(WebViewActivity.FLAG_TITLE, title);
		intent.putExtra(WebViewActivity.FLAG_URL, url);
		intent.setClass(ac, WebViewActivity.class);
		ac.startActivity(intent);
	}
}
