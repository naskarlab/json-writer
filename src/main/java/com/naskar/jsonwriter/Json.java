package com.naskar.jsonwriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.naskar.jsonwriter.function.BiAction;

public class Json {
	
	private List<JsonValue> values;
	private Map<Class<?>, BiAction<Json, ?>> mappings;
	
	public Json() {
		this.values = new ArrayList<JsonValue>();
		this.mappings = new HashMap<Class<?>, BiAction<Json, ?>>();
	}
	
	Json(Map<Class<?>, BiAction<Json, ?>> mappings) {
		this.values = new ArrayList<JsonValue>();
		this.mappings = mappings;
	}
	
	Map<Class<?>, BiAction<Json, ?>> getMappings() {
		return mappings;
	}
	
	public JsonValue a(String name) {
		JsonValue v = new JsonValue(this, name);
		this.values.add(v);
		return v;
	}
	
	public <T> Json map(Class<T> clazz, BiAction<Json, T> action) {
		this.mappings.put(clazz, action);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> BiAction<Json, T> getMap(Class<T> clazz) {
		return (BiAction<Json, T>) this.mappings.get(clazz);
	}

	public String build() {
		StringBuilder sb = new StringBuilder("{");
		
		boolean first = true;
		for(JsonValue v : this.values) {
			
			if(first) {
				first = false;
			} else {
				sb.append(",");
			}
			
			sb.append("\"");
			sb.append(v.name());
			sb.append("\":");
			sb.append(v.build());
		}
		
		sb.append("}");
		
		return sb.toString();
	}
}
