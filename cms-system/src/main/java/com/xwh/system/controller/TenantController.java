package com.xwh.system.controller;

import com.xwh.core.controller.BaseController;
import com.xwh.core.dao.Page;
import com.xwh.core.dto.Result;
import com.xwh.system.entity.SysTenant;
import com.xwh.system.service.SysTenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/tenant")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "系统:租户管理")
public class TenantController extends BaseController {
    final SysTenantService sysTenantService;

    @PostMapping("list/{size}/{index}")
   @Operation(summary = "分页查询获取当前的租户列表")
    public Result listPage(@PathVariable Long size, @PathVariable Long index){
        Page<SysTenant> page = new Page<>(index, size);
        return success().add(sysTenantService.page(page));
    }

   @Operation(summary = "添加一个租户信息")
    @PostMapping()
    public Result add(@RequestBody SysTenant sysTenant){
        sysTenantService.save(sysTenant);
        return success();
    }

   @Operation(summary = "删除一个租户信息")
    @DeleteMapping()
    public Result del(@RequestBody String[] ids){
        //通过id批量删除
        sysTenantService.removeByIds(Arrays.asList(ids));
        return success();
    }

   @Operation(summary = "修改这个租户")
    @PutMapping()
    public Result edit(@RequestBody SysTenant sysTenant){
        sysTenantService.updateById(sysTenant);
        return success();
    }


}
