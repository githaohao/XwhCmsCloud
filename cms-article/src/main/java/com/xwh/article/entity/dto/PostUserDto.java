package com.xwh.article.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xwh.article.entity.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class PostUserDto {

    @Id
    @Column(length = 20)
    @TableId(type = IdType.ASSIGN_ID)
    private String postId;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "这篇文章的用户信息")
    private Map<String,Object> user;

    @ApiModelProperty(value = "这篇文章的用户信息")
    private List<Tag> tagList;

    @ApiModelProperty(value = "文章状态 0==未发布1=已经发布2=草稿")
    @ColumnDefault("0")
    private Integer status;

    @ApiModelProperty(value = "跳转地址")
    private String url;

    @ApiModelProperty(value = "文章来源")
    private String source;

    @Lob
    @ApiModelProperty(value = "发布摘要")
    private String summary;


    @ApiModelProperty(value = "这篇文章的封面缩略图。")
    @Column(length = 1023)
    private String thumbnail;


    @ApiModelProperty(value = "文章内容")
    @Lob
    private String content;


    @ApiModelProperty(value = "文章访问数量")
    @ColumnDefault("0")
    private Long visits;

    @ColumnDefault("true")
    @ApiModelProperty(value = "是否允许评论 1=允许评论2=静止评论")
    private Boolean disallowComment;


    @ApiModelProperty(value = "访问密码")
    private String password;

    @ApiModelProperty(value = "自定义模板")
    private String template;


    @ApiModelProperty(value = "是否置顶")
    @ColumnDefault("0")
    private Integer topPriority;


    @ApiModelProperty(value = "喜欢数量")
    @ColumnDefault("0")
    private Long likes;


    @ApiModelProperty(value = "编辑时间")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;

    @ApiModelProperty(value = "自定义关键字")
    @Column(length = 511)
    private String metaKeywords;

    @ApiModelProperty(value = "自定义描述")
    @Column(length = 1023)
    private String metaDescription;


    @ApiModelProperty(value = "内容字数")
    @ColumnDefault("0")
    private Long wordCount;

    @ApiModelProperty(value = "发布版本")
    @ColumnDefault("1")
    private Integer version;

    @Column(length = 200)
    private String tags;

}
