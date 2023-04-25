package com.xwh.article.service.param;

import com.xwh.core.dao.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author xwh
 **/
@Data
@Schema(name = "文章查询参数")
public class PostParam extends Page<PostParam> {

    @Schema(name = "文章id")
    private String postId;

    @Schema(name = "文章标题")
    private String title;

    @Schema(name = "标签id")
    private String tagId;

    @Schema(name = "状态")
    private Integer status;
}
