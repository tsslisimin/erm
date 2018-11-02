package com.coomia.erm.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public R() {
		super.put("code", 200);
	}

	public static R error() {
		return error(500, "未知异常，请联系管理员");
	}

	public static R error(String msg) {
		return error(500, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	@SuppressWarnings("unchecked")
	public R put(String key, Object value) {
		Map<String, Object> subMap = new HashMap<String, Object>();
		if (super.get("data") != null && !((Map<String, Object>) super.get("data")).isEmpty()) {
			subMap = (Map<String, Object>) super.get("data");
		}
		if ("wyStatus"==key){
			super.put(key,value);
		}
		if ("code" == key) {
			super.put(key, value);
		} else {
			subMap.put(key, value);
			super.put("data", subMap);
		}
		return this;
	}
}
