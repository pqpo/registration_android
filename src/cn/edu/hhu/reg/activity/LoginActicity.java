package cn.edu.hhu.reg.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ToggleButton;
import cn.edu.hhu.reg.AppConfig;
import cn.edu.hhu.reg.AppContext;
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

public class LoginActicity extends BaseActivity {
	
	EditText et_email;
	EditText et_password;
	ToggleButton tb_rememberPassword;
	CustomProgressDialog progresDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		UiHelper.goBack(this);
		et_email = (EditText) findViewById(R.id.login_et_email);
		et_password = (EditText) findViewById(R.id.login_et_userPassword);
		tb_rememberPassword = (ToggleButton) findViewById(R.id.login_tb_rememberPassword);
		
		findViewById(R.id.login_btn_login).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});;
		
		findViewById(R.id.login_tv_register).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UiHelper.register(LoginActicity.this);
			}
		});;
	}

	private void login() {
		String email = et_email.getText().toString();
		String password = et_password.getText().toString();
		if(TextUtils.isEmpty(email)){
			ToastShow.shortT("邮箱不能为空！");
			return;
		}
		if(TextUtils.isEmpty(password)){
			ToastShow.shortT("密码不能为空！");
			return;
		}
		if(!RexUtils.isEmail(email)){
			ToastShow.shortT("邮箱格式不正常！");
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
						UserLogin userLogin = loginResponse.getData();
						if(userLogin==null){
							ToastShow.shortT("登录失败（无法获取用户信息）！");
							return;
						}
						AppConfig.getAppConfig().setLoginInfo(LoginActicity.this, userLogin);
						if(tb_rememberPassword.isChecked()){
							AppConfig.getAppConfig().setLoginTag(LoginActicity.this, true);
						}else{
							AppConfig.getAppConfig().setLoginTag(LoginActicity.this, false);
						}
						AppContext.isLoginSuccess = true;
						ToastShow.shortT("登录成功！");
						LoginActicity.this.finish();
					}else{
						String message = loginResponse.getMessage();
						if(message==null){
							message="";
						}
						ToastShow.shortT("登录失败！"+message);
					}
				} catch (Exception e) {
					ToastShow.shortT(e.getMessage());
				}
			}
			@Override
			public void onError(HttpException exception) {
				if(progresDialog!=null&&progresDialog.isShowing()) progresDialog.dismiss();
				ToastShow.shortT("登录失败！"+exception.getMessage());
			}
		};
		if(progresDialog==null){
			progresDialog = new CustomProgressDialog(this,"正在登录！");
		}
		progresDialog.show();
		ApiClient.login(email, password, httpAsync);
	}
	
	@Override
	protected void onDestroy() {
		if(progresDialog!=null&&progresDialog.isShowing()) progresDialog.dismiss();
		super.onDestroy();
	}
}
