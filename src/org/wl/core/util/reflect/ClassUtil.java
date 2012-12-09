package org.wl.core.util.reflect;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import org.wl.core.util.reflect.JavassistUtil;
import org.wl.core.util.regexp.RegexpUtil;

@SuppressWarnings("unchecked")
public class ClassUtil {

	private static final Logger logger = Logger.getLogger(ClassUtil.class);

	public static IClassFactory classFactory = ClassFactory.getFastClassFactory();
	private final static ConcurrentHashMap<Class<?>, BeanInfo> beanInfoCache = new ConcurrentHashMap<Class<?>, BeanInfo>();

	// public static final String METHOD_TYPE_SET = "set";
	// public static final String METHOD_TYPE_GET = "get";
	// public static final String METHOD_TYPE_IS = "is";

	public static void loadClass(Class<?> classes) {
		classFactory.getClass(classes);
	}

	public static BeanInfo getBeanInfo(Class<?> clazz) {
		synchronized (beanInfoCache) {
			BeanInfo beanInfo;
			beanInfo = beanInfoCache.get(clazz);
			if (beanInfo == null) {
				try {
					beanInfo = Introspector.getBeanInfo(clazz, Object.class);
				} catch (Exception e) {
					logger.error(e);
				}
				beanInfoCache.put(clazz, beanInfo);
			}
			return beanInfo;
		}
	}

	public static <T> T newInstance(Class<T> objectClass) {
		try {
			return classFactory.getClass(objectClass).newInstance();
		} catch (Exception e) {
			logger.error("创建类:" + objectClass.getName() + "\t时出现异常!", e);
			return null;
		}
	}

	public static Object newInstance(Class<? extends Object> classType, Object retVal) {
		return classFactory.getClass(classType).newInstance(retVal);
	}
	
	    public static <T> T newInstance(Class<T> clazz, Class<?>[] parameterTypes, Object[] parameters) {
		return classFactory.getClass(getRealClass(clazz)).newInstance(parameterTypes, parameters);
	    }

	    public static <T> Class<T> getRealClass(Class<T> clazz) {
		if ((clazz != null) && (clazz.getName().contains("$$"))) {
		    Class superClass = clazz.getSuperclass();
		    if ((superClass != null) && (!Object.class.equals(superClass))) {
			return superClass;
		    }
		}
		return clazz;
	    }
	    
	public static Object newInstance(String classes) {
		try {
			return newInstance(Class.forName(classes));
		} catch (ClassNotFoundException e) {
			logger.error(e);
		}
		return null;
	}

	public static Property[] getPropertys(Object target) {
		return getPropertys(target.getClass());
	}

	public static Property[] getPropertys(Class<?> target) {
		return classFactory.getClass(target).getPropertys();
	}

	public static Property getProperty(Object target, String name) {
		return getProperty(target.getClass(), name);
	}

	public static Property getProperty(Class<?> target, String name) {
		return classFactory.getClass(target).getProperty(name);
	}

	public static Object getValue(Object target, String name) {
		Property property = getProperty(target, name);
		if (property != null && property.isRead()) {
			return property.getValue(target);
		} else {
			return classFactory.getClass(target.getClass()).getValue(target, name);
		}
	}

	public static Field getDeclaredField(Class<?> classes, String fieldName) {
		for (Class<?> superClass = classes; superClass != Object.class; superClass = superClass.getSuperclass())
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		return null;
	}

	public static Field[] getDeclaredFields(Class<?> clazz, Class<? extends Annotation> annotClass) {
		List<Field> fields = new ArrayList<Field>();
		for (Property property : getPropertys(clazz)) {
			Field field = getDeclaredField(clazz, property.getName());
			if (field != null && field.getAnnotation(annotClass)!=null) {
				fields.add(field);
			}
		}
		return fields.toArray(new Field[fields.size()]);
	}

	public static void setValue(Object target, String name, Object value) {
		Property property = getProperty(target, name);
		if (property != null && property.isWrite()) {
			property.setValue(target, value);
		} else {
			classFactory.getClass(target.getClass()).setValue(target, name, value);
		}
	}

