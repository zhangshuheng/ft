package org.wl.core.security.domain;

public class WlUser {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.userid
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    private Integer userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.name
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.password
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.enable
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    private Boolean enable;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.account
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    private String account;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.userid
     *
     * @return the value of sys_user.userid
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.userid
     *
     * @param userid the value for sys_user.userid
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.name
     *
     * @return the value of sys_user.name
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.name
     *
     * @param name the value for sys_user.name
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.password
     *
     * @return the value of sys_user.password
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.password
     *
     * @param password the value for sys_user.password
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.enable
     *
     * @return the value of sys_user.enable
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.enable
     *
     * @param enable the value for sys_user.enable
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.account
     *
     * @return the value of sys_user.account
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.account
     *
     * @param account the value for sys_user.account
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }
}