package cn.edu.hhu.reg.adapter;

import java.util.List;

import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.entity.Registration;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RegistrationListAdapter extends BaseAdapter {

	List<Registration> list;
	Context context;
	
	public RegistrationListAdapter(List<Registration> list,Context context){
		this.list = list;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Registration getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler vh = null;
		Registration reg = list.get(position);
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_my_registration_listview, parent, false);
			vh = new ViewHodler();
			vh.tv_date = (TextView) convertView.findViewById(R.id.registration_listview_tv_date);
			vh.tv_department = (TextView) convertView.findViewById(R.id.registration_listview_tv_department);
			vh.tv_doctor = (TextView) convertView.findViewById(R.id.registration_listview_tv_doctor);
			vh.tv_status = (TextView) convertView.findViewById(R.id.registration_listview_tv_status);
			convertView.setTag(vh);
		}else{
			vh = (ViewHodler) convertView.getTag();
		}
		vh.tv_date.setText(reg.getDate());
		vh.tv_department.setText(reg.getDepartmentName());
		vh.tv_doctor.setText(reg.getDoctorName());
		/**
		 * 状态 0:有效  1:已完成 2:用户已取消 3：已过期
		 */
		int status = reg.getStatus();
		String statusStr = "有效";
		if(status==1){
			statusStr = "已完成";
		}else if(status==2){
			statusStr = "已取消";
		}else if(status==3){
			statusStr = "已过期";
		}
		vh.tv_status.setText(statusStr);
		return convertView;
	}

	private static class ViewHodler{
		public TextView tv_date;
		public TextView tv_department;
		public TextView tv_doctor;
		public TextView tv_status;
	}
}
