package cn.edu.hhu.reg.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.entity.Doctor;

public class DoctorListViewAdapter extends BaseAdapter {
	ArrayList<Doctor> list;
	Context context;
	public DoctorListViewAdapter(ArrayList<Doctor> list,Context context){
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Doctor getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_doctorlist_listview, parent, false);
			vh = new ViewHolder();
			vh.tv_name = (TextView) convertView.findViewById(R.id.item_doctorlist_tv_name);
			vh.tv_department = (TextView) convertView.findViewById(R.id.item_doctorlist_tv_department);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		vh.tv_name.setText(list.get(position).getNickname());
		vh.tv_department.setText(list.get(position).getDepartmentId());
		return convertView;
	}

	private static class ViewHolder{
		public TextView tv_name;
		public TextView tv_department;
	}
}
