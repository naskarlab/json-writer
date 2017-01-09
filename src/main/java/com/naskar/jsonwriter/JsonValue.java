package com.naskar.jsonwriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.naskar.jsonwriter.function.Action;
import com.naskar.jsonwriter.function.BiAction;

public class JsonValue {
	
	private Json json;
	private String name;
	private Object value;
	private List<Action<Json>> actions;
	
	JsonValue(Json json, String name) {
		this.json = json;
		this.name = name;
		this.actions = new ArrayList<Action<Json>>();
	}
	
	public String name() {
		return name;
	}
	
	public Json v(Integer v) {
		this.value = v;
		this.actions.clear();
		return this.json;
	}
	
	public Json v(String v) {
		this.value = "\"" + v + "\"";
		this.actions.clear();
		return this.json;
	}
	
	@SuppressWarnings("unchecked")
	public Json v(Object v) {
		this.actions.clear();
		if(v != null) {
			BiAction<Json, Object> t = 
				(BiAction<Json, Object>) this.json.getMap(v.getClass());
			if(t != null) {
				this.actions.add(x -> t.run(x, v));
				
			} else {
				this.value = v;
				
			}
		} else {
			this.value = null;
		}
		return this.json;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Json v(Collection<T> l) {
		this.actions.clear();
		if(l != null && !l.isEmpty()) {
			Class<?> clazz = l.iterator().next().getClass();
			BiAction<Json, Object> t = 
					(BiAction<Json, Object>) this.json.getMap(clazz);
			if(t != null) {
				for(T v : l) {
					this.actions.add(x -> t.run(x, v));
				}
			} else {
				this.value = l;
			}
		} else {
			this.value = null;
		}
		
		return this.json;
	}
	
	@SafeVarargs
	public final Json v(Action<Json>... actions) {
		this.value = null;
		this.actions.clear();
		this.actions.addAll(Arrays.asList(actions));
		return this.json;
	}
	
	public <T> Json v(Collection<T> l, BiAction<Json, T> t) {
		this.value = null;
		this.actions.clear();
		for(T i : l) {
			this.actions.add(x -> t.run(x, i));
		}
		return this.json;
	}
	
	public String build() {
		StringBuilder sb = new StringBuilder();
		
		if(this.actions.isEmpty()) {
			sb.append(this.value);
			
		} else {
			
			if(actions.size() > 1) {
				sb.append("[");
			}
			
			boolean first = true;
			for(Action<Json> a : actions) {
				
				if(first) {
					first = false;
				} else {
					sb.append(",");
				}
				
				Json json = new Json(this.json.getMappings());
				a.run(json);
				sb.append(json.build());
			}
			if(actions.size() > 1) {
				sb.append("]");
			}
		}
		
		return sb.toString();
	}
}
