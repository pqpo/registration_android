package cn.edu.hhu.reg.activity;

import java.util.Calendar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import cn.edu.hhu.reg.AppConfig;
import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.api.ApiClient;
import cn.edu.hhu.reg.api.ResponseUtils;
import cn.edu.hhu.reg.api.SimpleResponse;
import cn.edu.hhu.reg.common.ToastShow;
import cn.edu.hhu.reg.common.http.HttpException;
import cn.edu.hhu.reg.common.http.async.HttpAsync;
import cn.edu.hhu.reg.entity.Doctor;
import cn.edu.hhu.reg.entity.Registration;

public class RegistrationActivity extends BaseActivity {
	
	Doctor doctor;
	EditText et_date;
	EditText et_name;
	EditText et_age;
	EditText et_description;
	RadioGroup rg_gender;
	Button btn_commit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		UiHelper.goBack(this);
		doctor = (Doctor) getIntent().getSerializableExtra("doctor");
		if(doctor==null){
			return;
		}
		et_date = (EditText) findViewById(R.id.registration_et_date);
		et_name = (EditText) findViewById(R.id.registration_et_nickname);
		et_age = (EditText) findViewById(R.id.registration_et_age);
		et_description = (EditText) findViewById(R.id.registration_et_description);
		rg_gender = (RadioGroup) findViewById(R.id.registration_rg_gender);
		btn_commit = (Button) findViewById(R.id.registration_btn_commit);
		
		et_date.setKeyListener(null);
		et_date.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				datePicker();
			}
		});
		
		btn_commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				registration();
			}
		});
	}

	private void registration() {
		String uid = AppConfig.getAppConfig().getLoginInfo(this).getUid();
		String doctorId = doctor.getId();
		String date = et_date.getText().toString();
		String name = et_name.getText().toString();
		String ageStr = et_age.getText().toString();
		int gender = rg_gender.getCheckedRadioButtonId();
		String description = et_description.getText().toString();
		if(TextUtils.isEmpty(uid)){
			ToastShow.shortT("请重新登录！");
			return;
		}
		if(TextUtils.isEmpty(date)){
			ToastShow.shortT("请选择预约时间！");
			return;
		}
		if(TextUtils.isEmpty(name)){
			ToastShow.shortT("请填写姓名！");
			return;
		}
		if(TextUtils.isEmpty(ageStr)){
			ToastShow.shortT("请填写年龄！");
			return;
		}
		int age = Integer.parseInt(ageStr);
		
		if(TextUtils.isEmpty(description)){
			ToastShow.shortT("请填写描述！");
			return;
		}
		if(gender==-1){
			ToastShow.shortT("请选择性别！");
			return;
		}
		if(gender==R.id.registration_rb_male){
			gender=1;
		}else{
			gender=0;
		}
		
		HttpAsync httpAsync = new HttpAsync() {
			
			@Override
			protected void onSuccess(String json) {
				SimpleResponse<Registration> registrationResponse = ResponseUtils.registrationResponse(json);
				if(registrationResponse.isSuccess()){
					ToastShow.shortT("预约成功！");
					RegistrationActivity.this.finish();
				}else{
					String message = registrationResponse.getMessage();
					if(message==null){
						message="";
					}
					ToastShow.longT("预约失败！"+message);
				}
			}
			@Override
			public void onError(HttpException exception) {
				ToastShow.shortT("预约失败！"+exception.getMessage());
			}
		};
		showConfirmDialog(uid, doctorId, date, name, age, gender, description,doctor.getNickname(),httpAsync);
	}

	private void showConfirmDialog(final String uid, final String doctorId, final String date,
			final String name,final int age,final int gender,final String description,
			final String nickname,final HttpAsync httpAsync) {
		new AlertDialog.Builder(this).setTitle("确认")
		.setMessage("确认在 "+date+" 预约 "+nickname+"医生?")
		.setPositiveButton("确认", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ApiClient.registration(uid, doctorId, date, name, age, gender, description, httpAsync);
			}})
		.setNegativeButton("取消", null).create().show();
	}

	private Calendar calendar;
	private DatePickerDialog dpd;
	
	private void datePicker() {
		if(calendar==null){
			calendar = Calendar.getInstance();
			dpd = new DatePickerDialog(this, new OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					et_date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			dpd.setTitle("请选择预约日期");
		}
		dpd.show();
	}
}
