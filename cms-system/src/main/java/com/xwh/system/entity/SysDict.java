package com.xwh.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xwh.core.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "sys_dict")
public class SysDict extends BaseEntity implements Serializable {

    @Id
    @Column(length = 20)
    @TableId(type = IdType.ASSIGN_ID)
    private String dictId;

    @NotBlank
    @ApiModelProperty(value = "名称")
    @Column(length = 50)
    private String name;

    @ApiModelProperty(value = "描述")
    @Column(length = 100)
    private String description;

    @ApiModelProperty(value = "状态")
    @Column(length = 1)
    Boolean status;
}
