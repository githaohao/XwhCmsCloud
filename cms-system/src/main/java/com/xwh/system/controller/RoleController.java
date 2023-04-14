package com.xwh.system.controller;

import com.xwh.core.controller.BaseController;
import com.xwh.core.dto.Result;
import com.xwh.system.entity.SysRole;
import com.xwh.system.entity.SysRoleMenu;
import com.xwh.system.service.SysRoleMenuService;
import com.xwh.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统角色管理
 *
 * @author xwh
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
@Api(tags = "系统:角色管理")
@Slf4j
public class RoleController extends BaseController {

    final SysRoleService sysRoleService;
    final SysRoleMenuService sysRoleMenuService;

    @GetMapping()
    @ApiOperation(value = "获取角色列表")
    public Result list() {
        List<SysRole> list = sysRoleService.list();
        return success().add(list);
    }

    @PostMapping()
    @ApiOperation(value = "添加角色")
    public Result add(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return success();
    }

    @PutMapping
    @ApiOperation(value = "修改角色")
    public Result update(@RequestBody SysRole sysRole) {
        SysRole role = sysRoleService.getById(sysRole.getRoleId());
        if (!role.getRoleId().equals(sysRole.getRoleId())){
            return fail("权限表示不可修改");
        }
        sysRoleService.updateById(sysRole);
        return success().add(sysRole);
    }

    @DeleteMapping()
    @ApiOperation(value = "删除角色")
    public Result delete(@RequestBody String id) {
        sysRoleService.delete(id);
        return success();
    }


    @PostMapping("/roleIdSaveMenus/{roleId}")
    @ApiOperation(value = "通过当前角色id授权菜单权限")
    public Result roleIdSaveMenus(@PathVariable String roleId, @RequestBody String[] menuIds){
        // 删除该角色的所有菜单
        sysRoleMenuService.removeByMap(Map.of("role_id", roleId));
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        for (String menuId : menuIds) {
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuService.save(sysRoleMenu);
        }
        return success();
    }

}
