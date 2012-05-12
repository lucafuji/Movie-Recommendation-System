package com.wzfuji.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lucafuji
 * represent an object as a <feature,feautr_value> map
 */
public class ReflectionUtils {
	/**
	 *  prevent instansilation of this object
	 */
	private ReflectionUtils(){
	}
	
	/**
	 * transform an object into a <feature,feautr_value> map with all its attributes
	 * @param obj
	 * @return feature map
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static Map<String,Object> getFeatureMap(Object obj) throws IllegalArgumentException, IllegalAccessException{
			Map<String,Object> featureMap = new HashMap<String,Object>();
			Class clazz = obj.getClass();
			for(Field field:clazz.getDeclaredFields()){
				field.setAccessible(true);
				featureMap.put(field.getName(), field.get(obj));
			}
			return featureMap;
	}
	

	/** transform an object into a <feature,feautr_value> map with the specified field name
	 * @param obj
	 * @param fieldNames
	 * @return featureMap
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public static Map<String,Object> getFeatureMap(Object obj,List<String> fieldNames) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException{
			Map<String,Object> featureMap = new HashMap<String,Object>();
			Class clazz = obj.getClass();
			for(String fieldName:fieldNames){
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				featureMap.put(field.getName(), field.get(obj));
			}
			return featureMap;
	}
	
	public static Map<String,String> getAttrTypeMap(Class clazz) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException{
		Map<String,String> attrMap = new HashMap<String,String>();
		for(Field field:clazz.getDeclaredFields()){
			field.setAccessible(true);
			attrMap.put(field.getName(), field.getClass().getSimpleName());
		}
		return attrMap;
	}
}
