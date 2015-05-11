package cn.edu.hhu.reg.common;

import cn.edu.hhu.reg.R;
import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomProgressDialog extends Dialog {
	final TextView tv_msg;
	final ImageView iv_progress;
	final Context mContext;
	Animation hyperspaceJumpAnimation;

	public CustomProgressDialog(Context context) {
		this(context, "加载中！", R.style.loading_dialog);
	}
	public CustomProgressDialog(Context context, String message) {
		this(context, message, R.style.loading_dialog);
	}
	public CustomProgressDialog(Context context, String message, int theme) {
		super(context, theme);
		mContext = context;
		this.setContentView(R.layout.widget_progress_dialog);
		this.setCanceledOnTouchOutside(false);
		tv_msg = (TextView) findViewById(R.id.tv_coustomprogress_loadingmsg);
		iv_progress = (ImageView)findViewById(R.id.iv_coustomprogress);
		tv_msg.setText(message);
	}
	
	public void setMessage(String str){
		tv_msg.setText(str);
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (!hasFocus) {
			dismiss();
		}
	}

	@Override
	public void show() {
		if (hyperspaceJumpAnimation == null
				|| !hyperspaceJumpAnimation.isInitialized()) {
			// 加载动画
			hyperspaceJumpAnimation = AnimationUtils.loadAnimation(mContext,
					R.anim.loading_anim);
			// 使用ImageView显示动画
		} else {
			hyperspaceJumpAnimation.reset();
		}
		iv_progress.startAnimation(hyperspaceJumpAnimation);
		super.show();
	}

	@Override
	public void hide() {
		hyperspaceJumpAnimation.cancel();
		super.hide();
	}
}
