package com.xwh.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwh.system.entity.SysResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface SysResourceMapper extends BaseMapper<SysResource> {


    /**
     * 获取所有重复的接口
     * @return
     */
    @Select("select * ,count(*) as c from sys_resource group by path,type having c>1")
    List<SysResource> groupByResource();

    /**
     *  通过 controller 分组显示所有的 service 和 controller
     * @return
     */
    @Select("SELECT service_desc,controller_description,controller,service FROM sys_resource WHERE controller IS NOT NULL GROUP BY SUBSTRING_INDEX(controller_description,',',-1)")
    List<SysResource> listByGroupController();

    /**
     *  通过角色id查询所有的接口授权id
     * @param roleId
     * @return
     */

    @Select("SELECT sr.resource_id FROM `sys_role_resource` t left join sys_resource sr ON t.resource_id = sr.resource_id  WHERE role_id = #{roleId}")
    Set<String> resourceIdsByRoleId(String roleId);
}
