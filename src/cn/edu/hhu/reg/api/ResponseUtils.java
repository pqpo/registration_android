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

public class ResponseUtils {
	
	private static JsonParser jsonParser = new JsonParser();
	private static Gson gson= new Gson();
	
	public static SimpleResponse<Object> SimpleResponse(String response){
		SimpleResponse<Object> simpleResponse;
		JsonElement jsonElement = jsonParser.parse(response);
		Type type = new TypeToken<SimpleResponse<Object>>(){}.getType();
		simpleResponse = gson.fromJson(jsonElement, type);
		return simpleResponse;
	}
	public static SimpleResponse<UserLogin> loginResponse(String response){
		SimpleResponse<UserLogin> simpleResponse;
		JsonElement jsonElement = jsonParser.parse(response);
		Type type = new TypeToken<SimpleResponse<UserLogin>>(){}.getType();
		simpleResponse = gson.fromJson(jsonElement, type);
		return simpleResponse;
	}
	public static SimpleResponse<UserProfile> proflileResponse(String response){
		SimpleResponse<UserProfile> simpleResponse;
		JsonElement jsonElement = jsonParser.parse(response);
		Type type = new TypeToken<SimpleResponse<UserProfile>>(){}.getType();
		simpleResponse = gson.fromJson(jsonElement, type);
		return simpleResponse;
	}
	
	public static SimpleResponse<ArrayList<Department>> departmentListResponse(String response){
		SimpleResponse<ArrayList<Department>> simpleResponse;
		JsonElement jsonElement = jsonParser.parse(response);
		Type type = new TypeToken<SimpleResponse<ArrayList<Department>>>(){}.getType();
		simpleResponse = gson.fromJson(jsonElement, type);
		return simpleResponse;
	}
	
	public static SimpleResponse<ArrayList<Doctor>> doctorListResponse(String response){
		SimpleResponse<ArrayList<Doctor>> simpleResponse;
		JsonElement jsonElement = jsonParser.parse(response);
		Type type = new TypeToken<SimpleResponse<ArrayList<Doctor>>>(){}.getType();
		simpleResponse = gson.fromJson(jsonElement, type);
		return simpleResponse;
	}
	
	public static SimpleResponse<Registration> registrationResponse(String response){
		SimpleResponse<Registration> simpleResponse;
		JsonElement jsonElement = jsonParser.parse(response);
		Type type = new TypeToken<SimpleResponse<Registration>>(){}.getType();
		simpleResponse = gson.fromJson(jsonElement, type);
		return simpleResponse;
	}
	
	public static SimpleResponse<ArrayList<Registration>> registrationListResponse(String response){
		SimpleResponse<ArrayList<Registration>> simpleResponse;
		JsonElement jsonElement = jsonParser.parse(response);
		Type type = new TypeToken<SimpleResponse<ArrayList<Registration>>>(){}.getType();
		simpleResponse = gson.fromJson(jsonElement, type);
		return simpleResponse;
	}
}
