package cn.edu.hhu.reg.activity;

import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.entity.Doctor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DoctorPageActicity extends BaseActivity {
	
	Doctor doctor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_page);
		UiHelper.goBack(this);
		
		doctor = (Doctor) getIntent().getSerializableExtra("doctor");
		if(doctor==null){
			return;
		}
		
		((TextView)findViewById(R.id.item_doctor_tv_name)).setText(doctor.getNickname());
		((TextView)findViewById(R.id.item_doctor_tv_age)).setText(doctor.getAge()+"");
		((TextView)findViewById(R.id.item_doctor_tv_gender)).setText(doctor.getGender()==1?"男":"女");
		((TextView)findViewById(R.id.item_doctor_tv_department)).setText(doctor.getDepartmentId());
		((TextView)findViewById(R.id.item_doctor_tv_description)).setText(doctor.getIntroduction());
		((Button)findViewById(R.id.item_doctor_btn_registration)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UiHelper.registration(DoctorPageActicity.this, doctor);
			}
		});
		
		
	}
}
