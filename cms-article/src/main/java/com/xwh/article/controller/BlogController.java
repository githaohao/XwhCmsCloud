package com.xwh.article.controller;

import com.xwh.article.entity.Post;
import com.xwh.article.service.PostService;
import com.xwh.article.service.param.PostParam;
import com.xwh.core.controller.BaseController;
import com.xwh.core.dao.Page;
import com.xwh.core.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    /*
    * 查询所有用户的公开文章分页模糊查询
    * */
    @ApiOperation("查询所有用户的公开文章分页模糊查询")
    @PostMapping("list")
    public Result list(@RequestBody  PostParam postParam){
        Page<Post> page = new Page<>(postParam.getIndex(),postParam.getSize());
        Page<Post> postPage = postService.page(page);
        return success().add(postPage);
    }
}
