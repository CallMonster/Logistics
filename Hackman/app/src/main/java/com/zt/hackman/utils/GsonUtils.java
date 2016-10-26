package com.zt.hackman.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;

/**
 * Gson类库的封装工具类，负责解析json数据 内部实现了Gson对象的单例
 * 
 */

public class GsonUtils {

	private static Gson gson = null;

	static {
		if (gson == null) {
			gson = new Gson();
			// 转换器
			// GsonBuilder builder = new GsonBuilder();
			// 不转换没有 @Expose 注解的字段
			// builder.excludeFieldsWithoutExposeAnnotation();
			// gson = builder.create();
		}
	}

	private GsonUtils() {

	}

	/**
	 * 将对象转换成json格式
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJson(Object ts) {
		String jsonStr = null;
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}

	/**
	 * 将对象转换成json格式(并自定义日期格式)
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJsonDateSerializer(Object ts,
			final String dateformat) {
		String jsonStr = null;
		gson = new GsonBuilder()
				.registerTypeHierarchyAdapter(Date.class,
						new JsonSerializer<Date>() {
							@SuppressLint("SimpleDateFormat")
							public JsonElement serialize(Date src,
									Type typeOfSrc,
									JsonSerializationContext context) {
								SimpleDateFormat format = new SimpleDateFormat(
										dateformat);
								return new JsonPrimitive(format.format(src));
							}
						}).setDateFormat(dateformat).create();
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}

	/**
	 * 将json格式转换成list对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static List<?> jsonToList(String jsonStr) {


		List<?> objList = null;
		if (gson != null) {
			Type type = new TypeToken<List<?>>() {
			}.getType();
			objList = gson.fromJson(jsonStr, type);
		}

		return objList;
	}

	/**
	 * 将json格式转换成list对象，并准确指定类型
	 * 
	 * @param <T>
	 * @param <clazz>
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static <T, clazz> List<?> jsonToList(String jsonStr, Class<T> clazz) {
		List<?> objList = null;
		if (gson != null) {
			Type type = new TypeToken<ArrayList<clazz>>() {
			}.getType();
			objList = gson.fromJson(jsonStr, type);
		}
		return objList;
	}

	/**
	 * 将json格式转换成list对象，并准确指定类型
	 * 
	 * @param jsonStr
	 * @param type
	 * @return
	 */
	public static List<?> jsonToList(String jsonStr, Type type) {
		List<?> objList = null;
		if (gson != null && (jsonStr != null && jsonStr.length() > 0)) {

			objList = gson.fromJson(jsonStr, type);
//			objList = gson.fromJson(objectToJson(getJsonValue(jsonStr, "result")), type);
		}
		return objList;
	}

	/**
	 * 将json格式转换成map对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<?, ?> jsonToMap(String jsonStr) {
		Map<?, ?> objMap = null;
		if (gson != null) {
			Type type = new TypeToken<Map<?, ?>>() {
			}.getType();
			objMap = gson.fromJson(jsonStr, type);
		}
		return objMap;
	}

	/**
	 * 将json转换成bean对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Object jsonToBean(String jsonStr, Class<?> cl) {
		Object obj = null;
		if (gson != null && !TextUtils.isEmpty(jsonStr)) {
			obj = gson.fromJson(jsonStr, cl);
		}
		return obj;
	}

	/**
	 * 获得JSON对象
	 * 
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> T getObjectJSON(String jsonString, Class<T> cls) {
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonString, cls);
		} catch (Exception e) {
		}
		return t;
	}

	/**
	 * 获得JSON对象列表
	 * 
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T, cls> List<T> getObjectJSONList(String jsonString,
			Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<cls>>() {
			}.getType());
		} catch (Exception e) {
		}
		return list;
	}

	public static List<Map<String, Object>> listKeyMaps(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString,
					new TypeToken<List<Map<String, Object>>>() {
					}.getType());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	public static <T> List<T> myJsonToList(String jsonString,Class<T> tClass) {
		List<T> list = new ArrayList<T>();
		try {
			JsonArray array = new JsonParser().parse(jsonString).getAsJsonArray();
			for (JsonElement ele : array){
				list.add(new Gson().fromJson(ele,tClass));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	/**
	 * 根据key获取值
	 * 
	 * @param jsonStr
	 * @param key
	 * @return
	 */
	public static Object getJsonValue(String jsonStr, String key) {
		Object rulsObj = null;
		Map<?, ?> rulsMap = jsonToMap(jsonStr);
		if (rulsMap != null && rulsMap.size() > 0) {
			rulsObj = rulsMap.get(key);
		}
		return rulsObj;
	}
}