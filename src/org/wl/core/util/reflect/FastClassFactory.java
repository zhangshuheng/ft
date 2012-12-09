package org.wl.core.util.reflect;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class FastClassFactory implements IClassFactory {
	
	private Map<String,FastClasses> classes = new HashMap<String,FastClasses>();

	public <T> IClass<T> getClass(Class<T> cla) {
		synchronized (classes) {
			if (!classes.containsKey(cla.getName())) {
				classes.put(cla.getName(), new FastClasses(cla));
			}
			return (IClass) classes.get(cla.getName());
		}
	}
}
