package org.wl.core.util.reflect;

public interface IClassFactory{
	
    public <T> IClass<T> getClass(Class<T> classes);
}