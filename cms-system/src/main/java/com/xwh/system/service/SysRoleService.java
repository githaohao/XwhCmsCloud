package com.xwh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwh.system.entity.SysRole;

/**
 * @author xwh
 **/
public interface SysRoleService extends IService<SysRole> {

    /**
     * 通过角色id 删除角色
     * @param id
     */
    public void delete(String id);
}
