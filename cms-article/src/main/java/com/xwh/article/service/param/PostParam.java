package com.xwh.article.service.param;

import com.xwh.core.dao.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xwh
 **/
@Data
@ApiModel
public class PostParam extends Page<PostParam> {

    @ApiModelProperty(value = "文章id")
    private String postId;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "标签id")
    private String tagId;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
