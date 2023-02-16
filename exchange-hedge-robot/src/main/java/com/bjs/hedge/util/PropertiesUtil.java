package com.bjs.hedge.util;


import com.bjs.hedge.config.binance.BinanceConfig;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * 配置文件读取和写入工具类
 * @author liuhuan.liuh
 *
 */
public class PropertiesUtil {
	private static final Logger log = LoggerFactory.getLogger(PropertiesUtil.class);
	private static Properties properties;
	private static PropertiesUtil instance = null;
	private PropertiesUtil() { }
	
	public static PropertiesUtil getInstance() {
		if (instance == null){
			instance = new PropertiesUtil();
		}
		return instance;
	}

	public static void reload() {
		instance = new PropertiesUtil();
	}
	
	public void init(String filename) {
		properties = new Properties();
		//String filename = "config.properties";
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(filename);
			properties.load(is);
		} catch (IOException e) {
			log.error("load config error",e.toString());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception ex) {
				}
			}
		}
	}

	public Properties getProperties() {
		return properties;
	}

	/**
	 * 得到key的值
	 * 
	 * @param key 取得其值的键
	 * @return key的值
	 */
	public String getValue(String key) {
		if (properties.containsKey(key)) {
			String value = properties.getProperty(key);
			return value;
		} else {
			return null;
		}
	}

	/**
	 * 得到key的值
	 * 
	 * @param key 取得其值的键
	 * @return key的值
	 */
	public String getValue(String key, String defaultValue) {
		if (properties.containsKey(key)) {
			String value = properties.getProperty(key, defaultValue);
			return value;
		} else {
			return defaultValue;
		}
	}

	public int getInt(String key, int defaultValue) {
		String value = getValue(key);
		if (StringUtils.isNumericSpace(value)) {
			Integer i = Ints.tryParse(value);
			if (i != null) {
				return i.intValue();
			}
		}
		return defaultValue;
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		String value = getValue(key);
		if (value == null) {
			return defaultValue;
		}
		if (value.equalsIgnoreCase("true")) {
			return true;
		}
		if (value.equalsIgnoreCase("false")) {
			return false;
		}
		return defaultValue;
	}

	public double getDouble(String key, double defaultValue) {
		String value = getValue(key);
		if (StringUtils.isNumericSpace(value)) {
			Double i = Doubles.tryParse(value);
			if (i != null) {
				return i.intValue();
			}
		}
		return defaultValue;
	}

//	public static void main(String[] args) throws Exception {
//		System.out.println(PropertiesUtil.getInstance().getValue("map.max_page_number"));
//		Thread.sleep(20000);
//		PropertiesUtil.reload();
//		System.out.println(PropertiesUtil.getInstance().getValue("map.max_page_number"));
//	}

	public <T> T getBean(String prefix, Class<T> clazz){
		init("application.properties");
		try {
			T t = clazz.newInstance();
			Field[] declaredFields = clazz.getDeclaredFields();
			for (Field field : declaredFields) {
				String name = field.getName();
				String value = getValue(String.format("%s.%s",prefix,name));
				field.set(t,value);
			}
			return t;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args)throws Exception {

		Object o = PropertiesUtil.getInstance().getBean("binance", BinanceConfig.class);
	}
}
