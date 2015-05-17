package cn.edu.hhu.reg.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.api.ApiClient;
import cn.edu.hhu.reg.api.ResponseUtils;
import cn.edu.hhu.reg.api.SimpleResponse;
import cn.edu.hhu.reg.common.CustomProgressDialog;
import cn.edu.hhu.reg.common.RexUtils;
import cn.edu.hhu.reg.common.ToastShow;
import cn.edu.hhu.reg.common.http.HttpException;
import cn.edu.hhu.reg.common.http.async.HttpAsync;
import cn.edu.hhu.reg.entity.UserLogin;

public class RegisterActivity extends BaseActivity {

	EditText et_email;
	EditText et_password;
	EditText et_password2;
	Button btn_register;
	CustomProgressDialog progresDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		UiHelper.goBack(this);
		et_email = (EditText) findViewById(R.id.register_et_email);
		et_password = (EditText) findViewById(R.id.register_et_userPassword);
		et_password2 = (EditText) findViewById(R.id.register_et_userPassword2);
		findViewById(R.id.register_btn_register).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				register();
			}
		});
	}
	
	private void register() {
		String email = et_email.getText().toString();
		String password = et_password.getText().toString();
		String password2 = et_password2.getText().toString();
		if(TextUtils.isEmpty(email)){
			ToastShow.shortT("邮箱不能为空！");
			return;
		}
		if(TextUtils.isEmpty(password)){
			ToastShow.shortT("密码不能为空！");
			return;
		}
		if(!password.equals(password2)){
			ToastShow.shortT("两次密码不一致！");
			return;
		}
		if(!RexUtils.isEmail(email)){
			ToastShow.shortT("邮箱格式不正确！");
			return;
		}
		if(!RexUtils.isPasswordValidate(password)){
			ToastShow.shortT("密码格式不正确！");
			return;
		}
		HttpAsync httpAsync = new HttpAsync() {
			@Override
			protected void onSuccess(String json) {
				if(progresDialog!=null&&progresDialog.isShowing()) progresDialog.dismiss();
				try {
					SimpleResponse<UserLogin> loginResponse = ResponseUtils.loginResponse(json);
					if(loginResponse.isSuccess()){
						ToastShow.shortT("注册成功！");
						RegisterActivity.this.finish();
					}else{
						String message = loginResponse.getMessage();
						if(message==null){
							message="";
						}
						ToastShow.shortT("注册失败！"+message);
					}
				} catch (Exception e) {
					ToastShow.shortT(e.getMessage());
				}
			}
			@Override
			public void onError(HttpException exception) {
				if(progresDialog!=null&&progresDialog.isShowing()) progresDialog.dismiss();
				ToastShow.shortT("注册失败！"+exception.getMessage());
			}
		};
		if(progresDialog==null){
			progresDialog = new CustomProgressDialog(this,"正在注册！");
		}
		progresDialog.show();
		ApiClient.register(email, password2, httpAsync);
	}
	
	@Override
	protected void onDestroy() {
		if(progresDialog!=null&&progresDialog.isShowing()) progresDialog.dismiss();
		super.onDestroy();
	}
}
