package com.xwh.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.article.entity.Post;
import com.xwh.article.entity.PostTag;
import com.xwh.article.feign.SystemUserService;
import com.xwh.article.mapper.PostMapper;
import com.xwh.article.mapper.PostTagMapper;
import com.xwh.article.mapper.TagMapper;
import com.xwh.article.service.PostService;
import com.xwh.article.service.param.PostParam;
import com.xwh.core.dao.Page;
import com.xwh.core.exception.FailException;
import com.xwh.core.utils.StringUtil;
import com.xwh.core.utils.TokenUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @author xwh
 **/
@Service
@RequiredArgsConstructor
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    final PostTagMapper postTagMapper;
    final PostMapper postMapper;
    @Lazy
    @Resource
    final SystemUserService systemUserService;
    final TagMapper tagMapper;

    /**
     * 保存当前用户的文章
     *
     * @param vo
     */
    @Override
    public void saveByUser(Post vo) {
        // 获取当前的用户名
        Post post = BeanUtil.toBean(vo, Post.class);
        String userId = TokenUtil.getUserId();
        post.setUserId(userId);
        if (StringUtil.isEmpty(post.getPostId())) {
            String postId = IdUtil.getSnowflakeNextIdStr();
            post.setPostId(postId);
            save(post);
            savePostTags(postId, vo.getTags());
        } else {
            // 判断是否是该用户的文章
            Post postById = getById(post.getPostId());
            if (Objects.isNull(postById)) {
                throw new FailException("文章不存在");
            }
            if (!postById.getUserId().equals(userId)) {
                throw new FailException("无权限修改");
            }
            updateById(post);
            savePostTags(post.getPostId(), vo.getTags());
        }
    }

    /**
     * 保存文章的标签
     *
     * @param postId
     * @param arr
     */
    @Override
    public void savePostTags(String postId, String arr) {
        //解决当数组只有一个的时候
        String[] tags = arr.substring(1, arr.length() - 1).split(",");
        System.out.println(tags.length);
        // 如果 tags 为空就不保存
        if ((tags.length == 0)) {
            return;
        }
        // 先删除该文章的所有标签JSON
        postTagMapper.deleteByMap(Map.of("post_id", postId));
        for (String tagId : tags) {
            PostTag postTag = new PostTag();
            postTag.setPostId(postId);
            // 去除 arr 的引号
            tagId = tagId.substring(1, tagId.length() - 1);
            postTag.setTagId(tagId);
            postTagMapper.insert(postTag);
        }
    }


    /**
     * 查询当前用户的的文章列表
     *
     * @param param
     * @return
     */
    @Override
    public Page<Post> userPostList(PostParam param) {
        String userId = TokenUtil.getUserId();
        return postMapper.userListPage(userId, param);
    }

    /**
     * 查询当前用户单个文章
     * @return
     */
    public Post getUserPost(String postId) {
        // 判断是否是该用户的文章
        Post postById = getById(postId);
        if (Objects.isNull(postById)) {
            throw new FailException("文章不存在");
        }
        String userId = TokenUtil.getUserId();
        if (!postById.getUserId().equals(userId)) {
            throw new FailException("无权限查看");
        }
        return postById;
    }

    /**
     * 查询文章列表
     *
     * @return
     */
    @Override
    public Page<Post> listPage(PostParam postParam) {
        return postMapper.listPage(postParam);
    }
}
