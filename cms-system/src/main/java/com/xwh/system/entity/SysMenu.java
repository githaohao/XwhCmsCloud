package com.xwh.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xwh.core.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


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
	@Schema(name = "标题")
    @Column(length = 50)
    private String name;
	@Schema(name = "资源标识符")
    @Column(length = 50)
    private String identity;
	@Schema(name = "菜单path")
    @Column(length = 50)
    private String path;
	@Schema(name = "组件路径")
    @Column(length = 100)
	private String component;
	@Schema(name = "父资源")
    @Column(length = 20)
    private String parentId;
	@Schema(name = "菜单权重")
    @Column(length = 4)
    private Integer weight;
	@Schema(name = "菜单图标")
    @Column(length = 50)
    private String icon;
	@Schema(name = "菜单缓存")
	private Boolean isCache;
	@Schema(name = " 资源状态（0=禁止1=显示）")
    @Column(length = 1)
    private Boolean status;
	@Schema(name = "资源类型（0=目录1=菜单2=外链）")
    @Column(length = 1)
	private Integer menuType;
}
