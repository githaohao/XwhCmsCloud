package com.xwh.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xwh.article.entity.Post;
import com.xwh.article.entity.TagEntity;
import com.xwh.article.entity.dto.BlogUserDto;
import com.xwh.article.enums.TimeRange;
import com.xwh.article.feign.SystemUserService;
import com.xwh.article.mapper.PostTagMapper;
import com.xwh.article.service.BlogService;
import com.xwh.article.service.PostService;
import com.xwh.article.service.TagService;
import com.xwh.core.dto.Result;
import com.xwh.core.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

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
    final SystemUserService systemUserService;
    final TagService tagService;
    final JdbcTemplate jdbcTemplate;
    final PlatformTransactionManager transactionManager;


    @Override
    public BlogUserDto getBlog(String postId) {
        // 获得该文章的用户
        Post post = postService.getById(postId);
        // 获取当前的用户昵称,如果为空就获取用户名
        String userId = post.getUserId();
        Result user = systemUserService.findById(userId);
        Map<String, Object> data = (Map<String, Object>) user.getData();
        String nickname = String.valueOf(data.get("nickname"));
        // 获取头像
        String avatar = String.valueOf(data.get("avatar"));
        // 如果nickname 为空就获取username
        if (StringUtil.isEmpty(nickname)) {
            nickname = String.valueOf(data.get("username"));
        }
        //头像为空就用默认头像
        if (StringUtil.isEmpty(avatar)) {
            avatar = "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png";
        }
        BlogUserDto blogUserDto = BeanUtil.toBean(post, BlogUserDto.class);
        blogUserDto.setEditTime(post.getUpdateTime());
        // 设置用户信息
        blogUserDto.setAuthor(nickname);
        // 设置头像
        blogUserDto.setAvatar(avatar);
        // 获得该文章的标签列表
        List<TagEntity> tagsByPostId = postTagMapper.getTagsByPostId(blogUserDto.getPostId());
        blogUserDto.setTagList(tagsByPostId);
        // 该文章的访问量 +1
        // 如果访问量为空就设置为1
        post.setVisits(post.getVisits() == null ? 1L : post.getVisits() + 1L);
        postService.updateById(post);
        // 该文章的标签访问数量 + 1
        // 如果标签的访问量为空就设置为1
        tagsByPostId.forEach(tag -> {
            tag.setVisits(tag.getVisits() == null ? 1L : tag.getVisits() + 1L);
            tagService.updateById(tag);
        });
        return blogUserDto;
    }

    /**
     * 用工具类获取时间段的query 方法
     *
     * @param size
     * @param timeRange
     * @param orderBy
     * @param <T>
     * @return
     */
    private <T> QueryWrapper<T> getTopItems(Integer size, TimeRange timeRange, String orderBy) {
        QueryWrapper<T> query = new QueryWrapper<>();
        query.orderByDesc(orderBy);
        query.last("limit " + size);
        //查询时间段内的文章
        query.apply(timeRange.getExpression() + " <= date(create_time)");
        return query;
    }

    @Override
    public List<Post> getTopBlog(Integer size, TimeRange timeRange) {
        QueryWrapper<Post> visits = getTopItems(size, timeRange, "visits");
        return postService.list(visits);

    }

    @Override
    public List<TagEntity> getTopTag(Integer size, TimeRange timeRange) {
        QueryWrapper<TagEntity> visits = getTopItems(size, timeRange, "visits");
        return tagService.list(visits);
    }

    @Override
    public void like(String postId) {
        Post post = postService.getById(postId);
        post.setLikes(post.getLikes() == null ? 1L : post.getLikes() + 1L);
        postService.updateById(post);
    }

    @Override
    public void unlike(String postId) {
        Post post = postService.getById(postId);
        // 如果点赞数为0就不减了
        if (post.getLikes() == null) {
            post.setLikes(0L);
        } else if (post.getLikes() > 0) {
            post.setLikes(post.getLikes() - 1L);
        } else {
            post.setLikes(0L);
        }
        postService.updateById(post);
    }
}
