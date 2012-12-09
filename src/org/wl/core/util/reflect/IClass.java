package org.wl.core.util.reflect;


public interface IClass<T>{
	
	public Property getProperty(String name);
	
	public Property[] getPropertys();
	 
	public T newInstance();
	
	public T newInstance(Object object);
	
	public T newInstance(Class<?> type,Object object);
	
	public MethodProxy getMethod(String methodName);
	
	public MethodProxy getMethod(String methodName, Class<?>... parameterTypes);
	
	public void setValue(Object target, String name, Object value);

	public Object getValue(Object target, String name);
	
	public T newInstance(Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject);
	
	/*
    public Field getField(String fieldName);
    
    public MethodProxy getMethod(Field field, String type);

    public Object newInstance();

    public MethodProxy getMethod(String methodName, Class classType);
    
    *
     * 根据方法名查找方法
     * 		匹配规则：
     * 				1.如果方法名对应的方法只有一个，返回该方法。
     * 				2.如果方法名以set开头,则先取获取get方法,如果存在的话,以get方法的返回的类型作为参数类型继续查找set方法
     * 				3.如果方法名以set方法开头,尝试去获取对应属性的类型,如果存在的话.已属性类型作为参数类型继续查找set方法
     * @param methodName
     * @return
     
    public MethodProxy getMethod(String methodName);

    public void setFieldValue(Object target, String fname, Object fvalue);

    public Field[] getFields();
    
    public Method[] getMethods();
	*/	
}