package com.xwh.article.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xwh.article.annotation.TimeRange;
import com.xwh.article.entity.Post;
import com.xwh.article.entity.Tag;
import com.xwh.article.entity.dto.BlogUserDto;
import com.xwh.article.service.BlogService;
import com.xwh.article.service.PostService;
import com.xwh.article.service.TagService;
import com.xwh.article.service.param.PostParam;
import com.xwh.core.controller.BaseController;
import com.xwh.core.dao.Page;
import com.xwh.core.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 博客接口
 *
 * @author xwh
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("blog")
@Api(tags = "文章:博客接口管理")
public class BlogController extends BaseController {

    final PostService postService;
    final BlogService blogService;
    final TagService tagService;


    /**
     * List result.
     *
     * @param postParam the post param
     * @return the result
     */
    /*
    * 查询所有用户的公开文章分页模糊查询
    * */
    @ApiOperation("查询所有用户的公开文章分页模糊查询")
    @PostMapping("list")
    public Result list(@RequestBody  PostParam postParam){
        Page<Post> postPage = postService.listPage(postParam);
        return success().add(propertyDel(postPage,"password"));
    }

    /**
     * 查询单个文章
     *
     * @param postId the post id
     * @return the result
     */
    @ApiOperation("查询单个文章")
    @GetMapping("get/{postId}")
    public Result get(@PathVariable String postId){
        BlogUserDto post = blogService.getBlog(postId);
        // 获取当前文章的用户姓名
        return success().add(propertyDel(post,"password"));
    }

    @ApiOperation("查询热门文章")
    @GetMapping("top{size}/{time}")
    public Result getTop(@PathVariable Integer size,@PathVariable String time){
        TimeRange timeRange = TimeRange.valueOf(time.toUpperCase());
        List<Post> topBlog = blogService.getTopBlog(size, timeRange);
        // 返回指定字段
        return success().add(propertyShow(topBlog,"title","visits","postId","createTime"));
    }

    @ApiOperation("查询热门标签")
    @GetMapping("topTag{size}/{time}")
    public Result getTopTag(@PathVariable Integer size,@PathVariable String time){
        TimeRange timeRange = TimeRange.valueOf(time.toUpperCase());
        List<Tag> topTag = blogService.getTopTag(size,timeRange);
        // 返回指定字段
        return success().add(propertyShow(topTag,"name","visits","tagId","color"));
    }

    /**
     * 获取全部标签
     */
    @ApiOperation("获取全部标签")
    @GetMapping("allTag")
    public Result getAllTag(){
        List<Tag> list = tagService.list();
        // 返回指定字段
        return success().add(propertyShow(list,"name","visits","tagId","color"));
    }

}
