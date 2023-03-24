package com.xwh.article.service.param;

import com.xwh.core.dao.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xwh
 **/
@Data
@ApiModel
public class PostParam extends Page<PostParam> {

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "标签id")
    private String tagName;

    @ApiModelProperty(value = "标签id")
    private String tagId;
}
