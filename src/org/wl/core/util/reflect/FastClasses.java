package org.wl.core.util.reflect;

import java.beans.BeanInfo;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.reflect.FastClass;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.wl.core.util.common.ObjectUtil;
import org.wl.core.util.regexp.RegexpUtil;

@SuppressWarnings("unchecked")
public class FastClasses<T> implements IClass<T> {
	private static final Log logger = LogFactory.getLog(FastClasses.class);
	private FastClass classes;
	private BeanInfo beanInfo;
	
	private Map<String,Property>  propertys = new HashMap<String, Property>();
	private Map<String,MethodProxy> methodProxys = new HashMap<String,MethodProxy>();
	private Map<Class<?>,Constructor<?>> constructors = new HashMap<Class<?>,Constructor<?>>();
	private Map<String,Field> fields = new HashMap<String,Field>();
	
	public FastClasses(Class<?> classes) {
		this.classes = FastClass.create(classes);
		if(!classes.isInterface()){
			this.beanInfo = ClassUtil.getBeanInfo(classes);
			PropertyDescriptor[] propertyDescriptors = this.beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				MethodProxy readMethodProxy = descriptor.getReadMethod() == null ? null : new MethodProxy(this.classes.getMethod(descriptor.getReadMethod()));
				MethodProxy writeMethodProxy = descriptor.getWriteMethod() == null ? null : new MethodProxy(this.classes.getMethod(descriptor.getWriteMethod()),descriptor.getPropertyType());
				this.propertys.put(descriptor.getName(),new Property(descriptor.getName(),readMethodProxy,writeMethodProxy,descriptor.getPropertyType()));
			}
			MethodDescriptor[] methodDescriptors = this.beanInfo.getMethodDescriptors();
			for (MethodDescriptor methodDescriptor : methodDescriptors){			
				String name = methodDescriptor.getName();
				Method method  = methodDescriptor.getMethod();
				Class<?>[] parameters = method.getParameterTypes();
				for(int i=0;i<parameters.length;i++){
					Class<?> parameterType = parameters[i];
					name += ( i==0 ? "(" : "" ) + parameterType.getName() + ( i+1 == parameters.length ? ")" : "," );
				}
				methodProxys.put(name,new MethodProxy(this.classes.getMethod(method),parameters));
			}
			Constructor<?>[] constructors = classes.getConstructors();
			for(Constructor<?> constructor : constructors){
				Class<?>[] parameterTypes = constructor.getParameterTypes();
				if(parameterTypes.length == 1){
					this.constructors.put(parameterTypes[0],constructor);
				}else{
					//TODO 多个构造类型的构造方法怎么匹配
				}
			}
		}else{
			Method[] methods  = classes.getDeclaredMethods();
			for(Method method : methods){
				String name = method.getName();
				Class<?>[] parameters = method.getParameterTypes();
				for(int i=0;i<parameters.length;i++){
					Class<?> parameterType = parameters[i];
					name += ( i==0 ? "(" : "" ) + parameterType.getName() + ( i+1 == parameters.length ? ")" : "," );
				}
				methodProxys.put(name,new MethodProxy(this.classes.getMethod(method),parameters));
			}
		}
	}

	public T newInstance() {
		try {
			return (T) classes.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}	

	public T newInstance(Object object) {
		try {
			if(object == null)
				return newInstance();
			return newInstance(object.getClass(),object) ;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public T newInstance(Class<?> type,Object object) {
		try {
			return (T) constructors.get(type).newInstance(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Property getProperty(String name) {
		if(this.propertys.containsKey(name)){
			return this.propertys.get(name);
		}
		return null;
	}

	public Property[] getPropertys() {		
		return this.propertys.values().toArray(new Property[this.propertys.size()]);
	}

	public MethodProxy getMethod(String methodName) {
		MethodProxy methodProxy = this.methodProxys.get(methodName);
		if(ObjectUtil.isNotNull(methodProxy))
			return methodProxy;
		for(Map.Entry<String,MethodProxy> entry : this.methodProxys.entrySet()){
			if(RegexpUtil.replace(entry.getKey(), "\\([\\S\\s]+\\)", "").equals(methodName)){
				return entry.getValue();
			}
		}
		return null;
	}

	public MethodProxy getMethod(String methodName, Class<?>... parameterTypes) {
		for(int i=0;i<parameterTypes.length;i++){
			methodName += ( i==0 ? "(" : "" ) + parameterTypes[i].getName() + ( i+1 == parameterTypes.length ? ")" : "," );
		}
		return this.methodProxys.get(methodName);
	}

	public void setValue(Object target, String name, Object value) {
		Field field = null;
		if(!this.fields.containsKey(name)){
			try {
				field = ClassUtil.getDeclaredField(this.classes.getJavaClass(),name);
				if(!field.isAccessible()){
					field.setAccessible(true);
				}
				this.fields.put(name,field);
			} catch (Exception e) {
				logger.error(e);
				this.fields.put(name,field);
			}
		}else{
			field = this.fields.get(name);
		}
		try {
			if(field != null){
				field.set(target , value);
			}else{
				throw new Exception("没有找到["+this.classes.getName()+"."+name+"]对应的属性!");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public Object getValue(Object target, String name) {
		Field field = null;
		if(!this.fields.containsKey(name)){
			try {
				field = ClassUtil.getDeclaredField(this.classes.getJavaClass(),name);
				if(!field.isAccessible()){
					field.setAccessible(true);
				}
				this.fields.put(name,field);
			} catch (Exception e) {
				logger.error(e);
				this.fields.put(name,field);
			}
		}else{
			field = this.fields.get(name);
		}
		try{
			return field.get(target);
		}catch(Exception ex){
			throw new RuntimeException("没有找到["+this.classes.getName()+"."+name+"]对应的属性!",ex);
		}
	}
	
	    public T newInstance(Class<?>[] parameterTypes, Object[] parameters) {
		if (parameterTypes.length == 0)
		    return newInstance();
		if (parameterTypes.length == 1) {
		    return newInstance(parameterTypes[0], parameters[0]);
		}
		throw new RuntimeException("还不支持多个参数的构造器");
	    }
}