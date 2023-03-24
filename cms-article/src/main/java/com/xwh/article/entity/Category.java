package com.xwh.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xwh.core.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Category extends BaseEntity {

    @Id
    @Column(length = 20)
    @TableId(type = IdType.ASSIGN_ID)
    private Integer categoryId;



    @ApiModelProperty(value = "分类密码")
    private String password;


}
