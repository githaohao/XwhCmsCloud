package com.xwh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.core.exception.FailException;
import com.xwh.system.entity.SysRole;
import com.xwh.system.mapper.SysRoleMapper;
import com.xwh.system.service.SysRoleService;
import com.xwh.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author xwh
 **/
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {

    final SysUserRoleService sysUserRoleService;

    /**
     * 通过角色id 删除角色
     * @param id
     */
    public void delete(String id) {
        //判断该角色下是否存在用户
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        long role_id = count(query.eq("role_id", id));
        if (role_id > 0){
            throw new FailException("当前角色存在用户,无法删除");
        }else {
            removeById(id);
        }

    }
}
