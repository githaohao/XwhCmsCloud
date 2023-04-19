package com.xwh.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwh.article.entity.Post;
import com.xwh.article.service.param.PostParam;
import com.xwh.core.dao.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface PostMapper extends BaseMapper<Post> {
    // 查询当前用户的文章列表
    Page<Post> userListPage(@Param("userId") String userId, @Param("param") PostParam param);
    // 查询文章列表
    Page<Post> listPage(@Param("param") PostParam param);
    // 查询单个文章
    Post getPost(@Param("postId") String postId);

}
