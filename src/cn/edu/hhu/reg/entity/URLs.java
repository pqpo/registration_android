package cn.edu.hhu.reg.entity;

public class URLs {
	
	private static final String PATH = "http://192.168.1.100:8080/Registration";
	     
	/**
	 * 用户相关
	 */
	
	public static final String USER_LOGIN_POST = PATH+"/user/login";
	public static final String USER_REGISTER_POST = PATH+"/user/register";
	public static final String USER_PROFILE_GET = PATH+"/user/profile/";
	public static final String USER_PROFILE_UPDATE_POST = PATH+"/user/profile/update";
	public static final String USER_PASSWORD_RESET_POST = PATH+"/user/password/reset";
	
	/**
	 * 预约相关接口
	 */
	public static final String REGISTRATION_USER_LIST_GET = PATH+"/registration/user/list/";
	public static final String REGISTRATION_USER_REGISTER_POST = PATH+"/registration/user/register";
	public static final String REGISTRATION_USER_UNREGISTER_POST = PATH+"/registration/user/unregister/";
	
	/**
	 * 医生相关
	 */
	public static final String DOCTOR_LIST_GET = PATH+"/doctor/list";
	public static final String DOCTOR_LIST_DEPARTMETN_GET = PATH+"/doctor/list/department/";
	
	/**
	 * 科室相关
	 */
	public static final String DEPARTMENT_LIST_GET = PATH+"/department/list";
}
