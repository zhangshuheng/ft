package org.wl.core.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.reflect.FastMethod;

import org.wl.core.util.reflect.JavassistUtil;

/**
 * 
 */
public class MethodProxy {
	private Object method;
	private Class<?>[] parameterTypes;
	private Class<?> returnType;

	public MethodProxy(Object method) {
		this.method = method;
		if (method instanceof FastMethod) {
			this.parameterTypes = ((FastMethod) method).getParameterTypes();
			this.returnType = ((FastMethod) method).getReturnType();
		} else {
			this.parameterTypes = ((Method) method).getParameterTypes();
			this.returnType = ((Method) method).getReturnType();
		}
	}
	
	public MethodProxy(Object method, Class<?>[] parameterTypes) {
		this(method);
		this.parameterTypes = parameterTypes;
	}

	public MethodProxy(Object method, Class<?> parameterType) {
		this(method);
		if(parameterType != null)
			this.parameterTypes = new Class[]{parameterType};
	}

	public Object invoke(Object object){
		try {
			if (method instanceof FastMethod) {
				return ((FastMethod) method).invoke(object, null);
			} else {
				return ((Method) method).invoke(object);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			e.getTargetException();
			return null;
		}
	}

	public Object invoke(Object object, Object... params) {
		try {
			if (method instanceof FastMethod) {
				if (params.length > 0) {
					return ((FastMethod) method).invoke(object,params);
				} else {
					return ((FastMethod) method).invoke(object,new Object[]{params});
				}
			} else {
				if (params.length > 0) {
					return ((Method) method).invoke(object,params);
				} else {
					return ((Method) method).invoke(object,new Object[]{params});
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static MethodProxy create(Object method) {
		if (method == null)
			return null;
		return new MethodProxy(method);
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	@Override
	public String toString() {
		return "MethodProxy ["+this.method.toString()+"]";
	}

	public Method getMethod(){
		return this.method instanceof FastMethod ? ((FastMethod)this.method).getJavaMethod(): (Method)this.method;
	}
	
	public String[] getParamNames(){
		try {
		if (this.method instanceof FastMethod) {
			Class<?> declaringClass = ((FastMethod) this.method).getDeclaringClass();
			FastMethod method = (FastMethod)this.method;
			return JavassistUtil.getParamNames(declaringClass.getName(),method.getName(),parameterTypes);
		}else{
			Class<?> declaringClass = ((Method) this.method).getDeclaringClass();
			Method method = (Method)this.method;
			return JavassistUtil.getParamNames(declaringClass.getName(),method.getName(),parameterTypes);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}