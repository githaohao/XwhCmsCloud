package com.xwh.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.system.entity.SysRole;
import com.xwh.system.entity.SysUserRole;
import com.xwh.system.mapper.SysUserRoleMapper;
import com.xwh.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiangwenhao
 * @create 2022-02-09 22:57
 **/
@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    final SysUserRoleMapper sysUserRoleMapper;

    public List<SysRole> listByUserId(String userId, String roleId) {
        return sysUserRoleMapper.listByUserId(userId,roleId);
    }
}
