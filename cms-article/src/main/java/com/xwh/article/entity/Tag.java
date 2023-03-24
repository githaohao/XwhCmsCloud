package com.xwh.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xwh.core.entity.BaseTenantEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


@Setter
@Getter
@Entity
@TableName("tag")
public class Tag extends BaseTenantEntity implements Serializable {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @Column(length = 20)
    private String tagId;

    @ApiModelProperty(value = "标签名")
    private String name;

    @ApiModelProperty(value = "别名")
    private String alias;

    @ApiModelProperty(value = "标签访问数量")
    @ColumnDefault("0")
    private Long visits;

    @ApiModelProperty(value = "标签颜色")
    @Column(length = 25)
    private String color;

    @ApiModelProperty(value = "封面缩略图")
    @Column(length = 1023)
    private String thumbnail;

}
