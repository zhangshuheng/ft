package org.wl.core.util.reflect;

public class Functor {
    private Object source;
    private MethodProxy method;
    
    private Functor(Object object, MethodProxy method){
        this.source = object;
        this.method = method;
    }
    
    public Object call(){
        try {
			return method.invoke(source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public Object call(Object object){
        return method.invoke(source, object);
    }
    
    public static Functor create(Object object,MethodProxy method){
        return new Functor(object,method);
    }
    
}
