package cn.edu.hhu.reg.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import cn.edu.hhu.reg.AppConfig;
import cn.edu.hhu.reg.AppContext;
import cn.edu.hhu.reg.R;

/**
 * 程序引导页
 * @author qlm
 * @created 2014.10.10
 */
public class Splash extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view = View.inflate(this, R.layout.activity_splash, null);
		view.setBackgroundResource(R.drawable.splash);
		setContentView(view);
		
		//设置动画
		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		aa.setDuration(2000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
				AppContext.isLoginSuccess = AppConfig.getAppConfig().getLoginTag(Splash.this);
				if(AppContext.isDebug)Log.d("IsLoginSuccess", AppContext.isLoginSuccess+"");
			}
			@Override
			public void onAnimationRepeat(Animation arg0) {
			}
			@Override
			public void onAnimationEnd(Animation arg0) {
				redirectTo();
			}
		});
	}
	
	//跳转
	private void redirectTo() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}
	
}
