package com.xwh.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xwh.core.controller.BaseController;
import com.xwh.core.dao.Page;
import com.xwh.core.dto.Result;
import com.xwh.core.utils.BlankUtils;
import com.xwh.core.utils.SecurityUtils;
import com.xwh.core.utils.TokenUtil;
import com.xwh.system.entity.SysUser;
import com.xwh.system.service.SysUserRoleService;
import com.xwh.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author xiangwenhao
 * @create 2022-02-10 10:15
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "系统:用户管理")
@Slf4j
public class UserController extends BaseController {

    final SysUserService sysUserService;
    final SysUserRoleService sysUserRoleService;


    @ApiOperation(value = "分页模糊查询所有用户")
    @PostMapping("/search/{index}/{size}")
    public Result search(@PathVariable Long index, @PathVariable Long size, @RequestBody SysUser sysUser) {
        Page<SysUser> userPage = new Page<>(index, size);
        QueryWrapper<SysUser> query = new QueryWrapper<SysUser>();
        query.orderByDesc("update_time");
        Page<SysUser> page = sysUserService.page(userPage, query);
        return success().add(propertyDel(page, "password"));
    }


    @ApiOperation(value = "通过 id 删除用户")
    @DeleteMapping()
    public Result del(@RequestBody String[] ids) {
        sysUserService.delByIds(ids);
        return success();
    }

    /**
     * 创建系统_用户
     */
    @PostMapping()
    @ApiOperation(value = "创建用户")
    public Result add(@RequestBody JSONObject data) {
        SysUser sysUser = JSON.toJavaObject(data, SysUser.class);
        try {
        // 是否锁定，添加锁定时间
        //            if ("1".equals(sysUser.getIsLock())) {
        //                sysUser.setLockTime(new Date());
        //            }
            // 判断是否为管理员 如果是超级管理员的话需要手动添加租户id
            String isAdmin = TokenUtil.getUserIsAdmin();
            if (StringUtils.isNotBlank(isAdmin) && "1".equals(isAdmin)) {
                return fail("管理员添加用户需要指定租户ID");
            }
            sysUser.setPassword(SecurityUtils.MD5Encode(sysUser.getPassword()));
            sysUser.setCreateBy(String.valueOf(TokenUtil.getUserId()));
            sysUserService.saveUserInfo(sysUser);
        } catch (Exception e) {
            log.error("创建系统_用户异常", e);
            return fail("创建系统_用户异常！");
        }
        return success().add(sysUser);
    }

    /**
     * 修改系统_用户
     */
    @PutMapping()
    @ApiOperation(value = "修改用户")
    public Result update(@RequestBody JSONObject data) {
        SysUser sysUser = JSON.toJavaObject(data, SysUser.class);
        if (StringUtils.isNotBlank(sysUser.getPassword())) {
            sysUser.setPassword(SecurityUtils.MD5Encode(sysUser.getPassword()));
        }
        sysUser.setUpdateBy(String.valueOf(TokenUtil.getUserId()));
        //先删除角色信息再保存信息
        sysUserService.updateById(sysUser);
        if (sysUser.getRoleId() != null) {
            sysUserRoleService. removeByMap(Map.of("user_id", sysUser.getUserId()));
            sysUserService.saveUserRoleInfo(sysUser.getUserId(), sysUser.getRoleId());
        }
        return success();
    }


    /**
     * @param @param  pwd
     * @param @param  newpwd
     * @param @param  newpwd2
     * @param @return
     * @Title：updatePwd
     * @Description: 修改密码
     * @author: xiangwenhao
     * @date 2020年1月16日 上午10:52:49
     */
    @PutMapping("/updatePwd")
    @ApiOperation(value = "用户修改密码")
    public Result updatePwd(@RequestBody JSONObject data) {
        String pwd = data.getString("pwd");
        String newpwd = data.getString("newpwd");
        String newpwd2 = data.getString("newpwd2");
        try {
            if (BlankUtils.isBlank(pwd)) {
                return fail("系统_用户修改密码异常！原密码不能为空");
            }
            if (BlankUtils.isBlank(newpwd)) {
                return fail("系统_用户修改密码异常！新密码不能为空");
            }
            if (BlankUtils.isBlank(newpwd2)) {
                return fail("系统_用户修改密码异常！重复新密码不能为空");
            }
            if (!newpwd.equals(newpwd2)) {
                return fail("系统_用户修改密码异常！新密码和重复新密码必须一致");
            }
            String userId = TokenUtil.getUserId();
            SysUser sysUser = sysUserService.getById(userId);
            if (SecurityUtils.MD5Encode(pwd).equals(sysUser.getPassword())) {
                sysUserService.updatePwd(sysUser, newpwd);
                return success();
            } else {
                return fail("系统_用户修改密码异常！原密码不正确");
            }
        } catch (Exception e) {
            log.error("系统_用户重置密码异常！", e);
            return fail("系统_用户重置密码异常！");
        }
    }



    @GetMapping("/{id}")
    @ApiOperation(value = "通过id获取单个用户的信息(密码除外)")
    public Result findById(@PathVariable String id) {
        return success().add(propertyDel(sysUserService.getById(id),"password"));
    }
}
