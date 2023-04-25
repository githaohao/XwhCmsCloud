package com.xwh.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xwh.core.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@Entity
public class Category extends BaseEntity {

    @Id
    @Column(length = 20)
    @TableId(type = IdType.ASSIGN_ID)
    private Integer categoryId;



    @Schema(name = "分类密码")
    private String password;


}
