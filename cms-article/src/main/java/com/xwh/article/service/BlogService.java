package com.xwh.article.service;

import com.xwh.article.annotation.TimeRange;
import com.xwh.article.entity.Post;
import com.xwh.article.entity.Tag;
import com.xwh.article.entity.dto.BlogUserDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xwh
 **/

@Service
public interface BlogService {

    /**
     * 获取单个博客
     *
     * @param postId the post id
     * @return the blog
     */
    BlogUserDto getBlog(String postId);

    List<Post> getTopBlog(Integer size, TimeRange time);

    List<Tag> getTopTag(Integer size, TimeRange time);
}
