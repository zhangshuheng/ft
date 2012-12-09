package org.wl.core.util.reflect;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class JavaClassFactory implements IClassFactory {
	
	private Map<String,JavaClasses> classes = new HashMap<String,JavaClasses>();

	public <T> IClass<T> getClass(Class<T> cla) {
		synchronized (classes) {
			if (!classes.containsKey(cla.getName())) {
				classes.put(cla.getName(), new JavaClasses(cla));
			}
			return (IClass) classes.get(cla.getName());
		}
	}

}