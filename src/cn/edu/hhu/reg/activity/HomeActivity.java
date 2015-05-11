package cn.edu.hhu.reg.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.adapter.HospitalHomeGridViewAdapter;

public class HomeActivity extends BaseActivity {
	
	GridView gv;
	int gridViewImage[] = { 
			R.drawable.hospital_department,
			R.drawable.hospital_doctor,
			R.drawable.hospital_introduce,
			R.drawable.hospital_wiki,
			R.drawable.hospital_myreg,
			R.drawable.hospital_account 
			};

	String gridViewText[] = { 
			"找科室",
			"找医生",
			"公告资讯",
			"医疗百科",
			"我的预约",
			"个人中心" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		gv = (GridView) findViewById(R.id.home_gv);
		gv.setAdapter(new HospitalHomeGridViewAdapter(gridViewImage, gridViewText, this));
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0://找科室
					break;
				case 1://找医生
					break;
				case 2://公告资讯
					break;
				case 3://医疗百科
					break;
				case 4://我的预约
					break;
				case 5://个人中心
					UiHelper.profile(HomeActivity.this);
					break;
				default:
					break;
				}
			}
		});
	}
}
