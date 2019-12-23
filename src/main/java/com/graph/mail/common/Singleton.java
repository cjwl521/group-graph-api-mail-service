package com.graph.mail.common;

import java.util.HashMap;
import java.util.Map;

public class Singleton {
	private Map<String, String> map = new HashMap<String, String>();

	private static Singleton single = null;

	// 静态工厂方法
	public static Singleton getInstance() {
		if (single == null) {
			single = new Singleton();
		}
		return single;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public static Singleton getSingle() {
		return single;
	}

	public static void setSingle(Singleton single) {
		Singleton.single = single;
	}

}
