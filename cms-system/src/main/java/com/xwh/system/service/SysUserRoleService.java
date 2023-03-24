package com.xwh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwh.system.entity.SysRole;
import com.xwh.system.entity.SysUserRole;

import java.util.List;

/**
 * @author xiangwenhao
 * @create 2022-02-09 22:57
 **/
public interface SysUserRoleService extends IService<SysUserRole> {

    public List<SysRole> listByUserId(String userId, String roleId);
}
