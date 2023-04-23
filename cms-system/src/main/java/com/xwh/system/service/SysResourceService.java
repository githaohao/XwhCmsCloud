package com.xwh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwh.system.entity.SysResource;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The interface Sys resource service.
 *
 * @author xwh
 */
public interface SysResourceService extends IService<SysResource> {

    /**
     * 通过角色id查询所有的接口授权id
     *
     * @param roleId the role id
     * @return set
     */
    public Set<String> resourceIdsByRoleId(String roleId);

    /**
     * 去除所有重复的列
     */
    public void delGroupResource();

    /**
     * 返回权限的服务列表
     *
     * @return list
     */
    public List<Map<String, Object>> groupService();

    /**
     * 更新该服务的接口
     *
     * @param apiByPackage the api by package
     * @param service      the service
     */
    public void saveResourceIsUpdate(String apiByPackage, String service);

    /**
     * My list contains boolean.
     *
     * @param <E>        the type parameter
     * @param sourceList the source list
     * @param element    the element
     * @return the boolean
     */
    <E> boolean myListContains(List<E> sourceList, E element);

    boolean add(SysResource sysResource);
}
