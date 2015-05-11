package cn.edu.hhu.reg.api;

import java.util.HashMap;
import android.util.Log;
import com.google.gson.Gson;
import cn.edu.hhu.reg.AppContext;
import cn.edu.hhu.reg.common.http.HttpException;
import cn.edu.hhu.reg.common.http.HttpRequest;
import cn.edu.hhu.reg.common.http.async.HttpAsync;
import cn.edu.hhu.reg.entity.URLs;

public class ApiClient {
	private static final String TAG = ApiClient.class.getSimpleName();
	private final static int TIMEOUT_READ = 15000; //读取超时
	private final static int TIMEOUT_CONNECTION = 15000; //服务器连接超时
	private final static String CHARSET = AppContext.UTF_8;
	private final static Gson gson = new Gson();
	
	private static HttpRequest getRequest(String url) throws HttpException{
		HttpRequest request = new HttpRequest(HttpRequest.METHOD_GET,url);
		request.setReadTimeout(TIMEOUT_READ);
		request.setConnectTimeout(TIMEOUT_CONNECTION);
		request.setCharset(CHARSET);
		return request;
	}

	private static HttpRequest postRequest(String url)throws HttpException{
		HttpRequest request = new HttpRequest(HttpRequest.METHOD_POST,url);
		request.setReadTimeout(TIMEOUT_READ);
		request.setConnectTimeout(TIMEOUT_CONNECTION);
		request.setCharset(CHARSET);
		return request;
	}
	
	/**
	 * 登录
	 * @param email
	 * @param password
	 * @param httpAsync
	 */
	public static void login(String email,String password,HttpAsync httpAsync){
		String url = URLs.USER_LOGIN_POST;
		if(AppContext.isDebug)Log.d(TAG, url);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("password",password);
		String json = gson.toJson(params);
		if(AppContext.isDebug)Log.d(TAG, json);
		try {
			HttpRequest request = postRequest(url);
			request.putJson(json);
			httpAsync.execute(request);
		} catch (HttpException e) {
			httpAsync.onError(e);
		}
	}
	
	/**
	 * 注册
	 * @param email
	 * @param password
	 * @param httpAsync
	 */
	public static void register(String email,String password,HttpAsync httpAsync){
		String url = URLs.USER_REGISTER_POST;
		if(AppContext.isDebug)Log.d(TAG, url);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("password",password);
		String json = gson.toJson(params);
		try {
			HttpRequest request = postRequest(url);
			request.putJson(json);
			httpAsync.execute(request);
		} catch (HttpException e) {
			httpAsync.onError(e);
		}
	}
	
	/**
	 * 获取用户信息
	 * @param uid
	 * @param httpAsync
	 */
	public static void userProfile(String uid,HttpAsync httpAsync){
		String url = URLs.USER_PROFILE_GET+uid;
		if(AppContext.isDebug)Log.d(TAG, url);
		try {
			HttpRequest request = getRequest(url);
			httpAsync.execute(request);
		} catch (HttpException e) {
			httpAsync.onError(e);
		}
	}
	
	/**
	 * 更新用户信息
	 * @param uid
	 * @param email
	 * @param nickname
	 * @param gender
	 * @param age
	 * @param httpAsync
	 */
	public static void userProfileUpdate(String uid,String nickname,
			int gender,int age,HttpAsync httpAsync){
		String url = URLs.USER_PROFILE_UPDATE_POST;
		if(AppContext.isDebug)Log.d(TAG, url);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", uid);
		params.put("nickname",nickname);
		params.put("gender",gender+"");
		params.put("age",age+"");
		String json = gson.toJson(params);
		if(AppContext.isDebug)Log.d(TAG, json);
		try {
			HttpRequest request = postRequest(url);
			request.putJson(json);
			httpAsync.execute(request);
		} catch (HttpException e) {
			httpAsync.onError(e);
		}
	}
	
	/**
	 * 修改用户密码
	 * @param uid
	 * @param password
	 * @param httpAsync
	 */
	public static void userPasswordReset(String uid,String password,HttpAsync httpAsync){
		String url = URLs.USER_PASSWORD_RESET_POST;
		if(AppContext.isDebug)Log.d(TAG,url);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", uid);
		params.put("password",password);
		String json = gson.toJson(params);
		try {
			HttpRequest request = postRequest(url);
			request.putJson(json);
			httpAsync.execute(request);
		} catch (HttpException e) {
			httpAsync.onError(e);
		}
	}
	
	/**
	 * 我的预约
	 * @param uid
	 * @param httpAsync
	 */
	public static void myRegistrationList(String uid,HttpAsync httpAsync){
		String url = URLs.REGISTRATION_USER_LIST_GET+uid;
		if(AppContext.isDebug)Log.d(TAG, url);
		try {
			HttpRequest request = getRequest(url);
			httpAsync.execute(request);
		} catch (HttpException e) {
			httpAsync.onError(e);
		}
	}
	
	/**
	 * 预约
	 * @param userId
	 * @param doctorId
	 * @param date
	 * @param name
	 * @param age
	 * @param gender
	 * @param description
	 * @param httpAsync
	 */
	public static void registration(String userId,String doctorId,String date,
			String name,int age,int gender,String description,HttpAsync httpAsync){
		String url = URLs.REGISTRATION_USER_REGISTER_POST;
		if(AppContext.isDebug)Log.d(TAG, url);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("doctorId",doctorId);
		params.put("date",date);
		params.put("name",name);
		params.put("age",age+"");
		params.put("gender",gender+"");
		params.put("description",description);
		String json = gson.toJson(params);
		try {
			HttpRequest request = postRequest(url);
			request.putJson(json);
			httpAsync.execute(request);
		} catch (HttpException e) {
			httpAsync.onError(e);
		}
	}
	
	/**
	 * 取消预约
	 * @param rid
	 * @param httpAsync
	 */
	public static void unRegistration(String rid,HttpAsync httpAsync){
		String url = URLs.REGISTRATION_USER_UNREGISTER_POST+rid;
		if(AppContext.isDebug)Log.d(TAG, url);
		try {
			HttpRequest request = getRequest(url);
			httpAsync.execute(request);
		} catch (HttpException e) {
			httpAsync.onError(e);
		}
	}
	
	/**
	 * 医生列表
	 * @param httpAsync
	 */
	public static void doctorList(HttpAsync httpAsync){
		String url = URLs.DOCTOR_LIST_GET;
		if(AppContext.isDebug)Log.d(TAG, url);
		try {
			HttpRequest request = getRequest(url);
			httpAsync.execute(request);
		} catch (HttpException e) {
			httpAsync.onError(e);
		}
	}
	
	/**
	 * 科室医生列表
	 * @param did
	 * @param httpAsync
	 */
	public static void doctorListDepartment(String did,HttpAsync httpAsync){
		String url = URLs.DOCTOR_LIST_DEPARTMETN_GET+did;
		if(AppContext.isDebug)Log.d(TAG, url);
		try {
			HttpRequest request = getRequest(url);
			httpAsync.execute(request);
		} catch (HttpException e) {
			httpAsync.onError(e);
		}
	}
	
	/**
	 * 科室列表
	 * @param httpAsync
	 */
	public static void departmentList(HttpAsync httpAsync){
		String url = URLs.DEPARTMENT_LIST_GET;
		if(AppContext.isDebug)Log.d(TAG, url);
		try {
			HttpRequest request = getRequest(url);
			httpAsync.execute(request);
		} catch (HttpException e) {
			httpAsync.onError(e);
		}
	}
}
