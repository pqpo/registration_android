package cn.edu.hhu.reg.activity;

import java.util.Calendar;

import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.api.ApiClient;
import cn.edu.hhu.reg.api.ResponseUtils;
import cn.edu.hhu.reg.api.SimpleResponse;
import cn.edu.hhu.reg.common.CustomProgressDialog;
import cn.edu.hhu.reg.common.ToastShow;
import cn.edu.hhu.reg.common.http.HttpException;
import cn.edu.hhu.reg.common.http.async.HttpAsync;
import cn.edu.hhu.reg.entity.Registration;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RegistrationDetailActivity extends BaseActivity {

	Registration registration;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registration = (Registration) getIntent().getSerializableExtra("registration");
		if(registration==null){
			return;
		}
		setContentView(R.layout.activity_registration_detail);
		UiHelper.goBack(this);
		((TextView)findViewById(R.id.registration_detail_tv_doctor_name)).setText(registration.getDoctorName());
		((TextView)findViewById(R.id.registration_detail_tv_doctor_department)).setText(registration.getDepartmentName());
		((TextView)findViewById(R.id.registration_detail_tv_date)).setText(registration.getDate());
		((TextView)findViewById(R.id.registration_detail_tv_name)).setText(registration.getName());
		((TextView)findViewById(R.id.registration_detail_tv_age)).setText(registration.getAge()+"");
		((TextView)findViewById(R.id.registration_detail_tv_gender)).setText(registration.getGender()==1?"男":"女");
		((TextView)findViewById(R.id.registration_detail_tv_description)).setText(registration.getDescription());
		
		long time = Long.parseLong(registration.getCreateTime());
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		StringBuffer sb = new StringBuffer();
		sb.append(c.get(Calendar.YEAR))
		.append("-")
		.append(c.get(Calendar.MONTH)+1)
		.append("-")
		.append(c.get(Calendar.DAY_OF_MONTH))
		.append(" ")
		.append(c.get(Calendar.HOUR_OF_DAY))
		.append(":")
		.append(c.get(Calendar.MINUTE))
		.append(":")
		.append(c.get(Calendar.SECOND));
		((TextView)findViewById(R.id.registration_detail_tv_create_time)).setText(sb.toString());
		
		
		((Button)findViewById(R.id.registration_detail_btn_cancel)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showConfirmDialog();
			}
		});
	}

	private AlertDialog alertDialog;
	private void showConfirmDialog() {
		if(alertDialog==null){
			alertDialog = new AlertDialog.Builder(this)
			.setTitle("提示")
			.setMessage("确定要取消本次预约吗？取消后不可恢复！")
			.setPositiveButton("确定", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					cancelRegistration();
				}})
			.setNegativeButton("取消", null)
			.create();
		}
		alertDialog.show();
	}
	
	CustomProgressDialog progressDialog;
	
	private void cancelRegistration() {
		HttpAsync httpAsync = new HttpAsync() {
			@Override
			protected void onSuccess(String json) {
				progressDialog.dismiss();
				SimpleResponse<Object> response = ResponseUtils.SimpleResponse(json);
				if(response.isSuccess()){
					ToastShow.longT("已取消！");
					finish();
				}else{
					String msg = response.getMessage();
					if(msg==null){
						msg="";
					}
					ToastShow.longT("取消失败！"+msg);
				}
			}
			@Override
			public void onError(HttpException exception) {
				progressDialog.dismiss();
				ToastShow.longT("取消失败！"+exception.getMessage());
			}
		};
		if(progressDialog==null)progressDialog = new CustomProgressDialog(this,"正在取消！");
		progressDialog.show();
		ApiClient.unRegistration(registration.getId()+"", httpAsync);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(progressDialog!=null)progressDialog.dismiss();
	}
}
