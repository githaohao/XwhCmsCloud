package com.xwh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwh.system.entity.SysRoleResource;

/**
 * @author xwh
 **/

public interface SysRoleResourceService extends IService<SysRoleResource> {

    public int checkAuthByUserId(String userId, String type, String path);


    /**
     * 保存角色的授权
     * @param roleId
     * @param resourceIds
     */
    public void saveUpdateRoleResource(String roleId, String[] resourceIds);
}
