package com.xwh.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwh.article.entity.TagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface TagMapper extends BaseMapper<TagEntity> {

    /**
     * 通过userid 获得tag列表
     *
     * @param userId
     * @return
     */
    List<TagEntity> tagListByUser(@Param("userId") String userId, @Param("tag") TagEntity tag);
}
