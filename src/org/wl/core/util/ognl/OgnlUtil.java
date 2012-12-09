package org.wl.core.util.ognl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ognl.DefaultTypeConverter;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import ognl.OgnlRuntime;
import ognl.TypeConverter;

@SuppressWarnings("unchecked")
public class OgnlUtil {
	
	private static OgnlUtil ognlUtil = new OgnlUtil();
	
	private ConcurrentHashMap<String, Object> expressions = new ConcurrentHashMap<String, Object>();
	private final ConcurrentHashMap<Class, BeanInfo> beanInfoCache = new ConcurrentHashMap<Class, BeanInfo>();

	private Map<Class<?>, TypeConverter> typeConverters = new HashMap<Class<?>, TypeConverter>();
	private TypeConverter defaultTypeConverter = new DefaultTypeConverter();

	private TypeConverter typeConverter = new TypeConverter() {

		public Object convertValue(Map context, Object root, Member member, String name, Object value, Class toType) {
			if (typeConverters.containsKey(toType.getClass())) {
				return typeConverters.get(toType.getClass()).convertValue(context, root, member, name, value, toType);
			} else {
				return defaultTypeConverter.convertValue(context, root, member, name, value, toType);
			}
		}

	};
	
	public static OgnlUtil getInstance(){
		return OgnlUtil.ognlUtil;
	}

	public void setValue(String name, Object root, Object value) throws OgnlException {
		Ognl.setValue(compile(name), getRealTarget(name, new HashMap<String, Object>(), root), value);
	}

	public void setValue(String name, Map<String, Object> context, Object root, Object value) throws OgnlException {
		Ognl.setValue(compile(name), getRealTarget(name, context, root), value);
	}

	public Object getValue(String key, Object root) {
		return getValue(key, null, root);
	}

	public Object getValue(String name, Map<String, Object> context, Object root) {
		try {
			if (context == null) {
				return Ognl.getValue(name, root);
			}
			return Ognl.getValue(name, context, root);
		} catch (Exception e) {
			return null;
		}
	}

	public Object getValue(String name, Map<String, Object> context, Object root, Class resultType) throws OgnlException {
		return resultType;
	}

	public Object compile(String expression) throws OgnlException {
		synchronized (expressions) {
			Object o = expressions.get(expression);
			if (o == null) {
				o = Ognl.parseExpression(expression);
				expressions.put(expression, o);
			}
			return o;
		}
	}

	public PropertyDescriptor[] getPropertyDescriptors(Object source) throws IntrospectionException {
		BeanInfo beanInfo = getBeanInfo(source);
		return beanInfo.getPropertyDescriptors();
	}

	public PropertyDescriptor[] getPropertyDescriptors(Class clazz) throws IntrospectionException {
		BeanInfo beanInfo = getBeanInfo(clazz);
		return beanInfo.getPropertyDescriptors();
	}

