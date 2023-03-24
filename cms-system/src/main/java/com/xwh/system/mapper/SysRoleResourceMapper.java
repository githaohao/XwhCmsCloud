package com.xwh.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwh.system.entity.SysRoleResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author xwh
 **/

@Mapper
public interface SysRoleResourceMapper extends BaseMapper<SysRoleResource> {

    @Select("select count(*) from sys_role_resource srr left join sys_resource sr on srr.resource_id = sr.resource_id  where #{path} like sr.path and sr.type = #{type} and sr.status = 1 and srr.role_id = #{roleId}")
    int checkResource(String roleId, String path, String type);
}
