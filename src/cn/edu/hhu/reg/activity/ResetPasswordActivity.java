package cn.edu.hhu.reg.activity;

import cn.edu.hhu.reg.AppConfig;
import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.api.ApiClient;
import cn.edu.hhu.reg.api.ResponseUtils;
import cn.edu.hhu.reg.api.SimpleResponse;
import cn.edu.hhu.reg.common.CustomProgressDialog;
import cn.edu.hhu.reg.common.RexUtils;
import cn.edu.hhu.reg.common.ToastShow;
import cn.edu.hhu.reg.common.http.HttpException;
import cn.edu.hhu.reg.common.http.async.HttpAsync;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ResetPasswordActivity extends BaseActivity {

	EditText et_newPassword;
	EditText et_newPassword2;
	Button btn_commit;
	CustomProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resetpassword);
		UiHelper.goBack(this);
		et_newPassword = (EditText) findViewById(R.id.resetPsw_et_newPass);
		et_newPassword2 = (EditText) findViewById(R.id.resetPsw_et_newPass_again);
		btn_commit = (Button) findViewById(R.id.resetPsw_btn_commit);
		btn_commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resetPassword();
			}
		});
	}

	private void resetPassword() {
		String uid = AppConfig.getAppConfig().getLoginInfo(this).getUid();
		if(TextUtils.isEmpty(uid)){
			ToastShow.shortT("请重新登录！");
			return;
		}
		String password = et_newPassword.getText().toString();
		String password2 = et_newPassword2.getText().toString();
		if(TextUtils.isEmpty(password)){
			ToastShow.shortT("请输入新密码！");
			return;
		}
		if(!RexUtils.isPasswordValidate(password)){
			ToastShow.shortT("密码格式不正确！");
			return;
		}
		if(!password.equals(password2)){
			ToastShow.shortT("两次密码不一致！");
			return;
		}
		HttpAsync httpAsync = new HttpAsync() {
			
			@Override
			protected void onSuccess(String json) {
				if(progressDialog!=null&&progressDialog.isShowing()) progressDialog.dismiss();
				try {
					SimpleResponse<Object> response = ResponseUtils.SimpleResponse(json);
					if(response.isSuccess()){
						ToastShow.shortT("修改成功！");
						ResetPasswordActivity.this.finish();
					}else{
						String msg = response.getMessage();
						if(msg==null){
							msg="";
						}
						ToastShow.shortT("修改失败！"+msg);
					}
				} catch (Exception e) {
					ToastShow.shortT(e.getMessage());
				}
			}
			
			@Override
			public void onError(HttpException exception) {
				if(progressDialog!=null&&progressDialog.isShowing()) progressDialog.dismiss();
				ToastShow.shortT("修改失败！"+exception.getMessage());
			}
		};
		if(progressDialog==null){
			progressDialog = new CustomProgressDialog(this,"正在修改！");
		}
		progressDialog.show();
		ApiClient.userPasswordReset(uid, password, httpAsync);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(progressDialog!=null&&progressDialog.isShowing()) progressDialog.dismiss();
	}
}
