package com.xwh.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.core.utils.BlankUtils;
import com.xwh.core.utils.RedisUtils;
import com.xwh.core.utils.SecurityUtils;
import com.xwh.system.entity.SysMenu;
import com.xwh.system.entity.SysRole;
import com.xwh.system.entity.SysUser;
import com.xwh.system.entity.SysUserRole;
import com.xwh.system.mapper.SysUserMapper;
import com.xwh.system.mapper.SysUserRoleMapper;
import com.xwh.system.service.SysMenuService;
import com.xwh.system.service.SysRoleMenuService;
import com.xwh.system.service.SysUserRoleService;
import com.xwh.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiangwenhao
 * @create 2022-02-09 22:39
 **/
@Service
@RequiredArgsConstructor
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    final SysUserMapper sysUserMapper;
    final SysUserRoleMapper sysUserRoleMapper;

    final SysUserRoleService sysUserRoleService;
    final SysRoleMenuService sysRoleMenuService;
    final SysMenuService sysMenuService;
    final RedisUtils redis;


    /**
     * @param @param sysUser
     * @return void
     * @throws
     * @Title：saveUserInfo
     * @Description: 保存用户信息
     * @date 2019年12月26日 下午4:51:51
     */
    public void saveUserInfo(SysUser sysUser) {
        String roleId = String.valueOf(sysUser.getRoleId());
        // 如果角色id不为空进行用户角色表的添加操作
        if (BlankUtils.isNotBlank(roleId)) {
            saveUserRoleInfo(sysUser.getUserId(), roleId);
        }
        save(sysUser);
    }

    /**
     * 更新用户的角色列表
     */
    public void saveUserRoleInfo(String userId, String roleId) {
        // 将字符串转换为 list
        List<String> roles = Arrays.asList(roleId.substring(1, roleId.length() - 1).split(","));
        SysUserRole userRole = new SysUserRole();
        for (String ra : roles) {
            userRole.setRoleId(ra);
            userRole.setUserId(userId);
            sysUserRoleService.save(userRole);
        }
        //批量插入
    }

    // 登录获取用户信息
    public SysUser login(String username, String password) {
        SysUser sysUser = null;
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            // 根据用户名和密码查询
            sysUser = getByUsernameAndPassword(username, password);
            // 根据邮箱和密码查询
            if (ObjectUtils.isEmpty(sysUser)) {
                sysUser = getByEmailAndPassword(username, password);
            }
            // 根据手机号和密码查询
            if (ObjectUtil.isEmpty(sysUser)) {
                sysUser = getByMobileAndPassword(username, password);
            }
        }
        return sysUser;
    }


    /**
     * @param @param  username
     * @param @param  password
     * @param @param  menuPerms
     * @param @return
     * @return Map<String, Object>
     * @throws
     * @Title：getLoginDatas
     * @Description: 获取登录后的信息
     * @author: xiangwenhao
     * @date 2020年01月20日 下午2:06:23
     */
    public Map<String, Object> getLoginDatas(String username, boolean menuPerms) {
        SysUser sysUser = this.selectByUsername(username);
        // 返回信息
        Map<String, Object> rtDatas = new HashMap<>();
        // 用户信息
        Map<String, Object> userData  = BeanUtil.beanToMap(sysUser);
        // 获得角色和角色权限
        List<SysRole> sysRoleList = sysUserRoleService.listByUserId(sysUser.getUserId(), null);
        if (BlankUtils.isNotBlank(sysRoleList) || (sysUser.getIsAdmin() != null && sysUser.getIsAdmin() == 1)) {
            int roleSize = sysRoleList.size();
            List<String> roleIds = new ArrayList<>(roleSize);
            List<String> roleNames = menuPerms ? new ArrayList<>(roleSize) : null;
            for (SysRole r : sysRoleList) {
                if (BlankUtils.isNotBlank(r)) {
                    roleIds.add(r.getRoleId());
                    if (menuPerms) {
                        roleNames.add(r.getName());
                    }
                }
            }

            // 是否获取菜单权限
            if (menuPerms) {
                userData.put("roleName", roleNames.toString());
                // 获取当前权限的所有菜单
                String[] roleids = new String[roleIds.size()];
                roleids=roleIds.toArray(roleids);
                List<SysMenu> sysMenus = sysRoleMenuService.menuByRoleId(sysUser, roleids);
                // 去除 getStatus 状态为关闭的
                sysMenus = sysMenus.stream().filter(SysMenu::getStatus).collect(Collectors.toList());
                //转换
                List<Tree<String>> trees = sysMenuService.buildTreeData(sysMenus);
                rtDatas.put("menuData", trees);
                // 登录后把按钮权限标识保存到redis缓存中
//                redis.set("sys:permissions:" + sysUser.getUserId(), roleMenuPerms.getPermsList(), 0);
            }
        }
        rtDatas.put("userData", userData);
        // 更新登录成功次数
        this.updateLoginResult(sysUser.getUsername(), true);
        return rtDatas;
    }

    /**
     * @param @param username
     * @param @param bool
     * @return void
     * @throws
     * @Title：updateLoginResult
     * @Description: 根据登录结果更新登录次数时间信息
     * @author: xiangwenhao
     */
    public void updateLoginResult(String username, Boolean bool) {
        sysUserMapper.updateLoginResult(username, bool);
    }


    public SysUser selectByUsername(String username) {
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.eq("username",username);
        return getOne(query);
    }

    /**
     * 根据username和password查询
     *
     * @param username
     * @param password
     * @return
     */
    public SysUser getByUsernameAndPassword(String username, String password) {
        SysUser user = new SysUser();
        user.setPassword(SecurityUtils.MD5Encode(password));
        user.setUsername(username);
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.setEntity(user);
        return getOne(query);
    }

    /**
     * 根据email和password查询
     *
     * @param email
     * @param password
     * @return
     */
    public SysUser getByEmailAndPassword(String email, String password) {
        SysUser user = new SysUser();
        user.setEmail(email);
        user.setPassword(SecurityUtils.MD5Encode(password));
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.setEntity(user);
        return getOne(query);
    }

    /**
     * 根据mobile和password查询
     *
     * @param mobile
     * @param password
     * @return
     */
    public SysUser getByMobileAndPassword(String mobile, String password) {
        SysUser user = new SysUser();
        user.setMobile(mobile);
        user.setPassword(SecurityUtils.MD5Encode(password));
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.setEntity(user);
        return getOne(query);
    }

    /**
     * 修改用户 密码
     *
     * @param user
     * @param newpwd
     */
    public void updatePwd(SysUser user, String newpwd) {
        user.setUpdateBy(String.valueOf(user.getUserId()));
        user.setPassword(SecurityUtils.MD5Encode(newpwd));
        updateById(user);
    }

    /**
     * 通过 id 删除用户相关的
     *
     * @param split
     */
    public void delByIds(String[] split) {
        for (String s : split) {
            removeById(s);
            sysUserRoleService.removeByMap(Map.of("user_id", s));
        }
    }

}