	public static MethodProxy getMethod(Class<?> classType, String method) {
		try {
			return classFactory.getClass(classType).getMethod(method);
		} catch (Exception e) {
			logger.error(classType + "." + method + "-" + e.getMessage());
			return null;
		}
	}
	
	public static Method getDeclaredMethod(Class<?> clazz, String methodName) {
		MethodProxy proxy = getMethod(clazz, methodName);
		return proxy == null?null:proxy.getMethod();
	}

	public static MethodProxy getMethod(Class<?> classType, String methodName, Class<?>... parameterTypes) {
		return classFactory.getClass(classType).getMethod(methodName, parameterTypes);
	}

	/**
	 * 判断属性是不是基本数据类型
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isPrimitiveType(Field field) {
		Class<?> type = getWrappedType(field);
		return isPrimitiveType(type);
	}

	/**
	 * 判断是否是基本数据类型
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isPrimitiveType(Class<?> type) {
		type = getWrappedType(type);
		return Date.class.isAssignableFrom(type) || Integer.class.isAssignableFrom(type) || Float.class.isAssignableFrom(type) || Long.class.isAssignableFrom(type) || Boolean.class.isAssignableFrom(type) || String.class.isAssignableFrom(type) || Character.class.isAssignableFrom(type) || Double.class.isAssignableFrom(type) || type.isEnum();
	}

	/**
	 * @param object
	 * @return
	 */
	public static boolean isPrimitiveType(Object object) {
		return isPrimitiveType(object.getClass());
	}

	/**
	 * 获得属性的类型 原始类型转换为封装类型
	 * 
	 * @param type
	 * @return
	 */
	public static Class<?> getWrappedType(Field field) {
		Class<?> type = field.getType();
		return getWrappedType(type);
	}

	public static Class<?> getWrappedType(Class<?> type) {
		if (type == int.class) {
			type = Integer.class;
		} else if (type == float.class) {
			type = Float.class;
		} else if (type == long.class) {
			type = Long.class;
		} else if (type == boolean.class) {
			type = Boolean.class;
		} else if (type == char.class) {
			type = Character.class;
		} else if (type == double.class) {
			type = Double.class;
		}
		return type;
	}

	/**
	 * 判断是否是数字
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isNumber(Class<?> type) {
		type = ClassUtil.getWrappedType(type);
		return Integer.class.isAssignableFrom(type) || Float.class.isAssignableFrom(type) || Long.class.isAssignableFrom(type) || Character.class.isAssignableFrom(type) || Double.class.isAssignableFrom(type);
	}

	/**
	 * 根据指定的 componentType 类型和 length 来初始化一个数组
	 * 
	 * @param componentType
	 * @param length
	 * @return
	 */
	public static Object newInstance(Class<?> componentType, int length) {
		return Array.newInstance(componentType, length);
	}

	/**
	 * @param field
	 * @return
	 */
	public static boolean isArray(Field field) {
		return isArray(field.getType());
	}

	/**
	 * @param object
	 * @return
	 */
	public static boolean isArray(Object object) {
		return object != null && isArray(object.getClass());
	}

	/**
	 * @param object
	 * @return
	 */
	public static boolean isArray(Class<?> classes) {
		return classes.isArray();
	}

	/**
	 * 判断属性是否为接口.
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isInterface(Field field) {
		if (isMap(field) || isList(field))
			return false;
		if (isArray(field)) {
			return field.getType().getComponentType().isInterface();
		}
		return isInterface(field.getType());
	}

	/**
	 * @param field
	 * @return
	 */
	public static boolean isList(Field field) {
		return field.getType() == List.class;
	}

	/**
	 * @param field
	 * @return
	 */
	public static boolean isMap(Field field) {
		return field.getType() == Map.class;
	}

	/**
	 * @param objectClass
	 * @return
	 */
	public static boolean isList(Object obj) {
		return obj instanceof List<?>;
	}

	public static boolean isList(Class<?> classes) {
		return List.class.isAssignableFrom(classes);
	}

