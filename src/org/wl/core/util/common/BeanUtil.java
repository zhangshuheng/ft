package org.wl.core.util.common;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ognl.OgnlException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.wl.core.util.ognl.OgnlUtil;
import org.wl.core.util.reflect.ClassUtil;
import org.wl.core.util.reflect.Property;
import org.wl.core.util.regexp.RegexpUtil;

public class BeanUtil {

	private static final Log logger = LogFactory.getLog(BeanUtil.class);
	
	public static void setValue(Object target, String fieldName, Object value) {
		ClassUtil.setValue(target,fieldName,value);
	}

	public static Object getValue(Object target, String fieldName) {
		return ClassUtil.getValue(target, fieldName);
	}

	public static void copyProperties(Object dest,Object orig,String... excludeProperties) {
		Property[] propertys = ClassUtil.getPropertys(dest);
		for(Property property : propertys){
			if(ObjectUtil.indexOf(excludeProperties, property.getName()) >= 0)continue;
			if(!property.isWrite())continue;
			Property source = ClassUtil.getProperty(orig,property.getName());
			if(ObjectUtil.isNull(source) || !source.isRead())continue;
			if(!source.getPropertyType().isAssignableFrom(property.getPropertyType()))continue;
			property.setValue(dest, source.getValue(orig));
		}
	}

	/**
	 * 对象深度COPY
	 * @param <T>
	 * @param dest
	 * @param orig
	 * @param excludeProperties 正则表达式
	 * @return
	 */
	public static <T> T copy(T dest, Object orig, String... excludeProperties) {
		return copy(dest, orig, "", excludeProperties);
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T copy(T dest,Object orig,String superName,String... excludeProperties) {
		try {
			for(Map.Entry<String,Object> entry : OgnlUtil.getInstance().getBeanMap(orig).entrySet()){
				if(RegexpUtil.find(superName.concat(entry.getKey()),excludeProperties))continue;
				if(ObjectUtil.isNull(entry.getValue()))continue;
				if(ObjectUtil.isNull(ClassUtil.getProperty(dest, entry.getKey())))continue;
				if(!ClassUtil.getProperty(dest, entry.getKey()).isWrite())continue;
				if(ClassUtil.isPrimitiveType(entry.getValue()))
					OgnlUtil.getInstance().setValue(entry.getKey(),dest,entry.getValue());
				else if(ClassUtil.isList(ClassUtil.getProperty(dest, entry.getKey()).getPropertyType())){	
					List<Object> list = new ArrayList<Object>(); 
					OgnlUtil.getInstance().setValue(entry.getKey(),dest,list);
					int length = ObjectUtil.length(entry.getValue());
					for(int i=0;i<length;i++){
						list.add(copy(ClassUtil.newInstance(ClassUtil.getMethodGenericParameterTypes(ClassUtil.getProperty(dest,entry.getKey()).getWriteMethod().getMethod()).get(0)),ObjectUtil.get(entry.getValue(),i),superName.concat(entry.getKey()).concat("[").concat(String.valueOf(i)).concat("]").concat("."),excludeProperties));
					}
				}else{
					Object object = OgnlUtil.getInstance().getValue(entry.getKey(), dest);
					if(object == null)
						System.out.println("NULL:"+ClassUtil.getProperty(dest,entry.getKey()).getWriteMethod());
//					copy(,entry.getValue(),superName,excludeProperties);
				}
			}
		} catch (IntrospectionException e) {
			logger.debug(e.getMessage(),e);
		} catch (OgnlException e) {
			logger.debug(e.getMessage(),e);
		}
		return dest;
	}

	/*
	 		Property[] propertys = ClassUtil.getPropertys(orig);
		for(Property property : propertys){
			if(!property.isRead())//没有get方法
				continue;
			if(RegexpUtil.find(superName.concat(property.getName()),excludeProperties)){//被排除
				continue;
			}
			Property toProperty = ClassUtil.getProperty(dest,property.getName());
			if(!toProperty.isWrite())//目标没有set方法
				continue;
			Object value = property.getValue(orig);
			if(ObjectUtil.isNull(value))//源值为NULL
				continue;
			if(ClassUtil.isPrimitiveType(value)){//为基本数据类型(包含Enum及Date)
				toProperty.setValue(dest, value);
			}else if(ClassUtil.isArray(value)){//为数组
				logger.debug("不能构造对象.");
			}else if(ClassUtil.isList(value)){//为集合
				Object o = toProperty.getValue(dest);
				if(StringUtil.isNull(o)){
					o = new ArrayList<Object>();
				}
				int size = ((List)value).size();
				for(int i=0;i<size;i++){
					Object v = ((List)value).get(i);
					if(StringUtil.isNull(v))
						continue;
					if(ClassUtil.isPrimitiveType(v)){
						
					}else{
						v = OgnlUtil.getValue(superName.concat(property.getName()).concat("["+i+"]"), orig);
						System.out.println(superName.concat(property.getName()).concat("["+i+"]")+"|"+v);
					}				
				}
			}else{//对象类型继续clone下级
				Object o = toProperty.getValue(dest);
				if(StringUtil.isNull(o)){
					o = ClassUtil.newInstance(toProperty.getPropertyType());
				}
				if(ObjectUtil.isNull(o))//不能实例化对象
					continue;
				toProperty.setValue(dest,copy(o,value,superName.concat(property.getName()).concat("."),excludeProperties));
			}
		}
	 */
}
