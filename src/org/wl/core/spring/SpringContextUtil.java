package org.wl.core.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware,DisposableBean{
	
	public static final int AUTOWIRE_NO = 0;
	public static final int AUTOWIRE_BY_NAME = 1;
	public static final int AUTOWIRE_BY_TYPE = 2;
	public static final int AUTOWIRE_CONSTRUCTOR = 3;
	public static final int AUTOWIRE_AUTODETECT = 4;
	
	private static final Log logger = LogFactory.getLog(SpringContextUtil.class);
	
    private static ApplicationContext applicationContext;     //Spring应用上下文环境

    /**
    * 实现ApplicationContextAware接口的回调方法，设置上下文环境
    * @param applicationContext
    * @throws BeansException
    */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	logger.debug(applicationContext);
    	if(SpringContextUtil.applicationContext == null)
    		SpringContextUtil.applicationContext = applicationContext;
    }

    /**
    * @return ApplicationContext
    */
    public static ApplicationContext getApplicationContext() {
      return applicationContext;
    }

    /**
    * 获取对象
    * @param name
    * @return Object 一个以所给名字注册的bean的实例
    * @throws BeansException
    */
    public static Object getBean(String name){
      try{
    	return applicationContext.getBean(name);
      }catch (BeansException e) {
    	  if(logger.isErrorEnabled())
  			logger.error("{Bean:"+name+"}没有找到!", e);
    	  throw e;
      }
    }

    /**
    * 获取类型为requiredType的对象
    * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
    * @param name       bean注册名
    * @param requiredType 返回对象类型
    * @return Object 返回requiredType类型对象
    * @throws BeansException
    */
    public static Object getBean(String name, Class<?> requiredType){
    	try{    		
    		return applicationContext.getBean(name, requiredType);
    	}catch (BeansException e) {
    		if(logger.isErrorEnabled())
    			logger.error("{Bean:"+name+",Class:"+requiredType+"}没有找到!");
    		throw e;
		}catch (NullPointerException e) {
				logger.error("查找Bean:"+name+"时发现applicationContext未启动");
			return null;
		}
    }
    
    public static void autowireBean(Object existingBean){
    	applicationContext.getAutowireCapableBeanFactory().autowireBean(existingBean);
    }
    
    public static Object autowire(Class<?> beanClass,int autoType){
    	return applicationContext.getAutowireCapableBeanFactory().autowire(beanClass,autoType,false);
    }

    /**
    * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
    * @param name
    * @return boolean
    */
    public static boolean containsBean(String name) {
      return applicationContext.containsBean(name);
    }

    /**
    * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
    * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
    * @param name
    * @return boolean
    * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
    */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
      return applicationContext.isSingleton(name);
    }

    /**
    * @param name
    * @return Class 注册对象的类型
    * @throws NoSuchBeanDefinitionException
    */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
      return applicationContext.getType(name);
    }

    /**
    * 如果给定的bean名字在bean定义中有别名，则返回这些别名
    * @param name
    * @return
    * @throws NoSuchBeanDefinitionException
    */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
      return applicationContext.getAliases(name);
    }

	public void destroy() throws Exception {
		cleanApplicationContext();
	}
	
	public static void cleanApplicationContext() {
		applicationContext = null;
	}
	
  }