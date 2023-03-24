package com.xwh.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.system.entity.SysRole;
import com.xwh.system.entity.SysRoleResource;
import com.xwh.system.mapper.SysRoleResourceMapper;
import com.xwh.system.service.SysRoleResourceService;
import com.xwh.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xwh
 **/

@Service
@RequiredArgsConstructor
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource> implements SysRoleResourceService {

    final SysRoleResourceMapper sysRoleResourceMapper;
    final SysUserRoleService sysUserRoleService;

    public int checkAuthByUserId(String userId, String type, String path) {
        // 查询用户角色
        int count = 0;
        List<SysRole> sysRoles = sysUserRoleService.listByUserId(userId, null);
        for (SysRole sysRole : sysRoles) {
            // 通过用户角色查询当前的 url授权
            String roleId = sysRole.getRoleId();
            count = sysRoleResourceMapper.checkResource(roleId,path,type);
            System.out.println(count);
            if (count > 0){
                return count;
            }
        }
        return count;
    }


    /**
     * 保存角色的授权
     * @param roleId
     * @param resourceIds
     */
    public void saveUpdateRoleResource(String roleId, String[] resourceIds) {
        // 先删除该角色的所有授权
        boolean role = removeByMap(Map.of("role_id", roleId));
        if (role){
            for (String s : resourceIds) {
                SysRoleResource sysRoleResource = new SysRoleResource();
                sysRoleResource.setRoleId(roleId);
                sysRoleResource.setResourceId(s);
                save(sysRoleResource);
            }
        }

    }
}
