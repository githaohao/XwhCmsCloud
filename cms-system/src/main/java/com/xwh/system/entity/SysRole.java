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

/**
 * @description: 系统_角色
 * @date: 2019年12月20 14:32:42
 * @version: 1.0                                                 */
@Table(name="sys_role")
@Setter
@Getter
@Entity
public class SysRole extends BaseEntity implements Serializable {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @Column(length = 20)
	private String roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    @Column(length = 30)
    private String name;
    /**
     * 角色标识
     */
    @ApiModelProperty(value = "角色标识")
    @Column(length = 20)
    private String role;
    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    @Column(length = 100)
    private String description;
    /**
     * 角色状态
     */
    @ApiModelProperty(value = "角色状态")
    @Column(length = 1)
    private Boolean status;

}
