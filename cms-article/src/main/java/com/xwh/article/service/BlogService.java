package com.xwh.article.service;

import com.xwh.article.entity.dto.BlogUserDto;
import org.springframework.stereotype.Service;

/**
 * @author xwh
 **/

@Service
public interface BlogService {

    BlogUserDto getBlog(String postId);

}
