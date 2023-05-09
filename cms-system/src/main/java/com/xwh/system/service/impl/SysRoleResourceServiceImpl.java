package com.xwh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.core.utils.RedisUtils;
import com.xwh.system.entity.SysRole;
import com.xwh.system.entity.SysRoleResource;
import com.xwh.system.mapper.SysRoleResourceMapper;
import com.xwh.system.service.SysRoleResourceService;
import com.xwh.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xwh
 **/

@Service
@RequiredArgsConstructor
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource> implements SysRoleResourceService {

    final SysRoleResourceMapper sysRoleResourceMapper;
    final SysUserRoleService sysUserRoleService;
    final RedisUtils redisUtils;

    public int checkAuthByUserId(String userId, String type, String path) {
        String cacheKey = "auth:" + userId + ":" + type + ":" + path;
        Integer count = (Integer) redisUtils.get(cacheKey);
        if (count != null) {
            return count;
        }
        int result = 0;
        List<SysRole> sysRoles = sysUserRoleService.listByUserId(userId, null);
        for (SysRole sysRole : sysRoles) {
            String roleId = sysRole.getRoleId();
            result = sysRoleResourceMapper.checkResource(roleId, path, type);
            if (result > 0) {
                break;
            }
        }
        redisUtils.set(cacheKey, result,7, TimeUnit.DAYS);
        return result;
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

    /**
     * 删除角色的授权
     * @param query
     * @param type
     * @param path
     */
    @Override
    public void delete(QueryWrapper<SysRoleResource> query, String type, String path) {
        // 模糊删除 redis 缓存
        redisUtils.deleteByPattern("auth:*:" + type + ":" + path);
        // 删除角色的授权
        remove(query);
    }
}
