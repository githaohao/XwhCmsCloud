package com.xwh.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xwh.core.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

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
    @Schema(name = "名称")
    @Column(length = 50)
    private String name;

    @Schema(name = "描述")
    @Column(length = 100)
    private String description;

    @Schema(name = "状态")
    @Column(length = 1)
    Boolean status;
}
