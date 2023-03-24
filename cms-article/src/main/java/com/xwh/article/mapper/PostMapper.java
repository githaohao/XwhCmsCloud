package com.xwh.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwh.article.service.param.PostParam;
import com.xwh.article.entity.Post;
import com.xwh.core.dao.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface PostMapper extends BaseMapper<Post> {

    Page<Post> postByUser(@Param("userId") String userId, @Param("param") PostParam param);

}
