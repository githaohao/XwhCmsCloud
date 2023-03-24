package com.xwh.system.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xwh.system.entity.SysMenu;

import java.util.List;

/**
 * @author xiangwenhao
 * @create 2022-02-24 14:52
 **/
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 排序
     * * @param nodeList
     *
     * @return
     */
    public List<Tree<String>> buildTreeData(List<SysMenu> nodeList);

    /**
     * 排序
     * * @param nodeList
     *
     * @return
     */
    public List<Tree<String>> buildSelectData(List<SysMenu> nodeList);


    /**
     * 通过id查询下级菜单数量
     *
     * @return
     */
    public long findChildrenCount(String pid);

    /**
     * 获取所有下级菜单
     *
     * @return
     */
    public List<SysMenu> children(String pid, List<SysMenu> menus);


    /**
     * 删除菜单
     *
     * @param id
     */
    public void del(String id);

    /**
     * 排除所有子菜单后的所有上级菜单
     *
     * @param id
     * @param menus
     * @return
     */
    public List<SysMenu> getSuperior(String id, List<SysMenu> menus);
    /**
     * 模糊查询并查询下级菜单
     *
     * @param menu
     * @return 包含查询菜单和子菜单
     */
    public List<SysMenu> search(SysMenu menu);
}
