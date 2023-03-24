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

    @ApiModelProperty(value = "关联字典Id", hidden = true)
    private String dictId;

    @ApiModelProperty(value = "字典标签")
    @Column(length = 50)
    private String label;

    @ApiModelProperty(value = "字典值")
    @Column(length = 50)
    private String value;

    @ApiModelProperty(value = "排序")
    @Column(length = 5)
    private Integer dictSort = 999;

    @ApiModelProperty(value = "状态")
    @Column(length = 1)
    Boolean status;
}
