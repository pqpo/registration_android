package cn.edu.hhu.reg;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

/**
 * 全局应用管理类
 * @author qlm
 * @created 2014.10.10
 */
public class AppManager {

	private static final String TAG = AppManager.class.getSimpleName();
	private static AppManager appManager = new AppManager();
	private static Stack<Activity> activities = new Stack<Activity>();
	private AppManager(){};
	
	/**
	 * 获取Appmenager单一实例
	 * @return
	 */
	public static AppManager getAppManager(){
		return appManager;
	}
	
	/**
	 * 增加activity到栈
	 * @param activity
	 */
	public void addActivty(Activity activity){
		activities.add(activity);
	}
	/**
	 * 获取当前activity
	 * @return
	 */
	public Activity getCurrentActivity(){
		return activities.lastElement();
	}
	/**
	 * 结束activity
	 */
	public void finishActivity(){
		Activity a = activities.lastElement();
		if(a != null){
			finishActivity(a);
		}
	}
	/**
	 * 结束指定activity
	 * @param a
	 */
	public void finishActivity(Activity a) {
		if(a!=null){
			activities.remove(a);
			a.finish();
			a = null;
		}
	}
	/**
	 * 结束指定activity
	 * @param cls
	 */
	public void finishActivity(Class<? super Activity> cls){
		for(Activity a : activities){
			if(a.getClass().equals(cls)){
				finishActivity(a);
			}
		}
	}
	/**
	 * 结束所有activity
	 */
	public void finishAllActivity(){
		for (int i = 0; i < activities.size(); i++) {
			if(activities.get(i)!=null){
				activities.get(i).finish();
			}
		}
		activities.clear();
	}
	/**
	 * 退出程序
	 * @param ctx
	 */
	public void appExit(Context ctx){
		try {
			finishAllActivity();
			ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
			am.killBackgroundProcesses(ctx.getPackageName());
			System.exit(0);
			if(AppContext.isDebug){
				Log.d(TAG, "*********************Exited******************");
			}
		} catch (Exception e) {
		}
	}
}
