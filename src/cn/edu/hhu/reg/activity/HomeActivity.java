package cn.edu.hhu.reg.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cn.edu.hhu.reg.AppManager;
import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.adapter.HospitalHomeGridViewAdapter;
import cn.edu.hhu.reg.common.ToastShow;

public class HomeActivity extends BaseActivity {
	
	private long backPressedTime;
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
					UiHelper.departmentList(HomeActivity.this);
					break;
				case 1://找医生
					UiHelper.doctorList(HomeActivity.this, null);
					break;
				case 2://公告资讯
					UiHelper.web(HomeActivity.this, "公告资讯", "http://www.hhu.edu.cn");
					break;
				case 3://医疗百科
					UiHelper.web(HomeActivity.this, "医疗百科", "http://www.hhu.edu.cn");
					break;
				case 4://我的预约
					UiHelper.registrationMine(HomeActivity.this);
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
	
	@Override
	public void onBackPressed(){
		if((System.currentTimeMillis()-backPressedTime)<2000){ //按2次返回键退出
			AppManager.getAppManager().appExit(this);
		}else{
			backPressedTime = System.currentTimeMillis();
			ToastShow.shortT("再按一次退出");
		}
	}
}
