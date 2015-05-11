package cn.edu.hhu.reg.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.common.CustomProgressDialog;

@SuppressLint("HandlerLeak")
public class WebViewActivity extends BaseActivity {

	private WebView wv;
	public static final String FLAG_TITLE = "title";
	public static final String FLAG_URL = "url";
	private String url = "";
	private CustomProgressDialog lodeDialog;
	private static final int DIALOG_SHOW = 0;
	private static final int DIALOG_HIDDEN = 1;

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DIALOG_SHOW:
				if(lodeDialog==null) lodeDialog = new CustomProgressDialog(WebViewActivity.this, "正在加载！");
				if(!lodeDialog.isShowing())lodeDialog.show();
				break;
			case DIALOG_HIDDEN:
				lodeDialog.dismiss();
				break;
			default:
				break;
			}
		};
	};
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		UiHelper.goBack(this);
		
		url = getIntent().getExtras().getString(FLAG_URL);
		String title = getIntent().getExtras().getString(FLAG_TITLE,"网页浏览");
		((TextView)findViewById(R.id.webview_tv_title)).setText(title);
		
		if(url.equals(""))return;

		wv = (WebView)findViewById(R.id.webview);
		wv.getSettings().setJavaScriptEnabled(true);// 可用JS
		wv.getSettings().setSupportZoom(true);
		wv.getSettings().setUseWideViewPort(true);  
//		wv.getSettings().setLoadWithOverviewMode(true); 
		
		wv.setScrollBarStyle(0);// 滚动条风格
		wv.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(final WebView view,
					final String url) {
				handler.sendEmptyMessage(DIALOG_SHOW);
				wv.loadUrl(url);
				return true;
			}
		});
		wv.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
				if (progress == 100) {
					handler.sendEmptyMessage(DIALOG_HIDDEN);// 如果全部载入,隐藏进度对话框
				}
			}
		});

		wv.loadUrl(url);
		handler.sendEmptyMessage(DIALOG_SHOW);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(lodeDialog!=null) lodeDialog.dismiss();
	}
	@Override
	public void onBackPressed() {
		if(wv.canGoBack()){
			wv.goBack();
			return;
		}
		super.onBackPressed();
	}
}
