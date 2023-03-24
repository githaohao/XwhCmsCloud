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
import java.io.Serializable;

/**
 * @description: 系统_资源
 * @date: 2019年12月20 14:32:42
 * @version: 1.0
 */
@Entity
@Getter
@Setter
public class SysMenu extends BaseEntity implements Serializable {

	@Id
    @TableId(type = IdType.ASSIGN_ID)
    @Column(length = 20)
	private String menuId;
	@ApiModelProperty(value = "标题")
    @Column(length = 50)
    private String name;
	@ApiModelProperty(value = "资源标识符")
    @Column(length = 50)
    private String identity;
	@ApiModelProperty(value = "菜单path")
    @Column(length = 50)
    private String path;
	@ApiModelProperty(value = "组件路径")
    @Column(length = 100)
	private String component;
	@ApiModelProperty(value = "父资源")
    @Column(length = 20)
    private String parentId;
	@ApiModelProperty(value = "菜单权重")
    @Column(length = 4)
    private Integer weight;
	@ApiModelProperty(value = "菜单图标")
    @Column(length = 50)
    private String icon;
	@ApiModelProperty(value = "菜单缓存")
	private Boolean isCache;
	@ApiModelProperty(value = " 资源状态（0=禁止1=显示）")
    @Column(length = 1)
    private Boolean status;
	@ApiModelProperty(value = "资源类型（0=目录1=菜单2=外链）")
    @Column(length = 1)
	private Integer menuType;
}
