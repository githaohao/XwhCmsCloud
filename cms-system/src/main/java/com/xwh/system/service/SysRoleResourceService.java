package com.xwh.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xwh.system.entity.SysRoleResource;

/**
 * @author xwh
 **/

public interface SysRoleResourceService extends IService<SysRoleResource> {

    /**
     * 通过用户id查询是否有权限
     * @param userId
     * @param type
     * @param path
     * @return
     */
    public int checkAuthByUserId(String userId, String type, String path);


    /**
     * 保存角色的授权
     * @param roleId
     * @param resourceIds
     */
    public void saveUpdateRoleResource(String roleId, String[] resourceIds);

    /**
     * 删除角色的授权
     * @param query
     * @param type
     * @param path
     */
    void delete(QueryWrapper<SysRoleResource> query, String type, String path);

}
