package com.xwh.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.article.entity.Post;
import com.xwh.article.entity.PostTag;
import com.xwh.article.feign.SysUserService;
import com.xwh.article.mapper.PostMapper;
import com.xwh.article.mapper.PostTagMapper;
import com.xwh.article.mapper.TagMapper;
import com.xwh.article.service.PostService;
import com.xwh.article.service.param.PostParam;
import com.xwh.core.dao.Page;
import com.xwh.core.exception.FailException;
import com.xwh.core.utils.StringUtil;
import com.xwh.core.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
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
    final SysUserService sysUserService;
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
        String[] tags = arr.substring(1, arr.length() - 1).split(",");
        // 如果 tags 为空就不保存
        if ((tags.length - 1) == 0) {
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
     * @param param
     * @return
     */
    @Override
    public Page<Post> postByUser(PostParam param) {
        String userId = TokenUtil.getUserId();
        return postMapper.postByUser(userId, param);
    }

    /**
     * 根据文章id查询文章
     *
     * @return
     */
    @Override
    public Page<Post> getPost(PostParam postParam) {
        // 判断是否是该用户的文章
        return postMapper.postByUser(null, postParam);
    }
}
