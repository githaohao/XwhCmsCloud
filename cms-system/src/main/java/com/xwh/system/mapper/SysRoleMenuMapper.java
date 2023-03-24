package com.xwh.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwh.system.entity.SysMenu;
import com.xwh.system.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 系统_角色资源Mapper接口
 * @date: 2019年12月19 15:26:08
 * @version: 1.0
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
	List<SysMenu> listByRoleId(@Param("roleIds") String roleIds, @Param("menuType") String menuType);

}
