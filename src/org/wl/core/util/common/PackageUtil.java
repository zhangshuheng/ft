package org.wl.core.util.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 全局变量帮助类
 * @author NSNP365
 *
 */
public class PackageUtil {
	
	public final  Map<String,Object> dataMap ;
	

	/**
	 * 私有的构造子(构造器,构造函数,构造方法)
	 */
	private PackageUtil(Map<String,Object> map) {
		dataMap = map;
	}
	
	/**
	 * 私有，静态的类自身实例
	 */
	private static PackageUtil instance = null;
	
	/**
	 * 公开，静态的工厂方法，需要使用时才去创建该单体
	 */
	public static PackageUtil getInstance() {
		if (instance == null) {
			instance = new PackageUtil(new HashMap<String, Object>());
		}
		return instance;
	}
	
	public void clearPackage(){
		instance = null;
	}
	
	public static void main(String[] args) {
	}
}