	public Map<String, Object> getBeanMap(Object source) throws IntrospectionException, OgnlException {
		Map<String, Object> beanMap = new HashMap<String, Object>();
		Map<String, ?> sourceMap = Ognl.createDefaultContext(source);
		PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(source);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propertyName = propertyDescriptor.getDisplayName();
			Method readMethod = propertyDescriptor.getReadMethod();
			if (readMethod != null) {
				Object expr = compile(propertyName);
				Object value = Ognl.getValue(expr, sourceMap, source);
				beanMap.put(propertyName, value);
			} else {
				beanMap.put(propertyName, "There is no read method for " + propertyName);
			}
		}
		return beanMap;
	}

	public BeanInfo getBeanInfo(Object from) throws IntrospectionException {
		return getBeanInfo(from.getClass());
	}

	public BeanInfo getBeanInfo(Class clazz) throws IntrospectionException {
		synchronized (beanInfoCache) {
			BeanInfo beanInfo;
			beanInfo = beanInfoCache.get(clazz);
			if (beanInfo == null) {
				beanInfo = Introspector.getBeanInfo(clazz, Object.class);
				beanInfoCache.put(clazz, beanInfo);
			}
			return beanInfo;
		}
	}

	public void setProperties(Map<String, ?> props, Object o, Map<String, Object> context, boolean throwPropertyExceptions) throws Exception {
		if (props == null) {
			return;
		}
		Ognl.setTypeConverter(context, getTypeConverterFromContext(context));
		Object oldRoot = Ognl.getRoot(context);
		Ognl.setRoot(context, o);
		for (Map.Entry<String, ?> entry : props.entrySet()) {
			String expression = entry.getKey();
			internalSetProperty(expression, entry.getValue(), o, context, throwPropertyExceptions);
		}
		Ognl.setRoot(context, oldRoot);
	}

	void internalSetProperty(String name, Object value, Object o, Map<String, Object> context, boolean throwPropertyExceptions) throws Exception {
		try {
			setValue(name, context, o, value);
		} catch (OgnlException e) {
			Throwable reason = e.getReason();
			String msg = "Caught OgnlException while setting property '" + name + "' on type '" + o.getClass().getName() + "'.";
			Throwable exception = (reason == null) ? e : reason;
			if (throwPropertyExceptions) {
				throw new Exception(msg, exception);
			} else {
			}
		}
	}

	TypeConverter getTypeConverterFromContext(Map<String, Object> context) {
		return this.typeConverter;
	}

	public Object getRealTarget(String property, Map<String, Object> context, Object root) throws OgnlException {
		if ("top".equals(property)) {
			return root;
		}
		try {
			Object target = root;
			OgnlContext ognlContext = (OgnlContext) Ognl.createDefaultContext(target);
			ognlContext.setTypeConverter(typeConverter);
			if (OgnlRuntime.hasSetProperty(ognlContext, target, property) || OgnlRuntime.hasGetProperty(ognlContext, root, property) || OgnlRuntime.getIndexedPropertyType(ognlContext, root.getClass(), property) != OgnlRuntime.INDEXED_PROPERTY_NONE) {
				return target;
			}
			return context != null ? context : null;
		} catch (IntrospectionException ex) {
			throw new OgnlException("Cannot figure out real target class", ex);
		}
	}

	public void copy(Object from, Object to, Map<String, Object> context) {
		copy(from, to, context, null, null);
	}

	public void copy(Object from, Object to, Map<String, Object> context, Collection<String> exclusions, Collection<String> inclusions) {
		if (from == null || to == null) {
			return;
		}
		TypeConverter conv = getTypeConverterFromContext(context);
		Map contextFrom = Ognl.createDefaultContext(from);
		Ognl.setTypeConverter(contextFrom, conv);
		Map contextTo = Ognl.createDefaultContext(to);
		Ognl.setTypeConverter(contextTo, conv);
		PropertyDescriptor[] fromPds;
		PropertyDescriptor[] toPds;
		try {
			fromPds = getPropertyDescriptors(from);
			toPds = getPropertyDescriptors(to);
		} catch (IntrospectionException e) {
			// LOG.error("An error occured", e);
			return;
		}
		Map<String, PropertyDescriptor> toPdHash = new HashMap<String, PropertyDescriptor>();
		for (PropertyDescriptor toPd : toPds) {
			toPdHash.put(toPd.getName(), toPd);
		}
		for (PropertyDescriptor fromPd : fromPds) {
			if (fromPd.getReadMethod() != null) {
				boolean copy = true;
				if (exclusions != null && exclusions.contains(fromPd.getName())) {
					copy = false;
				} else if (inclusions != null && !inclusions.contains(fromPd.getName())) {
					copy = false;
				}
				if (copy == true) {
					PropertyDescriptor toPd = toPdHash.get(fromPd.getName());
					if ((toPd != null) && (toPd.getWriteMethod() != null)) {
						try {
							Object expr = compile(fromPd.getName());
							Object value = Ognl.getValue(expr, contextFrom, from);
							Ognl.setValue(expr, contextTo, to, value);
						} catch (OgnlException e) {
							// ignore, this is OK
						}
					}
				}
			}

		}
	}
}
