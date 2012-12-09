package org.wl.core.util.reflect;

public class ClassFactory{

    public static IClassFactory getJavaClassFactory() {
        return new JavaClassFactory();
    }
    
    public static IClassFactory getFastClassFactory() {
        return new FastClassFactory();
    }     
}
