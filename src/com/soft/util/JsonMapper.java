package com.soft.util;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

public class JsonMapper {
	private ObjectMapper mapper;
	public JsonMapper()
	{
	     this.mapper = new ObjectMapper();
	     this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	public String toJson(Object object) throws IOException {
		 return this.mapper.writeValueAsString(object);
	}
	
	public <T> T fromJson(String jsonString, Class<T> clazz) throws IOException {
	     if ((jsonString == null) || ("".equals(jsonString.trim()))) {
	       return null;
	     }
	 
	     return this.mapper.readValue(jsonString, clazz);
	}
	
	public <T> T fromJson(String jsonString, TypeReference typeReference)
	     throws IOException{
		if ((jsonString == null) || ("".equals(jsonString.trim()))) {
	       return null;
	}
	 
	     return this.mapper.readValue(jsonString, typeReference);
	   }
	 
	public String toJsonP(String functionName, Object object) throws IOException{
	     return toJson(new JSONPObject(functionName, object));
	}
}
