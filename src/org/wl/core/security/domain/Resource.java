package org.wl.core.security.domain;

import org.apache.ibatis.type.Alias;


/**
 * sys_resource
 * 
 * @author alpha zhang
 */
@Alias("Resource")
public class Resource implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * rid
	 */
    private Integer rid;
	/**
	 * code
	 */
    private String code;
	/**
	 * name
	 */
    private String name;
	/**
	 * url
	 */
    private String url;
	/**
	 * type
	 */
    private Integer type;
	/**
	 * priority
	 */
    private Integer priority;
	/**
	 * memo
	 */
    private String memo;
    
    public Resource(){
    }
    
	/**
	 * 设置  rid
	 * @param rid
	 */
    public void setRid(Integer rid){
    	this.rid = rid;
    }
    
	/**
	 * 获取  rid
	 * @return java.lang.Integer
	 */
    public Integer getRid(){
    	return this.rid;
    }
    
	/**
	 * 设置  code
	 * @param code
	 */
    public void setCode(String code){
    	this.code = code;
    }
    
	/**
	 * 获取  code
	 * @return java.lang.String
	 */
    public String getCode(){
    	return this.code;
    }
    
	/**
	 * 设置  name
	 * @param name
	 */
    public void setName(String name){
    	this.name = name;
    }
    
	/**
	 * 获取  name
	 * @return java.lang.String
	 */
    public String getName(){
    	return this.name;
    }
    
	/**
	 * 设置  url
	 * @param url
	 */
    public void setUrl(String url){
    	this.url = url;
    }
    
	/**
	 * 获取  url
	 * @return java.lang.String
	 */
    public String getUrl(){
    	return this.url;
    }
    
	/**
	 * 设置  type
	 * @param type
	 */
    public void setType(Integer type){
    	this.type = type;
    }
    
	/**
	 * 获取  type
	 * @return java.lang.Integer
	 */
    public Integer getType(){
    	return this.type;
    }
    
	/**
	 * 设置  priority
	 * @param priority
	 */
    public void setPriority(Integer priority){
    	this.priority = priority;
    }
    
	/**
	 * 获取  priority
	 * @return java.lang.Integer
	 */
    public Integer getPriority(){
    	return this.priority;
    }
    
	/**
	 * 设置  memo
	 * @param memo
	 */
    public void setMemo(String memo){
    	this.memo = memo;
    }
    
	/**
	 * 获取  memo
	 * @return java.lang.String
	 */
    public String getMemo(){
    	return this.memo;
    }
    
}