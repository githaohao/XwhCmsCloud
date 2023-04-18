package com.xwh.article.controller;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xwh.article.entity.Post;
import com.xwh.article.entity.Tag;
import com.xwh.article.entity.dto.BlogUserDto;
import com.xwh.article.mapper.TagMapper;
import com.xwh.article.service.BlogService;
import com.xwh.article.service.PostService;
import com.xwh.article.service.TagService;
import com.xwh.article.service.param.PostParam;
import com.xwh.core.controller.BaseController;
import com.xwh.core.dao.Page;
import com.xwh.core.dto.Result;
import com.xwh.core.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 博客接口
 *
 * @author xwh
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("blog")
@Api(tags = "文章:博客接口管理")
public class BlogController extends BaseController {

    final PostService postService;
    final BlogService blogService;
    final TagService tagService;
    /*
    * 查询所有用户的公开文章分页模糊查询
    * */
    @ApiOperation("查询所有用户的公开文章分页模糊查询")
    @PostMapping("list")
    public Result list(@RequestBody  PostParam postParam){
        Page<Post> page = new Page<>(postParam.getIndex(),postParam.getSize());
        // 根据 visits 排序
        QueryWrapper<Post> query = new QueryWrapper<>();
        // title 模糊查询为空忽略
        if (StringUtils.isNotBlank(postParam.getTitle())){
            query.like("title",postParam.getTitle());
        }
        query.eq("status",1);
        query.orderByDesc("visits");
        Page<Post> postPage = postService.page(page,query);
        return success().add(postPage);
    }

    /**
     * 查询单个文章
     */
    @ApiOperation("查询单个文章")
    @GetMapping("get/{postId}")
    public Result get(@PathVariable String postId){
        BlogUserDto post = blogService.getBlog(postId);
        return success().add(post);
    }

    @ApiOperation("查询点击量前20的标签")
    @GetMapping("top20Tag")
    public Result getTop20Tag(){
        QueryWrapper<Tag> query = new QueryWrapper<>();
        query.orderByDesc("visits");
        query.last("limit 20");
        List<Tag> list = tagService.list(query);
        return success().add(propertyShow(list,"name","visits","tagId","color"));
    }
}
