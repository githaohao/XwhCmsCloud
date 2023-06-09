package com.xwh.article.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xwh.article.entity.Post;
import com.xwh.article.entity.PostTag;
import com.xwh.article.mapper.PostTagMapper;
import com.xwh.article.service.PostService;
import com.xwh.article.service.param.PostParam;
import com.xwh.core.controller.BaseController;
import com.xwh.core.dao.Page;
import com.xwh.core.dto.Result;
import com.xwh.core.exception.FailException;
import com.xwh.core.utils.StringUtil;
import com.xwh.core.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;


/**
 * @author xiangwenhao
 */
@RestController
@RequestMapping("/post")
@Tag(name = "文章:文章管理")
@Slf4j
@RequiredArgsConstructor
public class PostController extends BaseController {

    final PostService postService;
    final PostTagMapper postTagMapper;

    /**
     * 新增笔记
     *
     * @return
     */
    @PostMapping("user")
   @Operation(summary = "保存我的文章")
    public Result saveByUser(@RequestBody JSONObject data) {
        Post post = JSON.toJavaObject(data, Post.class);
        if (StringUtil.isEmpty(post.getTitle())) {
            return fail("请输入文章标题");
        }
        postService.saveByUser(post);
        return success();
    }

   @Operation(summary = "获取我的文章列表")
    @PostMapping("userlist")
    public Result userlist(@RequestBody PostParam param) {
        Page<Post> postPageInfo = postService.userPostList(param);
        return success().add(postPageInfo);
    }

   @Operation(summary = "删除根据id删除")
    @DeleteMapping("user")
    public Result delByIds(@RequestBody String[] ids) {
        String userId = TokenUtil.getUserId();
        for (String id : ids) {
            QueryWrapper<Post> query = new QueryWrapper<>();
            query.eq("user_id", userId);
            query.eq("post_id", id);
            Post byQuery = postService.getOne(query);
            if (ObjectUtils.isEmpty(byQuery)) {
                throw new FailException("没有权限删除");
            }
            // 关联的tagPost也需要删除
            QueryWrapper<PostTag> queryPostTag = new QueryWrapper<>();
            queryPostTag.eq("post_id", id);
            postTagMapper.delete(queryPostTag);
            //删除文章
            postService.removeById(id);
        }
        return success();
    }
}
