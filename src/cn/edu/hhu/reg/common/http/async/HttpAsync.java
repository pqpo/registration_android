package cn.edu.hhu.reg.common.http.async;

import cn.edu.hhu.reg.AppContext;
import cn.edu.hhu.reg.common.http.HttpException;
import cn.edu.hhu.reg.common.http.HttpRequest;
import cn.edu.hhu.reg.common.http.HttpResponse;
import cn.edu.hhu.reg.common.http.HttpTransfer;
import android.os.AsyncTask;
import android.util.Log;

public abstract class HttpAsync extends AsyncTask<HttpRequest, Integer, HttpResponse> {
    private HttpException exception = new HttpException("未知错误！");
    private static final String TAG = HttpAsync.class.getSimpleName();
    
    @Override  
    protected HttpResponse doInBackground(HttpRequest... params) {
        try {
            return HttpTransfer.execute(params[0]);
        } catch (HttpException e) {
            exception = e;
            return null;
        }
    }
    
    @Override  
    protected void onPostExecute(HttpResponse response) {
        if(response != null) {
        	try {
				String body = response.body(AppContext.UTF_8);
				if(AppContext.isDebug){
					Log.d(TAG,body);
				}
				if(body!=null&&!body.equals("")){
					onSuccess(body);
				}else{
					onError(new HttpException("Body is empty!"));
				}
			} catch (HttpException e) {
				onError(e);
			}
        } else {
            onError(exception);
        }
    }

    protected abstract void onSuccess(String json);
    public abstract void onError(HttpException exception);
}