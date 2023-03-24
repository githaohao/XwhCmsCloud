package com.xwh.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.article.entity.Tag;
import com.xwh.article.entity.dto.PostUserDto;
import com.xwh.article.feign.SysUserService;
import com.xwh.article.mapper.TagMapper;
import com.xwh.article.service.param.PostParam;
import com.xwh.article.entity.Post;
import com.xwh.article.entity.PostTag;
import com.xwh.article.mapper.PostMapper;
import com.xwh.article.mapper.PostTagMapper;
import com.xwh.article.service.PostService;
import com.xwh.core.dao.Page;
import com.xwh.core.exception.FailException;
import com.xwh.core.utils.StringUtil;
import com.xwh.core.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xwh
 **/
@Service
@RequiredArgsConstructor
public class PostServiceImpl extends ServiceImpl<PostMapper,Post> implements PostService {

    final PostTagMapper postTagMapper;
    final PostMapper postMapper;
    final SysUserService sysUserService;
    final TagMapper tagMapper;

    /**
     * 保存当前用户的文章
     * @param vo
     */
    @Override
    public void saveByUser(Post vo) {
        // 获取当前的用户名
        Post post = BeanUtil.toBean(vo, Post.class);
        String userId = TokenUtil.getUserId();
        post.setUserId(userId);
        if (StringUtil.isEmpty(post.getPostId())){
            String postId = IdUtil.getSnowflakeNextIdStr();
            post.setPostId(postId);
            save(post);
            savePostTags(postId,vo.getTags());
        } else {
            //判断是否是该用户的文章
            Post byId = getById(post.getPostId());
            if (!Objects.equals(byId.getUserId(), userId)){
                throw new FailException("这不是您的文章");
            }
            updateById(post);
            savePostTags(byId.getPostId(),vo.getTags());
        }
    }

    /**
     * 保存文章的标签
     * @param postId
     * @param arr
     */
    @Override
    public void savePostTags(String postId, String arr) {
        // 先删除该文章的所有标签JSON
        postTagMapper.deleteByMap(Map.of("post_id", postId));
        List<String> tags = Arrays.asList(arr.substring(1, arr.length() - 1).split(","));
        for (String tagId : tags) {
            PostTag postTag = new PostTag();
            postTag.setPostId(postId);
            postTag.setTagId(tagId);
            postTagMapper.insert(postTag);
        }
    }


    /**
     * 查询当前用的文章
     *
     * @param param
     * @return
     */
    @Override
    public Page<Post> postByUser(PostParam param) {
        String userId = TokenUtil.getUserId();

        return postMapper.postByUser(userId,param);
    }

    @Override
    public PostUserDto getByIdAndUser(String postId) {
        Post postUser = getById(postId);
        // 获得该文章的用户
        Map<String, Object> userMap = BeanUtil.beanToMap(sysUserService.findById(postUser.getUserId()).getData(),
                "nickname","avatar");
        PostUserDto postUserDto = BeanUtil.toBean(postUser, PostUserDto.class);
        // 获得该文章的标签列表
        postUserDto.setUser(userMap);
        List<Tag> tagsByPostId = postTagMapper.getTagsByPostId(postUserDto.getPostId());
        postUserDto.setTagList(tagsByPostId);
        return postUserDto;
    }
}

