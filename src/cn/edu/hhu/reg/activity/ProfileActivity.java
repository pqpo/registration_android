package cn.edu.hhu.reg.activity;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.edu.hhu.reg.AppConfig;
import cn.edu.hhu.reg.AppContext;
import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.api.ApiClient;
import cn.edu.hhu.reg.api.ResponseUtils;
import cn.edu.hhu.reg.api.SimpleResponse;
import cn.edu.hhu.reg.common.CustomProgressDialog;
import cn.edu.hhu.reg.common.ToastShow;
import cn.edu.hhu.reg.common.http.HttpException;
import cn.edu.hhu.reg.common.http.async.HttpAsync;
import cn.edu.hhu.reg.entity.UserLogin;
import cn.edu.hhu.reg.entity.UserProfile;

public class ProfileActivity extends BaseActivity implements OnClickListener {

	private static final int SHOW = 0;//显示状态
	private static final int MODIFY = 1;//修改状态

	int status = SHOW;

	EditText et_email;
	EditText et_nickname;
	EditText et_age;
	RadioGroup rg_gender;
	Button btn;

	CustomProgressDialog progresDialog;

	UserProfile userProfile;
	
	int gender;//记录性别

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		UiHelper.goBack(this);
		et_email = (EditText) findViewById(R.id.profile_et_email);
		et_nickname = (EditText) findViewById(R.id.profile_et_nickname);
		et_age = (EditText) findViewById(R.id.profile_et_age);
		rg_gender = (RadioGroup) findViewById(R.id.profile_rg_gender);
		rg_gender.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(status==SHOW){
					Integer gender = userProfile.getGender();
					if(gender!=null){
						if(gender==1||gender==0){
							((RadioButton)rg_gender.getChildAt(gender)).setChecked(true);
							return;
						}
					}
					((RadioButton)rg_gender.getChildAt(0)).setChecked(true);
				}
			}
		});
		btn = (Button) findViewById(R.id.profile_btn);
		findViewById(R.id.profile_tv_logout).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AppContext.isLoginSuccess = false;
				AppConfig.getAppConfig().setLoginTag(ProfileActivity.this, false);
				ProfileActivity.this.finish();
			}
		});;
		
		et_email.setKeyListener(null);
		setEditable(false);
		et_email.setKeyListener(null);
		btn.setOnClickListener(this);
		getProfile();

	}

	private void getProfile() {
		UserLogin loginInfo = AppConfig.getAppConfig().getLoginInfo(this);
		String uid = loginInfo.getUid();
		if(TextUtils.isEmpty(uid)){
			ToastShow.shortT("获取用户id失败，请重新登录！");
			return;
		}

		HttpAsync httpAsync = new HttpAsync() {
			@Override
			protected void onSuccess(String json) {
				if(progresDialog!=null&&progresDialog.isShowing()) progresDialog.dismiss();
				SimpleResponse<UserProfile> response = ResponseUtils.proflileResponse(json);
				if(response.isSuccess()){
					userProfile = response.getData();
					showProfile();
				}else{
					String message = response.getMessage();
					if(message==null){
						message="";
					}
					ToastShow.shortT("获取用户信息失败！"+message);
				}
			}

			@Override
			public void onError(HttpException exception) {
				if(progresDialog!=null&&progresDialog.isShowing()) progresDialog.dismiss();
			}
		};
		if(progresDialog==null){
			progresDialog = new CustomProgressDialog(this);
		}
		progresDialog.setMessage("正在获取用户信息！");
		progresDialog.show();
		ApiClient.userProfile(uid, httpAsync);
	}

	private void showProfile() {
		if(userProfile!=null){
			String email = userProfile.getEmail();
			String nickname = userProfile.getNickname();
			Integer age = userProfile.getAge();
			Integer gender = userProfile.getGender();
			et_email.setText(email);
			et_nickname.setText(nickname);
			if(age!=null){
				et_age.setText(age+"");
			}
			if(gender!=null){
				if(gender==0||gender==1){
					((RadioButton)rg_gender.getChildAt(gender)).setChecked(true);
				}
			}
		}
	}

	private void setEditable(boolean b) {
		if(b){
			et_nickname.setInputType(InputType.TYPE_CLASS_TEXT);
			et_age.setInputType(InputType.TYPE_CLASS_NUMBER);
		}else{
			et_nickname.setInputType(InputType.TYPE_NULL);
			et_age.setInputType(InputType.TYPE_NULL);
		}
	}

	@Override
	public void onClick(View v) {
		if(v==btn){
			if(status==SHOW){
				setEditable(true);
				btn.setText("保存");
				status = MODIFY;
			}else if(status==MODIFY){
				updateUserProfile();
			}
		}
	}
	private void updateUserProfile() {
		UserLogin loginInfo = AppConfig.getAppConfig().getLoginInfo(this);
		String uid = loginInfo.getUid();
		if(TextUtils.isEmpty(uid)){
			ToastShow.shortT("获取用户id失败，请重新登录！");
			return;
		}
		String nickname = et_nickname.getText().toString();
		if(TextUtils.isEmpty(nickname)){
			ToastShow.shortT("请填写姓名！");
			return;
		}
		Integer gender = rg_gender.getCheckedRadioButtonId();
		if(gender==-1){
			ToastShow.shortT("请选择性别！");
			return;
		}else if(gender==R.id.profile_rb_female){
			gender=0;
		}else if(gender==R.id.profile_rb_male){
			gender = 1;
		}
		String ageStr = et_age.getText().toString();
		if(TextUtils.isEmpty(ageStr)){
			ToastShow.shortT("请填写年龄！");
			return;
		}
		int age = Integer.parseInt(ageStr);
		HttpAsync httpAsync = new HttpAsync() {
			@Override
			protected void onSuccess(String json) {
				if(progresDialog!=null&&progresDialog.isShowing()) progresDialog.dismiss();
				SimpleResponse<UserProfile> response = ResponseUtils.proflileResponse(json);
				if(response.isSuccess()){
					ToastShow.shortT("修改成功！");
					setEditable(false);
					btn.setText("修改");
					status=SHOW;
				}else{
					String message = response.getMessage();
					if(message==null){
						message="";
					}
					ToastShow.shortT("修改失败！"+message);
				}
			}
			
			@Override
			public void onError(HttpException exception) {
				if(progresDialog!=null&&progresDialog.isShowing()) progresDialog.dismiss();
			}
		};
		if(progresDialog==null){
			progresDialog = new CustomProgressDialog(this);
		}
		progresDialog.setMessage("正在修改用户信息！");
		progresDialog.show();
		ApiClient.userProfileUpdate(uid, nickname, gender, age, httpAsync);
	}

	@Override
	protected void onDestroy() {
		if(progresDialog!=null&&progresDialog.isShowing()) progresDialog.dismiss();
		super.onDestroy();
	}
}