	/**
	 * @param objectClass
	 * @return
	 */
	public static boolean isMap(Object obj) {
		return obj instanceof Map<?, ?>;
	}

	/**
	 * @param objectClass
	 * @return
	 */
	public static boolean isInterface(Class<?> objectClass) {
		return objectClass.isInterface();
	}

	public static boolean isLong(Class<?> objectClass) {
		return Long.class.isAssignableFrom(getWrappedType(objectClass));
	}

	public static boolean isString(Class<?> classes) {
		return String.class.isAssignableFrom(getWrappedType(classes));
	}

	/**
	 * 通过反射,获得指定类的父类的泛型参数的实际类型. 如BuyerServiceBean extends DaoSupport<Buyer>
	 * 
	 * @param clazz
	 *            clazz 需要反射的类,该类必须继承范型父类
	 * @param index
	 *            泛型参数所在索引,从0开始.
	 * @return 范型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class getSuperClassGenricType(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();// 得到泛型父类
		// 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		// 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class, 如BuyerServiceBean extends
		// DaoSupport<Buyer,Contact>就返回Buyer和Contact类型
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * 通过反射,获得指定类的父类的第一个泛型参数的实际类型. 如BuyerServiceBean extends DaoSupport<Buyer>
	 * 
	 * @param clazz
	 *            clazz 需要反射的类,该类必须继承泛型父类
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得方法返回值泛型参数的实际类型. 如: public Map<String, Buyer> getNames(){}
	 * 
	 * @param Method
	 *            method 方法
	 * @param int index 泛型参数所在索引,从0开始.
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class getMethodGenericReturnType(Method method, int index) {
		Type returnType = method.getGenericReturnType();
		if (returnType instanceof ParameterizedType) {
			ParameterizedType type = (ParameterizedType) returnType;
			Type[] typeArguments = type.getActualTypeArguments();
			if (index >= typeArguments.length || index < 0) {
				throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
			}
			return (Class) typeArguments[index];
		}
		return Object.class;
	}

	/**
	 * 通过反射,获得方法返回值第一个泛型参数的实际类型. 如: public Map<String, Buyer> getNames(){}
	 * 
	 * @param Method
	 *            method 方法
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class getMethodGenericReturnType(Method method) {
		return getMethodGenericReturnType(method, 0);
	}

	/**
	 * 通过反射,获得方法输入参数第index个输入参数的所有泛型参数的实际类型. 如: public void add(Map<String,
	 * Buyer> maps, List<String> names){}
	 * 
	 * @param Method
	 *            method 方法
	 * @param int index 第几个输入参数
	 * @return 输入参数的泛型参数的实际类型集合, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回空集合
	 */
	public static List<Class> getMethodGenericParameterTypes(Method method, int index) {
		List<Class> results = new ArrayList<Class>();
		Type[] genericParameterTypes = method.getGenericParameterTypes();
		if (index >= genericParameterTypes.length || index < 0) {
			throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
		}
		Type genericParameterType = genericParameterTypes[index];
		if (genericParameterType instanceof ParameterizedType) {
			ParameterizedType aType = (ParameterizedType) genericParameterType;
			Type[] parameterArgTypes = aType.getActualTypeArguments();
			for (Type parameterArgType : parameterArgTypes) {
				Class parameterArgClass = (Class) parameterArgType;
				results.add(parameterArgClass);
			}
			return results;
		}
		return results;
	}

	/**
	 * 通过反射,获得方法输入参数第一个输入参数的所有泛型参数的实际类型. 如: public void add(Map<String, Buyer>
	 * maps, List<String> names){}
	 * 
	 * @param Method
	 *            method 方法
	 * @return 输入参数的泛型参数的实际类型集合, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回空集合
	 */
	public static List<Class> getMethodGenericParameterTypes(Method method) {
		return getMethodGenericParameterTypes(method, 0);
	}

	public static Class<?> getFieldGenericType(Field field) {
		return getFieldGenericType(field, 0);
	}

