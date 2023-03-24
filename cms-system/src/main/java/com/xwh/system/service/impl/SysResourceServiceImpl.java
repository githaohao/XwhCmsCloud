package com.xwh.system.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.system.entity.SysResource;
import com.xwh.system.mapper.SysResourceMapper;
import com.xwh.system.service.SysResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xwh
 **/
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements SysResourceService {

    final SysResourceMapper sysResourceMapper;

    /**
     * 通过角色id查询所有的接口授权id
     *
     * @return
     */
    @Override
    public Set<String> resourceIdsByRoleId(String roleId) {
        return sysResourceMapper.resourceIdsByRoleId(roleId);
    }

    /**
     * 去除所有重复的列
     */
    @Override
    public void delGroupResource() {
        List<SysResource> sysResourceList = sysResourceMapper.groupByResource();
        for (SysResource sysResource : sysResourceList) {
            sysResourceMapper.deleteByMap(Map.of(
                    "is_update", 1,
                    "path", sysResource.getPath(),
                    "type", sysResource.getType()));
        }
    }


    /**
     * 返回权限的服务列表
     *
     * @return
     */
    @Override
    public ArrayList<Map<Object, Object>> groupService() {
        List<SysResource> sysResourceList = sysResourceMapper.listByGroupController();
        Map<String, List<SysResource>> map = CollStreamUtil.groupByKey(sysResourceList, SysResource::getService);
        ArrayList<Map<Object, Object>> list = new ArrayList<>();
        for (String s : map.keySet()) {
            HashMap<Object, Object> sysService = new HashMap<>();
            sysService.put("children", map.get(s));
            sysService.put("service", s);
            sysService.put("controller", map.get(s).get(0).getController());
            sysService.put("controllerDescription", map.get(s).get(0).getServiceDesc());
            list.add(sysService);
        }
        return list;
    }


    /**
     *  更新该服务的接口
     * @param apiByPackage
     * @param service
     */
    @Override
    public void saveResourceIsUpdate(String str, String service) {
        //本次接口和数据库中的接口进行对比
        // 查询当前 service
        List<String> apiByPackage = JSONArray.parseArray(str, String.class);
        List<SysResource> list1 = listByMap(Map.of("is_update", 1,"service", service));
        List<SysResource> list = apiByPackage.stream().map(item -> JSON.parseObject(item,SysResource.class)).collect(Collectors.toList());

        List<SysResource> addList = new ArrayList<>();
        List<SysResource> reduceList = new ArrayList<>();
        List<SysResource> editList = new ArrayList<>();


        //判断本次新增的数据
        for (SysResource resource : list) {
            if (myListContains(list1, resource)) {
                addList.add(resource);
            }
        }

        //判断本次减少的数据
        for (SysResource sysResource : list1) {
            if (myListContains(list, sysResource)) {
                reduceList.add(sysResource);
            }
        }

        //判断本次编辑的数据
        for (SysResource sysResource : list) {
            for (SysResource tip : list1) {
                String str1 = tip.getPath() + tip.getType();
                String str2 = sysResource.getPath() + sysResource.getType();
                // 变更部分
                String str3 = sysResource.getDescription() +sysResource.getControllerDescription() +sysResource.getServiceDesc();
                String str4 = tip.getDescription() +tip.getControllerDescription() +tip.getServiceDesc();
                if (StringUtils.equals(str1, str2)) {
                    // 存在变更项
                    if (!StringUtils.equals(str3, str4) && tip.getIsUpdate()) {
                        sysResource.setResourceId(tip.getResourceId());
                        sysResource.setIsUpdate(tip.getIsUpdate());
                        sysResource.setStatus(tip.getStatus());
                        sysResource.setIsPublic(tip.getIsPublic());
                        editList.add(sysResource);
                    }
                }
            }
        }


        // 对减少数据进行删除
        for (SysResource s : reduceList) {
            removeById(s);
            log.info("系统_接口管理_删除接口 {}:{}", s.getType(),s.getPath());
            // 删除所有绑定的所有角色
            System.out.println(s);
            removeByMap(Map.of("resourceId", s));
        }

        //对于新增的数据进行添加操作
        for (SysResource s : addList) {
            s.setStatus(true);
            s.setIsUpdate(true);
            s.setIsPublic(false);
            log.info("系统_接口管理_新增接口 {}:{}", s.getType(),s.getPath());
            save(s);
        }

        //对于需要编辑的数据进行编辑
        for (SysResource s : editList) {
            log.info("系统_接口管理_编辑接口 {}:{}", s.getType(),s.getPath());
            updateById(s);
        }

    }
    @Override

    public <E> boolean myListContains(List<SysResource> sourceList, SysResource element) {
        if (sourceList == null || element == null) {
            return true;
        }
        if (sourceList.isEmpty()) {
            return true;
        }
        for (SysResource tip : sourceList) {
            String str1 = tip.getPath() + tip.getType();
            String str2 = element.getPath() + element.getType();
            if (str1.equals(str2)) {
                return false;
            }
        }
        return true;
    }

}
