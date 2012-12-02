package org.wl.core.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wl.core.dao.sqlmapper.SqlMapper;
import org.wl.core.security.domain.WlUser;
import org.wl.core.security.domain.WlUserExample;

@Repository
public interface WlUserMapper extends SqlMapper{
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    int countByExample(WlUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    int deleteByExample(WlUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    int deleteByPrimaryKey(Integer userid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    int insert(WlUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    int insertSelective(WlUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    List<WlUser> selectByExample(WlUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    WlUser selectByPrimaryKey(Integer userid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    int updateByExampleSelective(@Param("record") WlUser record, @Param("example") WlUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    int updateByExample(@Param("record") WlUser record, @Param("example") WlUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    int updateByPrimaryKeySelective(WlUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sun Dec 02 17:24:38 CST 2012
     */
    int updateByPrimaryKey(WlUser record);
}