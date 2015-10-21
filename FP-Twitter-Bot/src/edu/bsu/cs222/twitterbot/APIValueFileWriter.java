package edu.bsu.cs222.twitterbot;

import java.util.Map;

import org.json.simple.JSONObject;

public class APIValueFileWriter {
	
	private String apiKey;
	private String apiSecret;
	
	public APIValueFileWriter(String apiKey, String apiSecret) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
	}
	
	public JSONObject createJSONObject(){
		JSONObject apiJsonObject = new JSONObject();
		asMap(apiJsonObject).put("apiKey", this.apiKey);
		asMap(apiJsonObject).put("apiSecret", this.apiSecret);
		return apiJsonObject;
	}
	
	@SuppressWarnings("unchecked")
	private final static Map<Object,Object> asMap(JSONObject jsonObject) {
	  return jsonObject;
	}
	
}