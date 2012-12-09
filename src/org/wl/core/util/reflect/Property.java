package org.wl.core.util.reflect;

public class Property {
	private String name;
	private MethodProxy readMethodProxy;
	private MethodProxy writeMethodProxy;
	private Class<?> propertyType;
	private boolean write;
	private boolean read;

	Property(String name, MethodProxy readMethodProxy,MethodProxy writeMethodProxy, Class<?> propertyType) {
		read = readMethodProxy != null;
		write = writeMethodProxy != null;
		this.name = name;
		this.readMethodProxy = readMethodProxy;
		this.writeMethodProxy = writeMethodProxy;
		this.propertyType = propertyType;
	}

	public boolean isWrite() {
		return write;
	}

	public boolean isRead() {
		return read;
	}

	public Object getValue(Object target) {
		return readMethodProxy.invoke(target);
	}

	public void setValue(Object target, Object value) {
		writeMethodProxy.invoke(target, value);
	}

	public Class<?> getPropertyType() {
		return propertyType;
	}

	public String getName() {
		return name;
	}

	public MethodProxy getReadMethod() {
		return readMethodProxy;
	}

	public MethodProxy getWriteMethod() {
		return writeMethodProxy;
	}

}
