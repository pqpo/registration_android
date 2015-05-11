package cn.edu.hhu.reg.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import cn.edu.hhu.reg.entity.UserLogin;
import cn.edu.hhu.reg.entity.UserProfile;

public class ResponseUtils {
	
	private static JsonParser jsonParser = new JsonParser();
	private static Gson gson= new Gson();
	
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
	
	
}
