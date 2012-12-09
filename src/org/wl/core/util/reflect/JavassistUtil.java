package org.wl.core.util.reflect;

import java.util.concurrent.ConcurrentHashMap;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.SignatureAttribute;

public class JavassistUtil {

	private final static ConcurrentHashMap<String, CtClass> ctClassCache = new ConcurrentHashMap<String, CtClass>();
	
	public static ClassPool getDefault() throws NotFoundException{
		ClassPool classPool = ClassPool.getDefault();
		classPool.appendClassPath(Thread.currentThread().getContextClassLoader().getResource("").getPath());
		return classPool;
	}
	
	public static CtClass getCtClass(String classname) throws NotFoundException{
		synchronized (ctClassCache) {
			if(!ctClassCache.containsKey(classname)){
				try {
					getDefault().insertClassPath(new ClassClassPath(Class.forName(classname)));
				} catch (ClassNotFoundException e) {
					throw new NotFoundException(e.getMessage(),e);
				}
				ctClassCache.put(classname, getDefault().get(classname));
			}
			return ctClassCache.get(classname);
		}
	}

	/**
	 * 获取方法参数名称
	 * @param classname
	 * @param methodname
	 * @param parameterTypes
	 * @return
	 * @throws NotFoundException
	 * @throws MissingLVException
	 */
	public static String[] getParamNames(String classname,String methodname,Class<?>... parameterTypes) throws NotFoundException, MissingLVException{
		String[] paramTypeNames = new String[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++)
			paramTypeNames[i] = parameterTypes[i].getName();
		 for(CtMethod cm : getCtClass(classname).getDeclaredMethods()){
			 if(cm.getMethodInfo().getAttributes().size() > 0){
				Object obj = cm.getMethodInfo().getCodeAttribute();
				System.out.println("CodeAttribute:"+obj);
				if(obj instanceof SignatureAttribute){
					 System.out.println(">"+((SignatureAttribute)obj).getSignature());
				}			
			 }
		 }
		CtMethod cm = getCtClass(classname).getDeclaredMethod(methodname,getDefault().get(paramTypeNames));
		return getParamNames(cm);
	}
	
	/**
	 * 获取方法参数名称
	 * @param cm
	 * @return
	 * @throws NotFoundException
	 * @throws MissingLVException
	 *             如果最终编译的class文件不包含局部变量表信息
	 */
	protected static String[] getParamNames(CtMethod cm) throws NotFoundException, MissingLVException {
		CtClass cc = cm.getDeclaringClass();
		MethodInfo methodInfo = cm.getMethodInfo();		
		
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
		if (attr == null)
			throw new MissingLVException(cc.getName());
		String[] paramNames = new String[cm.getParameterTypes().length];
		int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
		for (int i = 0; i < paramNames.length; i++)
			paramNames[i] = attr.variableName(i + pos);
		return paramNames;
	}
	
	/**
	 * 在class中未找到局部变量表信息<br>
	 * 使用编译器选项 javac -g:{vars}来编译源文件
	 * 
	 * @author Administrator
	 * 
	 */
	@SuppressWarnings("serial")
	public static class MissingLVException extends Exception {
		static String msg = "class:%s 不包含局部变量表信息，请使用编译器选项 javac -g:{vars}来编译源文件。";

		public MissingLVException(String clazzName) {
			super(String.format(msg, clazzName));
		}
	}
	
}
