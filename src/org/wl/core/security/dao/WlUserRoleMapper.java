package org.wl.core.security.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.wl.core.security.domain.WlUserRoleExample;
import org.wl.core.security.domain.WlUserRoleKey;

public interface WlUserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbggenerated Sun Dec 02 19:40:51 CST 2012
     */
    int countByExample(WlUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbggenerated Sun Dec 02 19:40:51 CST 2012
     */
    int deleteByExample(WlUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbggenerated Sun Dec 02 19:40:51 CST 2012
     */
    int deleteByPrimaryKey(WlUserRoleKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbggenerated Sun Dec 02 19:40:51 CST 2012
     */
    int insert(WlUserRoleKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbggenerated Sun Dec 02 19:40:51 CST 2012
     */
    int insertSelective(WlUserRoleKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbggenerated Sun Dec 02 19:40:51 CST 2012
     */
    List<WlUserRoleKey> selectByExample(WlUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbggenerated Sun Dec 02 19:40:51 CST 2012
     */
    int updateByExampleSelective(@Param("record") WlUserRoleKey record, @Param("example") WlUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbggenerated Sun Dec 02 19:40:51 CST 2012
     */
    int updateByExample(@Param("record") WlUserRoleKey record, @Param("example") WlUserRoleExample example);
}