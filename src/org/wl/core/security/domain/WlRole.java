package org.wl.core.security.domain;

import org.apache.ibatis.type.Alias;


/**
 * sys_role
 * 
 * @author alpha zhang
 */
@Alias("FtRole")
public class WlRole implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * roleid
	 */
    private Integer roleid;
	/**
	 * name
	 */
    private String name;
	/**
	 * enable
	 */
    private Boolean enable;
    
    public WlRole(){
    }
    
	/**
	 * 设置  roleid
	 * @param roleid
	 */
    public void setRoleid(Integer roleid){
    	this.roleid = roleid;
    }
    
	/**
	 * 获取  roleid
	 * @return java.lang.Integer
	 */
    public Integer getRoleid(){
    	return this.roleid;
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
	 * 设置  enable
	 * @param enable
	 */
    public void setEnable(Boolean enable){
    	this.enable = enable;
    }
    
	/**
	 * 获取  enable
	 * @return java.lang.Boolean
	 */
    public Boolean getEnable(){
    	return this.enable;
    }
    
}