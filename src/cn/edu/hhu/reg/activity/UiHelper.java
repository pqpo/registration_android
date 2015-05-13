package cn.edu.hhu.reg.activity;

import cn.edu.hhu.reg.AppContext;
import cn.edu.hhu.reg.R;
import cn.edu.hhu.reg.common.ToastShow;
import cn.edu.hhu.reg.entity.Doctor;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class UiHelper {
	
	public static void goBack(final Activity ac){
		ac.findViewById(R.id.iv_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.finish();
			}
		});
	}

	/**
	 * 主页面
	 * @param ac
	 */
	public static void home(Activity ac){
		Intent intent = new Intent();
		intent.setClass(ac, HomeActivity.class);
		ac.startActivity(intent);
	}
	
	/**
	 * 用户信息
	 * @param ac
	 */
	public static void profile(Activity ac){
		if(!AppContext.isLoginSuccess){
			login(ac);
			return;
		}
		Intent intent = new Intent();
		intent.setClass(ac, ProfileActivity.class);
		ac.startActivity(intent);
	}
	
	/**
	 * 登录
	 * @param ac
	 */
	public static void login(Activity ac){
		Intent intent = new Intent();
		intent.setClass(ac, LoginActicity.class);
		ac.startActivity(intent);
	}
	
	/**
	 * 科室列表
	 * @param ac
	 */
	public static void departmentList(Activity ac){
		Intent intent = new Intent();
		intent.setClass(ac, DepartmentListActivity.class);
		ac.startActivity(intent);
	}
	
	/**
	 * 医生列表
	 * @param ac
	 * @param department
	 */
	public static void doctorList(Activity ac,String department){
		Intent intent = new Intent();
		intent.putExtra("department", department);
		intent.setClass(ac, DoctorListActivity.class);
		ac.startActivity(intent);
	}
	
	/**
	 * 医生页面
	 * @param ac
	 * @param doctor
	 */
	public static void doctorPage(Activity ac,Doctor doctor){
		Intent intent = new Intent();
		intent.putExtra("doctor", doctor);
		intent.setClass(ac, DoctorPageActicity.class);
		ac.startActivity(intent);
	}
	
	/**
	 * 预约页面
	 * @param ac
	 * @param doctor
	 */
	public static void registration(Activity ac,Doctor doctor){
		if(!AppContext.isLoginSuccess){
			ToastShow.shortT("请先登录！");
			return;
		}
		Intent intent = new Intent();
		intent.putExtra("doctor", doctor);
		intent.setClass(ac, RegistrationActivity.class);
		ac.startActivity(intent);
	}
	
	/**
	 * 注册
	 * @param ac
	 */
	public static void register(Activity ac){
		Intent intent = new Intent();
		intent.setClass(ac, RegisterActivity.class);
		ac.startActivity(intent);
	}
	
	/**
	 * 修改密码
	 * @param ac
	 */
	public static void resetPassword(Activity ac){
		if(!AppContext.isLoginSuccess){
			ToastShow.shortT("请先登录！");
			return;
		}
		Intent intent = new Intent();
		intent.setClass(ac, ResetPasswordActivity.class);
		ac.startActivity(intent);
	}
	
	/**
	 * 网页浏览
	 * @param ac
	 * @param title
	 * @param url
	 */
	public static void web(Activity ac,String title,String url){
		Intent intent = new Intent();
		intent.putExtra(WebViewActivity.FLAG_TITLE, title);
		intent.putExtra(WebViewActivity.FLAG_URL, url);
		intent.setClass(ac, WebViewActivity.class);
		ac.startActivity(intent);
	}
}
