package com.xwh.article.controller;

import com.xwh.article.entity.TagEntity;
import com.xwh.article.service.TagService;
import com.xwh.core.controller.BaseController;
import com.xwh.core.dto.Result;
import com.xwh.core.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xwh
 **/

@RestController
@RequestMapping("/tag")
@Tag(name = "文章:我的标签")
@Slf4j
@RequiredArgsConstructor
public class TagController extends BaseController {

    final TagService tagService;

    @PostMapping("userlist")
   @Operation(summary = "查询当前用户的所有标签")
    public Result TagListByUser(@RequestBody TagEntity vo) {
        List<TagEntity> tags = tagService.listByUser(vo);
        return success().add(propertyShow(tags, "tagId", "name", "color"));
    }

    @PostMapping("user")
   @Operation(summary = "保存当前用户的标签")
    public Result saveByUser(@RequestBody TagEntity tag) {
        if (StringUtils.isBlank(tag.getName())) {
            return fail("标签名称不能为空");
        }
        String userId = TokenUtil.getUserId();
        tagService.saveByUser(userId,tag);
        return success();
    }

    @DeleteMapping("user")
   @Operation(summary = "删除当前用户标签")
    public Result deleteByUser(@RequestBody String[] ids) {
        for (String id : ids) {
            tagService.deleteByUser(id);
        }
        return success();
    }

    @PutMapping("user")
   @Operation(summary = "编辑当前用户的标签")
    public Result editUser(@RequestBody TagEntity tag) {
        if (StringUtils.isEmpty(tag.getTagId())){
            return fail("请输入标签id");
        }
        String userId = TokenUtil.getUserTenant();
        tagService.editUser(userId,tag);
        return success();
    }
}
