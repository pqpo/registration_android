package cn.edu.hhu.reg.api;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import cn.edu.hhu.reg.entity.Department;
import cn.edu.hhu.reg.entity.Doctor;
import cn.edu.hhu.reg.entity.Registration;
import cn.edu.hhu.reg.entity.UserLogin;
import cn.edu.hhu.reg.entity.UserProfile;

public class  ResponseUtils{
	
	private static JsonParser jsonParser = new JsonParser();
	private static Gson gson= new Gson();
	
	public static  SimpleResponse<Object> SimpleResponse(String response) throws Exception{
		SimpleResponse<Object> simpleResponse = null;
		try {
			JsonElement jsonElement = jsonParser.parse(response);
			Type type = new TypeToken<SimpleResponse<Object>>(){}.getType();
			simpleResponse = gson.fromJson(jsonElement, type);
		} catch (Exception e) {
			throw new Exception("返回值错误！");
		}
		return simpleResponse;
	}
	public static SimpleResponse<UserLogin> loginResponse(String response) throws Exception{ 
		SimpleResponse<UserLogin> simpleResponse  = null;
		try {
		JsonElement jsonElement = jsonParser.parse(response);
		Type type = new TypeToken<SimpleResponse<UserLogin>>(){}.getType();
		simpleResponse = gson.fromJson(jsonElement, type);
		} catch (Exception e) {
			throw new Exception("返回值错误！");
		}
		return simpleResponse;
	}
	public static SimpleResponse<UserProfile> proflileResponse(String response) throws Exception{
		SimpleResponse<UserProfile> simpleResponse  = null;
		try {
		JsonElement jsonElement = jsonParser.parse(response);
		Type type = new TypeToken<SimpleResponse<UserProfile>>(){}.getType();
		simpleResponse = gson.fromJson(jsonElement, type);
		} catch (Exception e) {
			throw new Exception("返回值错误！");
		}
		return simpleResponse;
	}
	
	public static SimpleResponse<ArrayList<Department>> departmentListResponse(String response) throws Exception{
		SimpleResponse<ArrayList<Department>> simpleResponse  = null;
		try {
		JsonElement jsonElement = jsonParser.parse(response);
		Type type = new TypeToken<SimpleResponse<ArrayList<Department>>>(){}.getType();
		simpleResponse = gson.fromJson(jsonElement, type);
		} catch (Exception e) {
			throw new Exception("返回值错误！");
		}
		return simpleResponse;
	}
	
	public static SimpleResponse<ArrayList<Doctor>> doctorListResponse(String response) throws Exception{
		SimpleResponse<ArrayList<Doctor>> simpleResponse  = null;
		try {
		JsonElement jsonElement = jsonParser.parse(response);
		Type type = new TypeToken<SimpleResponse<ArrayList<Doctor>>>(){}.getType();
		simpleResponse = gson.fromJson(jsonElement, type);
		} catch (Exception e) {
			throw new Exception("返回值错误！");
		}
		return simpleResponse;
	}
	
	public static SimpleResponse<Registration> registrationResponse(String response) throws Exception{
		SimpleResponse<Registration> simpleResponse  = null;
		try {
		JsonElement jsonElement = jsonParser.parse(response);
		Type type = new TypeToken<SimpleResponse<Registration>>(){}.getType();
		simpleResponse = gson.fromJson(jsonElement, type);
		} catch (Exception e) {
			throw new Exception("返回值错误！");
		}
		return simpleResponse;
	}
	
	public static SimpleResponse<ArrayList<Registration>> registrationListResponse(String response) throws Exception{
		SimpleResponse<ArrayList<Registration>> simpleResponse  = null;
		try {
		JsonElement jsonElement = jsonParser.parse(response);
		Type type = new TypeToken<SimpleResponse<ArrayList<Registration>>>(){}.getType();
		simpleResponse = gson.fromJson(jsonElement, type);
		} catch (Exception e) {
			throw new Exception("返回值错误！");
		}
		return simpleResponse;
	}
}
