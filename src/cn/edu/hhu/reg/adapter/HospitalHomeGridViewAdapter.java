package cn.edu.hhu.reg.adapter;

import cn.edu.hhu.reg.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HospitalHomeGridViewAdapter extends BaseAdapter {

	int[] imgs;
	String[] texts;
	Context ctx;
	public HospitalHomeGridViewAdapter(int[] images,String[] texts,Context context){
		imgs = images;
		this.texts = texts ;
		ctx = context;
	}
	
	@Override
	public int getCount() {
		return imgs.length;
	}

	@Override
	public Object getItem(int position) {
		return texts[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder vh = null;
		if(view==null){
			view = LayoutInflater.from(ctx).inflate(R.layout.item_hospital_gridview, parent,false);
			vh = new ViewHolder();
			vh.tv = (TextView) view.findViewById(R.id.item_hospital_gv_tv);
			vh.iv = (ImageView) view.findViewById(R.id.item_hospital_gv_iv);
			view.setTag(vh);
		}else{
			vh = (ViewHolder) view.getTag();
		}
		vh.tv.setText(texts[position]);
		vh.iv.setImageResource(imgs[position]);
		return view;
	}

	private static class ViewHolder{
		ImageView iv;
		TextView tv;
	}
}
