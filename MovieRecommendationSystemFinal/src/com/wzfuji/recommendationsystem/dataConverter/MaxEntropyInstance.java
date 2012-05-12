package com.wzfuji.recommendationsystem.dataConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.wzfuji.util.ReflectionUtils;


/**
 * @author lucafuji 
 * an instance of the data object which is input of the max entropy algorithm
 * the format is
 * feature1=value1 feature2=value2 ... class value
 */
public class MaxEntropyInstance<F,C> {
	private Map<String, Object> featureMap;
	private String valuePairWithTag;
	private String valuePairWithoutTag;
	private F featuresObject;
	private C classValue;
	public MaxEntropyInstance(F instance,C classValue) throws IllegalArgumentException, IllegalAccessException {
		super();
		this.classValue = classValue;
		this.featuresObject = instance;
		this.featureMap = ReflectionUtils.getFeatureMap(instance);
	}

	public Object getFeatureValue(String feature) {
		return this.featureMap.get(feature);
	}

	public void setFeatureValue(String feature, Object value) {
		this.featureMap.put(feature, value);
	}
	
	public void removeFeatureValue(String feature,Object value){
		this.featureMap.remove(feature);
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Object> entry : this.featureValuePairs()) {
			sb.append(" " + entry.getKey() + "=" + entry.getValue());
		}
		sb.append(classValue);
		return sb.toString();
	}

	public Set<Entry<String, Object>> featureValuePairs() {
		return this.featureMap.entrySet();
	}

	public String toValuePairStringWithTag() {
		StringBuilder sb = new StringBuilder();
		sb.append(toValuePairStringWithoutTag());
		sb.append(classValue);
		valuePairWithTag = sb.toString();
		return valuePairWithTag;
	}

	public String toValuePairStringWithoutTag() {
		StringBuilder sb = new StringBuilder();
		List<String> keyList = new ArrayList<String>();
		keyList.addAll(this.featureMap.keySet());
		Collections.sort(keyList);
		for (String key : keyList) {
			sb.append(key + "=" + this.featureMap.get(key) + " ");
		}
		valuePairWithoutTag = sb.toString();
		return valuePairWithoutTag;
	}

	public String[] toFeatureValuePairArray() {
		int size = this.featureMap.entrySet().size();
		String result[] = new String[size];
		int i = 0;
		List<String> keyList = new ArrayList<String>();
		keyList.addAll(this.featureMap.keySet());
		Collections.sort(keyList);
		for (String key : keyList) {
			result[i++] = key + "=" + this.featureMap.get(key);
		}
		return result;
	}
}
