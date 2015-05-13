package cn.edu.hhu.reg.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.adapter.DoctorListViewAdapter;
import cn.edu.hhu.reg.api.ApiClient;
import cn.edu.hhu.reg.api.ResponseUtils;
import cn.edu.hhu.reg.api.SimpleResponse;
import cn.edu.hhu.reg.common.CustomProgressDialog;
import cn.edu.hhu.reg.common.ToastShow;
import cn.edu.hhu.reg.common.http.HttpException;
import cn.edu.hhu.reg.common.http.async.HttpAsync;
import cn.edu.hhu.reg.entity.Department;
import cn.edu.hhu.reg.entity.Doctor;

public class DoctorListActivity extends BaseActivity {

	ListView lv;
	CustomProgressDialog progressDialog ;
	Map<String, String> departmentMap;
	String departmentId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		UiHelper.goBack(this);
		((TextView)findViewById(R.id.listview_tv_title)).setText("医生列表");
		lv = (ListView) findViewById(R.id.listview_lv);
		departmentId = getIntent().getStringExtra("department");
		getDepartmentList();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					DoctorListViewAdapter adapter = (DoctorListViewAdapter) parent.getAdapter();
					Doctor doctor = adapter.getItem(position);
					UiHelper.doctorPage(DoctorListActivity.this, doctor);
			}
		});
	}
	
	private void getDepartmentList() {
		HttpAsync httpAsync = new HttpAsync() {
			@Override
			protected void onSuccess(String json) {
				SimpleResponse<ArrayList<Department>> departmentListResponse = ResponseUtils.departmentListResponse(json);
				if(departmentListResponse.isSuccess()){
					ArrayList<Department> list = departmentListResponse.getData();
					departmentMap = new HashMap<String, String>();
					for(Department d:list){
						departmentMap.put(d.getId(), d.getName());
					}
					getDoctorList(departmentId);
				}else{
					String msg = departmentListResponse.getMessage();
					if(msg==null){
						msg="";
					}
					ToastShow.shortT("获取列表失败！"+msg);
				}
			}
			@Override
			public void onError(HttpException exception) {
				progressDialog.dismiss();
				ToastShow.shortT("获取列表失败！");
			}
		};
		if(progressDialog==null){
			progressDialog = new CustomProgressDialog(this,"正在获取列表！");
		}
		progressDialog.show();
		ApiClient.departmentList(httpAsync);
	}
	

	private void getDoctorList(String departmentId) {
		
		HttpAsync httpAsync = new HttpAsync() {
			@Override
			protected void onSuccess(String json) {
				progressDialog.dismiss();
				SimpleResponse<ArrayList<Doctor>> response = ResponseUtils.doctorListResponse(json);
				if(response.isSuccess()){
					ArrayList<Doctor> list = response.getData();
					if(list!=null&&!list.isEmpty()){
						for(Doctor d:list){
							d.setDepartmentId(departmentMap.get(d.getDepartmentId()));
						}
						DoctorListViewAdapter  adapter = new DoctorListViewAdapter(list, DoctorListActivity.this);
						lv.setAdapter(adapter);
					}
				}else{
					String msg = response.getMessage();
					if(msg==null){
						msg="";
					}
					ToastShow.shortT("获取列表失败！"+msg);
				}
			}
			
			@Override
			public void onError(HttpException exception) {
				progressDialog.dismiss();
				ToastShow.shortT("获取列表失败！"+exception.getMessage());
			}
		};
		if(departmentId==null){
			ApiClient.doctorList(httpAsync);
		}else{
			ApiClient.doctorListDepartment(departmentId, httpAsync);
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		progressDialog.dismiss();
	}
}
