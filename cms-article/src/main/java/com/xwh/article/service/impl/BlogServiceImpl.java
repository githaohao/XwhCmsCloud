package com.xwh.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xwh.article.entity.Post;
import com.xwh.article.entity.Tag;
import com.xwh.article.entity.dto.BlogUserDto;
import com.xwh.article.feign.SysUserService;
import com.xwh.article.mapper.PostTagMapper;
import com.xwh.article.service.BlogService;
import com.xwh.article.service.PostService;
import com.xwh.article.service.TagService;
import com.xwh.article.service.param.PostParam;
import com.xwh.core.dao.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xwh
 **/
@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    final PostService postService;
    final PostTagMapper postTagMapper;
    final SysUserService sysUserService;
    final TagService tagService;

    @Override
    public BlogUserDto getBlog(String postId) {
        // 获得该文章的用户
        Post post = postService.getPost(postId);
        Map<String, Object> userMap = BeanUtil.beanToMap(post, "nickname", "avatar");
        BlogUserDto blogUserDto = BeanUtil.toBean(post, BlogUserDto.class);
        // 获得该文章的标签列表
        blogUserDto.setUser(userMap);
        List<Tag> tagsByPostId = postTagMapper.getTagsByPostId(blogUserDto.getPostId());
        blogUserDto.setTagList(tagsByPostId);
        return blogUserDto;
    }
}
