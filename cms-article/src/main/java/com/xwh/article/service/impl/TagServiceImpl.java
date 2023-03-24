package com.xwh.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.article.entity.PostTag;
import com.xwh.article.entity.Tag;
import com.xwh.article.entity.TagUser;
import com.xwh.article.mapper.PostTagMapper;
import com.xwh.article.mapper.TagMapper;
import com.xwh.article.mapper.TagUserMapper;
import com.xwh.article.service.TagService;
import com.xwh.core.exception.FailException;
import com.xwh.core.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xwh
 **/
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    final TagMapper tagMapper;
    final TagUserMapper tagUserMapper;
    final PostTagMapper postTagMapper;

    /**
     * 查询当前用户的所有标签
     *
     * @return
     */
    public List<Tag> listByUser(Tag vo) {
        String userId = TokenUtil.getUserId();
        return tagMapper.tagListByUser(userId, vo);
    }

    /**
     * 保存标签
     *
     * @return
     */
    public Tag saveTag(Tag tag) {
        // 判断当前 标签是否已经存在
        QueryWrapper<Tag> query = new QueryWrapper<>();
        query.eq("name", tag.getName());
        long count = count(query);
        if (count > 0) {
            throw new FailException("当前标签已经存在");
        }
        save(tag);
        return tag;
    }

    /**
     * 删除该角色的标签
     *
     * @param id
     */
    public void deleteByUser(String id) {
        String userId = TokenUtil.   getUserId();
        QueryWrapper<TagUser> query = new QueryWrapper<>();
        query.eq("user_id", userId).eq("tag_id", id);
        // 判断该用户的标签是否存在
        TagUser postTags = tagUserMapper.selectOne(query);
        if (ObjectUtils.isEmpty(postTags)) {
            throw new FailException("这不是您的标签");
        }
        // 判断该标签是否存在关联文章
        QueryWrapper<PostTag> tagQuery = new QueryWrapper<>();
        boolean tagId = postTagMapper.exists(tagQuery.eq("tag_id", id));
        if (tagId) {
            throw new FailException("当前标签存在文章,无法删除");
        }
        // 查询该标签是否还有其他用户使用
        QueryWrapper<TagUser> queryWrapper = new QueryWrapper<>();
        boolean postags = tagUserMapper.exists(queryWrapper.eq("tag_id", id));
        if (postags) {
            // 删除没有用户使用的标签
            removeById(id);
            tagUserMapper.delete(query);
        }
    }

    @Override
    public boolean editUser(String userId, Tag tag) {
        QueryWrapper<TagUser> query = new QueryWrapper<>();
        boolean byQuery = tagUserMapper.exists(query.eq("user_id", userId).eq("tag_id", tag.getTagId()));
        if (!byQuery) {
            throw new FailException("这不是您的文章");
        }
        Tag byId = tagMapper.selectById((tag.getTagId()));
        if (!byId.getName().equals(tag.getName())) {
            //如果修改当前的标签名
            throw new FailException("标签名不允许被修改");
        }
        //修改标签
        return updateById(tag);
    }

    @Override
    public boolean saveByUser(String userId, Tag tag) {
        //当前标签是否存在
        QueryWrapper<Tag> query = new QueryWrapper<>();
        Tag tagName = getOne(query.eq("name", tag.getName()));
        // 如果为空创建新的标签
        if (ObjectUtils.isEmpty(tagName)) {
            tagName = saveTag(tag);
        }
        //为当前用户绑定该标签
        TagUser postTags = new TagUser();
        postTags.setTagId(tagName.getTagId());
        postTags.setUserId(userId);

        QueryWrapper<TagUser> queryTagUser = new QueryWrapper<>();
        //查询当前标签是否已经是该用户的标签
        boolean falg = tagUserMapper.exists(queryTagUser.eq("user_id", userId).eq("tag_id", tagName.getTagId()));
        if (falg) {
            throw new FailException("标签已存在");
        }
        //保存标签
        tagUserMapper.insert(postTags);
        return false;
    }
}
