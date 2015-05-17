package cn.edu.hhu.reg.activity;

import java.util.ArrayList;

import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.adapter.DepartmentListViewAdapter;
import cn.edu.hhu.reg.api.ApiClient;
import cn.edu.hhu.reg.api.ResponseUtils;
import cn.edu.hhu.reg.api.SimpleResponse;
import cn.edu.hhu.reg.common.CustomProgressDialog;
import cn.edu.hhu.reg.common.ToastShow;
import cn.edu.hhu.reg.common.http.HttpException;
import cn.edu.hhu.reg.common.http.async.HttpAsync;
import cn.edu.hhu.reg.entity.Department;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class DepartmentListActivity extends BaseActivity {
	
	ListView lv;
	CustomProgressDialog progressDialog ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		UiHelper.goBack(this);
		((TextView)findViewById(R.id.listview_tv_title)).setText("科室列表");
		lv = (ListView) findViewById(R.id.listview_lv);
		getDepartmentList();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DepartmentListViewAdapter adapter  = (DepartmentListViewAdapter)parent.getAdapter();
				if(adapter!=null){
					Department department = adapter.getItem(position);
					UiHelper.doctorList(DepartmentListActivity.this, department.getId());
				}
			}
		});
	}

	private void getDepartmentList() {
		HttpAsync httpAsync = new HttpAsync() {
			@Override
			protected void onSuccess(String json) {
				progressDialog.dismiss();
				try {
					SimpleResponse<ArrayList<Department>> departmentListResponse = ResponseUtils.departmentListResponse(json);
					if(departmentListResponse.isSuccess()){
						ArrayList<Department> list = departmentListResponse.getData();
						DepartmentListViewAdapter adapter = new DepartmentListViewAdapter(list, DepartmentListActivity.this);
						lv.setAdapter(adapter);
					}else{
						String msg = departmentListResponse.getMessage();
						if(msg==null){
							msg="";
						}
						ToastShow.shortT("获取列表失败！"+msg);
					}
				} catch (Exception e) {
					ToastShow.shortT(e.getMessage());
				}
			}
			
			@Override
			public void onError(HttpException exception) {
				progressDialog.dismiss();
				ToastShow.shortT("获取列表失败！");
			}
		};
		if(progressDialog==null){
			progressDialog = new CustomProgressDialog(this,"正在获取科室列表！");
		}
		progressDialog.show();
		ApiClient.departmentList(httpAsync);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		progressDialog.dismiss();
	}
}
