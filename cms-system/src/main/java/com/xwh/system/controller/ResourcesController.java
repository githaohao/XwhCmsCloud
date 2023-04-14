package com.xwh.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xwh.core.controller.BaseController;
import com.xwh.core.dto.Result;
import com.xwh.system.entity.SysResource;
import com.xwh.system.service.SysResourceService;
import com.xwh.system.service.SysRoleResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 接口权限控制
 *
 * @author xwh
 **/

@RestController
@RequestMapping("/resource")
@Api(tags = "系统:接口授权")
@Slf4j
@RequiredArgsConstructor
public class ResourcesController extends BaseController {

    final SysResourceService sysResourceService;
    final SysRoleResourceService sysRoleResourceService;


    /**
     * 通过角色id查询所有的接口授权id
     *
     * @return
     */
    @ApiOperation("通过角色id查询所有的接口授权id")
    @GetMapping("{roleId}")
    public Result resourceIdsByRoleId(@PathVariable String roleId) {
        Set<String> strings = sysResourceService.resourceIdsByRoleId(roleId);
        return success().add(strings);
    }

    /**
     * 新增单个接口
     * @return
     */
    @PostMapping("add")
    @ApiOperation("新增单个接口")
    public Result add(@RequestBody SysResource sysResource){
        sysResourceService.add(sysResource);
        return success();
    }

    /**
     * 保存该角色的授权
     *
     * @param roleId
     * @return
     */
    @ApiOperation("保存该角色的所有授权")
    @PostMapping("{roleId}")
    public Result saveResourceByRoleId(@PathVariable String roleId, @RequestBody String[] resourceIds) {
        sysRoleResourceService.saveUpdateRoleResource(roleId,resourceIds);
        return success();
    }


    @GetMapping("service")
    @ApiOperation("查询接口的 service 列表")
    public Result groupService() {
        ArrayList<Map<Object, Object>> maps = sysResourceService.groupService();
        return success().add(maps);
    }


    @GetMapping("list/{service}/{controller}")
    @ApiOperation("查询当前服务所有的接口")
    public Result list(@PathVariable String service, @PathVariable String controller) {
        QueryWrapper<SysResource> query = new QueryWrapper<>();
        query.eq("service", service);
        query.eq("controller", controller);
        List<SysResource> list = sysResourceService.list(query);
        return success().add(list);
    }


    @PostMapping("addAll/{service}")
    @ApiOperation("新增/更新 该服务所有的的接口")
    public Result addAll(@PathVariable String service, @RequestBody String apiList) {
        sysResourceService.saveResourceIsUpdate(apiList, service);
        return success();
    }

    @PutMapping()
    public Result update(@RequestBody SysResource sysResource){
        String resourceId = sysResource.getResourceId();
        if (StringUtils.isEmpty(resourceId)){
            return fail("Id 不能为空");
        }
        sysResourceService.updateById(sysResource);
        return success();
    }

}
