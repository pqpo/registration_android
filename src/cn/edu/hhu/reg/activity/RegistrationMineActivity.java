package cn.edu.hhu.reg.activity;

import java.util.ArrayList;
import cn.edu.hhu.reg.AppConfig;
import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.adapter.RegistrationListAdapter;
import cn.edu.hhu.reg.api.ApiClient;
import cn.edu.hhu.reg.api.ResponseUtils;
import cn.edu.hhu.reg.api.SimpleResponse;
import cn.edu.hhu.reg.common.CustomProgressDialog;
import cn.edu.hhu.reg.common.ToastShow;
import cn.edu.hhu.reg.common.http.HttpException;
import cn.edu.hhu.reg.common.http.async.HttpAsync;
import cn.edu.hhu.reg.entity.Registration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class RegistrationMineActivity extends BaseActivity {

	ListView lv;
	CustomProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		((TextView)findViewById(R.id.listview_tv_title)).setText("我的预约");
		UiHelper.goBack(this);
		lv = (ListView) findViewById(R.id.listview_lv);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				RegistrationListAdapter adapter = (RegistrationListAdapter) parent.getAdapter();
				Registration registration = adapter.getItem(position);
				UiHelper.registrationDetail(RegistrationMineActivity.this, registration);
			}
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getMyRegistrationList();
	}

	private void getMyRegistrationList() {
		String uid = AppConfig.getAppConfig().getLoginInfo(this).getUid();
		if(TextUtils.isEmpty(uid)){
			ToastShow.shortT("请重新登录！");
			finish();
			return;
		}
		HttpAsync httpAsync = new HttpAsync() {

			@Override
			protected void onSuccess(String json) {
				progressDialog.dismiss();
				SimpleResponse<ArrayList<Registration>> response = ResponseUtils.registrationListResponse(json);
				if(response.isSuccess()){
					ArrayList<Registration> list = response.getData();
					lv.setAdapter(new RegistrationListAdapter(list, RegistrationMineActivity.this));
				}else{
					String msg = response.getMessage();
					if(msg==null) msg="";
					ToastShow.shortT("获取列表失败！"+msg);
				}
			}

			@Override
			public void onError(HttpException exception) {
				progressDialog.dismiss();
				ToastShow.shortT("获取列表失败！"+exception.getMessage());
			}
		};
		if(progressDialog==null)progressDialog = new CustomProgressDialog(this, "正在获取列表！");
		progressDialog.show();
		ApiClient.myRegistrationList(uid, httpAsync);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(progressDialog!=null){
			progressDialog.dismiss();
		}
	}
}
