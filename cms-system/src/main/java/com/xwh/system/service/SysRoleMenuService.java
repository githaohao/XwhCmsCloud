package com.xwh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwh.system.entity.SysMenu;
import com.xwh.system.entity.SysRoleMenu;
import com.xwh.system.entity.SysUser;

import java.util.List;

/**
 * @author xiangwenhao
 * @create 2022-02-09 23:10
 **/
public interface SysRoleMenuService extends IService<SysRoleMenu> {


    /**
     * @param @param  roleIdsroleIds
     * @param @return
     * @return String
     * @throws
     * @Title：coverRoleIds
     * @Description: roleids 拼接成字符串 ‘id1’，‘id2’的格式
     * @date 2020年01月26日 下午5:40:00
     */
    public String coverRoleIds(String[] roleIdsroleIds);


    /**
     * 据角色id获得权限集合和菜单列表
     * @param sysUser
     * @param roleIds
     * @return
     */
    public List<SysMenu> menuByRoleId(SysUser sysUser, String[] roleIds);


    /**
     *  获取当前角色的菜单
     * @param roleIds
     * @param menuType
     * @return
     */
    public List<SysMenu> getRoleMenuList(String roleIds, String menuType);

}
