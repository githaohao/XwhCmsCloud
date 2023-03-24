package com.xwh.system.entity.vo;
import cn.hutool.core.lang.tree.Tree;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;

/**
 * @ClassName: RoleMenuPerms
 * @Description: 角色菜单权限
 * @date 2019年12月15日 下午5:32:07
 */
@Getter
@Setter
public class RoleMenuPerms {

	private HashSet<String> permsList;

    private List<Tree<String>> menuList;
}
