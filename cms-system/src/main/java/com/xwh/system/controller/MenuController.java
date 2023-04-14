package com.xwh.system.controller;

import cn.hutool.core.lang.tree.Tree;
import com.xwh.core.controller.BaseController;
import com.xwh.core.dto.Result;
import com.xwh.system.entity.SysMenu;
import com.xwh.system.entity.SysUser;
import com.xwh.system.service.SysMenuService;
import com.xwh.system.service.SysRoleMenuService;
import com.xwh.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单管理
 *
 * @author xiangwenhao
 * @create 2022-02-24 14:46
 **/

@RestController
@RequestMapping("/menu")
@Api(tags = "系统:菜单管理")
@RequiredArgsConstructor
public class MenuController extends BaseController {

    final SysMenuService sysMenuService;
    final SysRoleMenuService sysRoleMenuService;
    final SysRoleService sysRoleService;

    @ApiOperation("通过关键字模糊查询折叠菜单列表")
    @PostMapping("/search")
    public Result search(@RequestBody SysMenu menu) {
        List<SysMenu> search = sysMenuService.search(menu);
        List<Tree<String>> trees = sysMenuService.buildTreeData(search);
        return success().add(propertyDel(trees, "meta"));
    }

    @ApiOperation("添加/修改 单个菜单")
    @PostMapping()
    public Result save(@RequestBody SysMenu sysMenu) {
        if (sysMenu.getMenuId() == null) {
            sysMenuService.save(sysMenu);
        } else {
            sysMenuService.updateById(sysMenu);
        }
        return success().add(sysMenu);
    }

    @ApiOperation("通过 id 获取单个菜单")
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        SysMenu menu = sysMenuService.getById(id);
        return success().add(menu);
    }

    @ApiOperation("通过 id 删除菜单")
    @DeleteMapping("{id}")
    public Result del(@PathVariable String id) {
        sysMenuService.removeById(id);
        return success();
    }

    @ApiOperation("通过id获取可选上级菜单列表")
    @GetMapping("superior/{id}")
    public Result superior(@ApiParam("id 为 0 时候获取所有") @PathVariable String id) {
        List<SysMenu> superior = sysMenuService.getSuperior(id, new ArrayList<>());
        List<Tree<String>> trees = sysMenuService.buildSelectData(superior);
        return success().add(trees);
    }

    @GetMapping("role/{roleId}")
    @ApiOperation(value = "通过角色id获取所有的折叠菜单id")
    public Result getMenuIdByRoleId(@ApiParam(value = "请输入角色id") @PathVariable("roleId") String roleId) {
        List<SysMenu> sysMenus = sysRoleMenuService.menuByRoleId(new SysUser(), new String[]{roleId});
        List<String> collect = sysMenus.stream().map(SysMenu::getMenuId).collect(Collectors.toList());
        return success().add(collect);
    }
}
