package com.xwh.system.entity.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: Menu
 * @Description: 前端按钮vo
 * @date 2019年12月15日 下午8:47:29
 */
@Getter
@Setter
public class Menu {
	private Long id;

	private String name;// 菜单名

	private String title;// 标题名

	private String icon; // 菜单图标

	private String path;// 访问路径

	private String component;// 组件名

	private String parentId;// 父id

	private String outLink;// 外部链接（0，否，1是）

	private String displayMode;// 显示方式（0，普通内嵌，1，内嵌iframe，2，外窗口）

}
