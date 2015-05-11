package cn.edu.hhu.reg.common;
import cn.edu.hhu.reg.AppContext;
import android.widget.Toast;

public class ToastShow {
	
	public static synchronized void longT(String msg){
		Toast toast = Toast.makeText(AppContext.getInstance().getApplicationContext(),
				msg, Toast.LENGTH_LONG);
		toast.show();
	}
	public static synchronized void shortT(String msg){
		Toast toast = Toast.makeText(AppContext.getInstance().getApplicationContext(),
				msg, Toast.LENGTH_SHORT);
		toast.show();
	}
	
}
