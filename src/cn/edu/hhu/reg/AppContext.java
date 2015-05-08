package cn.edu.hhu.reg;

import cn.edu.hhu.reg.entity.NetConnection;
import android.app.Application;
import android.app.Service;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

/**
 * 全局应用程序类：用于保存和调用全局应用配置
 * @author qlm
 * @created 2014.10.10
 */
public class AppContext extends Application {

	private static final String TAG = AppContext.class.getSimpleName();
	public static final String UTF_8 = "UTF-8";
	public static final boolean isDebug = true;
	public static boolean isLoginSuccess = false;
	private static AppContext appContext;

	/**
	 * 获取AppContext实例
	 * @return
	 */
	public static AppContext getInstance(){
		return appContext;
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;
		if(isDebug)Log.i(TAG, "------------------------------debug---------------------------------------");
	}

	/**
	 * 获取网络连接状态
	 * @return
	 */
	public NetConnection getNetConnection(){
		NetConnection nc = NetConnection.OFFLINE;
		ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Service.CONNECTIVITY_SERVICE);
		if(cm==null)return nc;
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if(networkInfo==null) return nc;
		State state = networkInfo.getState();
		if(state==null)return nc;
		final int type = networkInfo.getType();
		switch(type){
		case 0:
			nc = NetConnection.ONMOBILE;
			break;
		case 1:
			nc = NetConnection.ONWIFE;
			break;
		default :
			break;
		}
		if(isDebug)Log.d(TAG, "NetWorkInfo:"+nc.toString());
		return nc;
	}
}
