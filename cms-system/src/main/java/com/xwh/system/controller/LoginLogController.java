package com.xwh.system.controller;

import com.xwh.core.controller.BaseController;
import com.xwh.core.dao.Page;
import com.xwh.core.dto.Result;
import com.xwh.core.utils.TokenUtil;
import com.xwh.system.entity.SysLoginLog;
import com.xwh.system.service.SysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 登录日志
 *
 * @author xwh
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/loginlog")
@Api(tags = "系统:登录日志")
@Slf4j
public class LoginLogController extends BaseController {

    final SysLoginLogService sysLoginLogService;

    @ApiOperation("获取当前登录用户的登录日志")
    @PostMapping("user")
    public Result login(@RequestBody Page<SysLoginLog> page) {
        // 获取当前用户的 userid
        Page<SysLoginLog> pageByUser = sysLoginLogService.getPageByUser(page);
        return success().add(pageByUser);
    }

    @ApiOperation("写入已经登录用户的登录日志")
    @PostMapping("")
    public Result save(@ApiParam("操作类型1=登录2=登出") @RequestParam Integer operType,
                       @ApiParam("登录结果类型(1,成功；2失败)") @RequestParam Integer resultType,
                       @ApiParam("登录类型(字典表查询)") @RequestParam Integer loginType,
                       @ApiParam("登录备注") @RequestParam String msg){
        String username = TokenUtil.getUsernameFromToken();
        sysLoginLogService.saveLoginLog(username, operType, resultType, loginType, msg);
        return success();
    }


}
