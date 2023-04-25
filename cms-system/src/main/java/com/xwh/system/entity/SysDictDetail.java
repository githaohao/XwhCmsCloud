package com.xwh.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xwh.core.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "sys_dict_detail")
public class SysDictDetail extends BaseEntity implements Serializable {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @Column(length = 20)
    private String detailId;

    @Schema(name = "关联字典Id", hidden = true)
    private String dictId;

    @Schema(name = "字典标签")
    @Column(length = 50)
    private String label;

    @Schema(name = "字典值")
    @Column(length = 50)
    private String value;

    @Schema(name = "排序")
    @Column(length = 5)
    private Integer dictSort = 999;

    @Schema(name = "状态")
    @Column(length = 1)
    Boolean status;
}
