package org.wl.core.util.reflect;


public class JavaClasses<T> implements IClass<T> {

	public JavaClasses(Class<?> cla) {
	}

	public Property getProperty(String name) {
		return null;
	}

	public Property[] getPropertys() {
		return null;
	}

	public T newInstance() {
		return null;
	}

	public MethodProxy getMethod(String methodName) {
		return null;
	}

	public T newInstance(Object object) {
		return null;
	}

	public T newInstance(Class<?> type, Object object) {
		return null;
	}

	public MethodProxy getMethod(String methodName, Class<?>... parameterTypes) {
		return null;
	}

	public void setValue(Object target, String name, Object value) {
		
	}

	public Object getValue(Object target, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public T newInstance(Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject) {
	    // TODO Auto-generated method stub
	    return null;
	}
	
}