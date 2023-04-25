package com.xwh.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwh.article.entity.PostTag;
import com.xwh.article.entity.TagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface PostTagMapper extends BaseMapper<PostTag> {

    @Select("SELECT t.name,t.color,t.tag_id FROM post_tag AS pt INNER JOIN tag AS t ON pt.tag_id = t.tag_id WHERE pt.post_id =#{postId}")
    List<TagEntity> getTagsByPostId(@Param("postId") String postId);

}
