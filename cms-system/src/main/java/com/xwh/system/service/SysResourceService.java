package com.xwh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwh.system.entity.SysResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xwh
 **/
public interface SysResourceService extends IService<SysResource> {

    /**
     * 通过角色id查询所有的接口授权id
     *
     * @return
     */
    public Set<String> resourceIdsByRoleId(String roleId);

    /**
     * 去除所有重复的列
     */
    public void delGroupResource();


    /**
     * 返回权限的服务列表
     *
     * @return
     */
    public ArrayList<Map<Object, Object>> groupService();


    /**
     *  更新该服务的接口
     * @param apiByPackage
     * @param service
     */
    public void saveResourceIsUpdate(String apiByPackage, String service);

    public <E> boolean myListContains(List<SysResource> sourceList, SysResource element);

}