	/**
	 * 通过反射,获得Field泛型参数的实际类型. 如: public Map<String, Buyer> names;
	 * 
	 * @param Field
	 *            field 字段
	 * @param int index 泛型参数所在索引,从0开始.
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class getFieldGenericType(Field field, int index) {
		Type genericFieldType = field.getGenericType();
		if (genericFieldType instanceof ParameterizedType) {
			ParameterizedType aType = (ParameterizedType) genericFieldType;
			Type[] fieldArgTypes = aType.getActualTypeArguments();
			if (index >= fieldArgTypes.length || index < 0) {
				throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
			}
			return (Class) fieldArgTypes[index];
		}
		return Object.class;
	}
	
	public static <T extends Annotation> T getFieldGenericType(Field field, Class<T> annotClass) {
		return field.getAnnotation(annotClass);
	}

	public static <T extends Annotation> T getFieldGenericType(Class<?> clazz, String fieldName, Class<T> annotClass) {
		return ClassUtil.getDeclaredField(clazz, fieldName).getAnnotation(annotClass);
	}
	
	public static String[] getParamNames(Class<?> clazz, String methodname, Class<?>... parameterTypes) {
		return getParamNames(clazz.getName(), methodname, parameterTypes);
	}

	public static String[] getParamNames(String classname, String methodname, Class<?>... parameterTypes) {
		try {
			return JavassistUtil.getParamNames(classname, methodname, parameterTypes);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new String[0];
	}

	public static Annotation getParamAnno(Method method) {
		return getParamAnnos(method, 0, 0);
	}
	
	public static Annotation[] getParamAnnos(Method method,Class<? extends Annotation> annotClass) {
		Annotation[][] annotations = method.getParameterAnnotations();
		for(Annotation[] paramAnnots : annotations){
			for(Annotation annot : paramAnnots){
				if(annotClass.equals(annot.annotationType()))
					return paramAnnots;
			}
		}
		return null;
	}
	
	public static <T extends Annotation> T getAnnotation(Annotation[] annotations,Class<T> annotClass){
		for(Annotation annot : annotations){
			if(annotClass.equals(annot.annotationType()))
				return (T) annot;
		}
		return null;
	}

	public static Annotation getParamAnnos(Method method, int i, int j) {
		return getParamAnnos(method, i)[j];
	}

	public static Annotation[] getParamAnnos(Method method, int i) {
		Annotation[][] annotations = method.getParameterAnnotations();
		return annotations[i];
	}

	public static Annotation[] getMethodAnnos(Method method) {
		return method.getAnnotations();
	}

	public static <T extends Annotation> T getMethodAnno(Method method, Class<T> classes) {
		if (method.isAnnotationPresent(classes)) {
			return method.getAnnotation(classes);
		}
		return null;
	}

	public static Class<?> forName(String className) {
		try {
			return Class.forName(RegexpUtil.replace(className, "\\$[\\S\\s]+", ""));
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public static <T extends Annotation> T getMethodAnnoByStackTrace(Class<T> annotClass) {
		long start = System.currentTimeMillis();
		StackTraceElement stacks[] = (new Throwable()).getStackTrace();
		for (StackTraceElement stack : stacks) {
			Class<?> clasz = ClassUtil.forName(stack.getClassName());
			if (clasz!=null && !ClassUtil.class.isAssignableFrom(clasz)) {
				MethodProxy methodProxy = ClassUtil.getMethod(clasz, stack.getMethodName());
				if (methodProxy!=null) {
					T annotation = ClassUtil.getMethodAnno(methodProxy.getMethod(), annotClass);
					if (annotation != null) {
						logger.error("找到" + annotation + "耗时：" + (System.currentTimeMillis() - start) + "ms");
						return annotation;
					}
				}
			}
		}
		logger.error("未找到耗时：" + (System.currentTimeMillis() - start) + "ms");
		return null;
	}
	
	public static <T extends Annotation> T getClassGenricType(Class clazz,Class<T> annotClass) {
		return (T) clazz.getAnnotation(annotClass);
	}

}
