package com.xwh.system.controller;

import com.xwh.core.controller.BaseController;
import com.xwh.core.dao.Page;
import com.xwh.core.dto.Result;
import com.xwh.system.entity.SysDict;
import com.xwh.system.entity.SysDictDetail;
import com.xwh.system.service.SysDictDetailService;
import com.xwh.system.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author xiangwenhao
 */
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "系统:字典管理")
public class DictController extends BaseController {

    final SysDictService sysDictService;
    final SysDictDetailService sysDictDetailService;


    @GetMapping("{name}")
    @ApiOperation("根据字典名称获取字典列表")
    public Result get(@PathVariable String name) {
        return success().add(propertyShow(sysDictService.dict(name), "label", "value"));
    }

    @PostMapping("list")
    @ApiOperation("获取所有的字典列表")
    public Result list(@RequestBody Page<SysDict> pageParam) {
        System.out.println(pageParam);
        Page<SysDict> page = sysDictService.page(pageParam);
        return success().add(page);
    }

    @GetMapping("detail/{dictId}")
    @ApiOperation("获取字典详细")
    public Result getDetail(@PathVariable String dictId) {
        List<SysDictDetail> list = sysDictDetailService.getBaseMapper()
                .selectByMap(Map.of("dict_id",dictId));
        return success().add(list);
    }

    @PostMapping
    @ApiOperation("添加一个字典")
    public Result add(@RequestBody SysDict sysDict) {
        if (sysDict.getName().isEmpty()){
            return fail("字典名称不能为空");
        }
        if (sysDict.getDescription().isEmpty()){
            return fail("字典描述不能为空");
        }
        sysDict.setDictId(sysDict.getName());
        sysDictService.save(sysDict);
        return success();
    }

    @PostMapping("detail")
    @ApiOperation("为当前字典添加一个字典详细")
    public Result addDetail(@RequestBody SysDictDetail sysDictDetail) {
        sysDictDetailService.save(sysDictDetail);
        return success();
    }

    @DeleteMapping()
    @ApiOperation("删除一个字典")
    public Result del(@RequestBody String[] dictIds) {
        for (String dictId : dictIds) {
            sysDictService.removeById(dictId);
            //删除当前所有的字典详情
            sysDictDetailService.removeByMap(Map.of("dict_id", dictId));
        }
        return success();
    }

    @DeleteMapping("detail")
    @ApiOperation("删除字典详情")
    public Result delDetail(@RequestBody String[] detailIds) {
        sysDictDetailService.removeByIds(Arrays.asList(detailIds));
        return success();
    }

    @PutMapping()
    @ApiOperation("编辑字典详情")
    public Result update(@RequestBody SysDict sysDict) {
        sysDictService.updateById(sysDict);
        return success();
    }

    @PutMapping("detail")
    @ApiOperation("编辑字典详情")
    public Result updateDetail(@RequestBody SysDictDetail sysDictDetail) {
        sysDictDetailService.updateById(sysDictDetail);
        return success();
    }
}
