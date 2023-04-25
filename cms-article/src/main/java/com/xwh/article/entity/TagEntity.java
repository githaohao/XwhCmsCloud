package com.xwh.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xwh.core.entity.BaseTenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;


import java.io.Serializable;


@Setter
@Getter
@Entity
@Table(name = "tag")
@TableName("tag")
public class TagEntity extends BaseTenantEntity implements Serializable {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @Column(length = 20)
    private String tagId;

    @Schema(name = "标签名")
    private String name;

    @Schema(name = "别名")
    private String alias;

    @Schema(name = "标签访问数量")
    @ColumnDefault("0")
    private Long visits;

    @Schema(name = "标签颜色")
    @Column(length = 25)
    private String color;

    @Schema(name = "封面缩略图")
    @Column(length = 1023)
    private String thumbnail;
}
