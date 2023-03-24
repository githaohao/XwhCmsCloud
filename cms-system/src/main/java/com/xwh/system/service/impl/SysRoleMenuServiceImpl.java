package com.xwh.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.system.entity.SysMenu;
import com.xwh.system.entity.SysRoleMenu;
import com.xwh.system.entity.SysUser;
import com.xwh.system.mapper.SysMenuMapper;
import com.xwh.system.mapper.SysRoleMenuMapper;
import com.xwh.system.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiangwenhao
 * @create 2022-02-09 23:10
 **/
@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    final SysRoleMenuMapper sysRoleMenuMapper;
    final SysMenuMapper sysMenuMapper;



    /**
     * @param @param  roleIdsroleIds
     * @param @return
     * @return String
     * @throws
     * @Title：coverRoleIds
     * @Description: roleids 拼接成字符串 ‘id1’，‘id2’的格式
     * @date 2020年01月26日 下午5:40:00
     */
    public String coverRoleIds(String[] roleIdsroleIds) {
        StringBuffer sb = new StringBuffer();
        for (String roleId : roleIdsroleIds) {
            sb.append("'").append(roleId).append("',");
        }
        String rt = sb.toString();
        if (rt.contains(",")) {
            rt = rt.substring(0, rt.lastIndexOf(","));
        }
        return rt;
    }


    /**
     * 据角色id获得权限集合和菜单列表
     * @param sysUser
     * @param roleIds
     * @return
     */
    public List<SysMenu> menuByRoleId(SysUser sysUser, String[] roleIds){
        //判断是否是超级管理员
        List<SysMenu> roleMenuList;
        if(sysUser.getIsAdmin() != null && sysUser.getIsAdmin() == 1){
            roleMenuList = sysMenuMapper.selectList(Wrappers.emptyWrapper());
        }else{
            roleMenuList = getRoleMenuList(coverRoleIds(roleIds),null);
        }
        return roleMenuList;
    }


    /**
     *  获取当前角色的菜单
     * @param roleIds
     * @param menuType
     * @return
     */
    public List<SysMenu> getRoleMenuList(String roleIds, String menuType) {
        return sysRoleMenuMapper.listByRoleId(roleIds, menuType);
    }

}
