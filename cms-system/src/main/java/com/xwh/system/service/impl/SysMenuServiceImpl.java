package com.xwh.system.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.core.exception.FailException;
import com.xwh.core.utils.TokenUtil;
import com.xwh.system.entity.SysMenu;
import com.xwh.system.mapper.SysMenuMapper;
import com.xwh.system.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiangwenhao
 * @create 2022-02-24 14:52
 **/
@Service
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public boolean save(SysMenu sysMenu) {
        String userId = TokenUtil.getUserId();
        sysMenu.setCreateBy(userId);
        return super.save(sysMenu);
    }

    /**
     * 排序
     * * @param nodeList
     *
     * @return
     */
    @Override
    public List<Tree<String>> buildTreeData(List<SysMenu> nodeList) {
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        //关闭排序
//        treeNodeConfig.setWeightKey("weightOff");
        treeNodeConfig.setIdKey("menuId");
        // 自定义属性名 都要默认值的
        // 最大递归深度
        treeNodeConfig.setDeep(3);
        return TreeUtil.build(nodeList, "0", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(String.valueOf(treeNode.getMenuId()));
                    tree.setParentId(String.valueOf(treeNode.getParentId()));
                    if (StringUtils.isNotEmpty(treeNode.getComponent())) {
                        tree.putExtra("component", treeNode.getComponent());
                    }
                    tree.putExtra("path", treeNode.getPath());
                    tree.putExtra("weight", treeNode.getWeight());
                    tree.putExtra("menuType", treeNode.getMenuType());
                    tree.putExtra("status", treeNode.getStatus());
                    tree.putExtra("name", treeNode.getName());
                    tree.putExtra("cache", treeNode.getIsCache());
                    tree.putExtra("icon", treeNode.getIcon());
                    tree.putExtra("meta", MapUtil.builder("title", treeNode.getName())
                            .put("icon", treeNode.getIcon())
                            .put("cache", String.valueOf(treeNode.getIsCache()))
                            .build());
                });
    }

    /**
     * 排序
     * * @param nodeList
     *
     * @return
     */
    @Override
    public List<Tree<String>> buildSelectData(List<SysMenu> nodeList) {
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        //关闭排序
//        treeNodeConfig.setWeightKey("weightOff");
        treeNodeConfig.setIdKey("value");
        // 自定义属性名 都要默认值的
        // 最大递归深度
        treeNodeConfig.setDeep(3);
        return TreeUtil.build(nodeList, "0", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(String.valueOf(treeNode.getMenuId()));
                    tree.setParentId(String.valueOf(treeNode.getParentId()));
                    tree.putExtra("label", treeNode.getName());

                });
    }


    /**
     * 通过id查询下级菜单数量
     *
     * @return
     */
    @Override
    public long findChildrenCount(String pid) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setParentId(pid);
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        return count(query);
    }

    /**
     * 获取所有下级菜单
     *
     * @return
     */
    @Override
    public List<SysMenu> children(String pid, List<SysMenu> menus) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setParentId(pid);
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.setEntity(sysMenu);
        List<SysMenu> select = this.list(query);
        menus.addAll(select);
        for (SysMenu menu : select) {
            return children(menu.getMenuId(), menus);
        }
        return menus;
    }


    /**
     * 删除菜单
     *
     * @param id
     */
    @Override
    public void del(String id) {
        long i = findChildrenCount(id);
        if (i > 0) {
            throw new FailException("该菜单存在下级菜单,请先移除下级菜单");
        }

        this.removeById(id);
    }

    /**
     * 排除所有子菜单后的所有上级菜单
     *
     * @param id
     * @param menus
     * @return
     */
    @Override
    public List<SysMenu> getSuperior(String id, List<SysMenu> menus) {
        List<SysMenu> list = this.list();
        if ("0".equals(id)) {
            return list;
        }
        List<SysMenu> children = children(id, new ArrayList<>());
        List<String> ids = children.stream().map(SysMenu::getMenuId).collect(Collectors.toList());
        list.forEach(item -> {
            if (!ids.contains(item.getMenuId()) && !item.getMenuId().equals(id)) {
                menus.add(item);
            }
        });
        return menus;
    }

    /**
     * 模糊查询并查询下级菜单
     *
     * @param menu
     * @return 包含查询菜单和子菜单
     */
    @Override
    public List<SysMenu> search(SysMenu menu) {
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        if (menu.getName() != null) {
            query.like("name", menu.getName());
        }
        List<SysMenu> result = list(query);
        ArrayList<SysMenu> menus = new ArrayList<>(result);
        if (menu.getName() != null) {
            for (SysMenu sysMenu : result) {
                List<SysMenu> children = children(sysMenu.getMenuId(), new ArrayList<>());
                if (children.size() > 0) {
                    menus.addAll(children);
                }
            }
        }
        return menus;
    }
}
