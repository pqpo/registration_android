package cn.edu.hhu.reg.adapter;

import java.util.ArrayList;

import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.entity.Department;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DepartmentListViewAdapter extends BaseAdapter {
	ArrayList<Department> list;
	Context context;
	public DepartmentListViewAdapter(ArrayList<Department> list,Context context){
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Department getItem(int position) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_department, parent, false);
			vh = new ViewHolder();
			vh.tv_name = (TextView) convertView.findViewById(R.id.department_tv_name);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		vh.tv_name.setText(list.get(position).getName());
		return convertView;
	}

	private static class ViewHolder{
		public TextView tv_name;
	}
}
