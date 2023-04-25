package com.xwh.article.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xwh.article.entity.TagEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class BlogUserDto {

    @Id
    @Column(length = 20)
    @TableId(type = IdType.ASSIGN_ID)
    private String postId;

    @Schema(name = "文章作者")
    private String author;

    @Schema(name = "作者头像")
    private String avatar;

    @Schema(name = "文章标题")
    private String title;

    @Schema(name = "这篇文章的用户信息")
    private Map<String, Object> user;

    @Schema(name = "这篇文章的用户信息")
    private List<TagEntity> tagList;

    @Schema(name = "文章状态 0==未发布1=已经发布2=草稿")
    @ColumnDefault("0")
    private Integer status;

    @Schema(name = "跳转地址")
    private String url;

    @Schema(name = "文章来源")
    private String source;

    @Lob
    @Schema(name = "发布摘要")
    private String summary;


    @Schema(name = "这篇文章的封面缩略图。")
    @Column(length = 1023)
    private String thumbnail;


    @Schema(name = "文章内容")
    @Lob
    private String content;


    @Schema(name = "文章访问数量")
    @ColumnDefault("0")
    private Long visits;

    @ColumnDefault("true")
    @Schema(name = "是否允许评论 1=允许评论2=静止评论")
    private Boolean disallowComment;


    @Schema(name = "访问密码")
    private String password;

    @Schema(name = "自定义模板")
    private String template;


    @Schema(name = "是否置顶")
    @ColumnDefault("0")
    private Integer topPriority;


    @Schema(name = "喜欢数量")
    @ColumnDefault("0")
    private Long likes;


    @Schema(name = "编辑时间")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;

    @Schema(name = "自定义关键字")
    @Column(length = 511)
    private String metaKeywords;

    @Schema(name = "自定义描述")
    @Column(length = 1023)
    private String metaDescription;

    @Schema(name = "内容字数")
    @ColumnDefault("0")
    private Long wordCount;

    @Schema(name = "发布版本")
    @ColumnDefault("1")
    private Integer version;

    @Column(length = 200)
    private String tags;

}
