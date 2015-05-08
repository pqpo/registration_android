package cn.edu.hhu.reg.activity;

import cn.edu.hhu.reg.AppManager;
import android.app.Activity;
import android.os.Bundle;

/**
 * Activity基类
 * @author qlm
 * @created 2014.10.10
 */
public class BaseActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivty(this);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}
}
