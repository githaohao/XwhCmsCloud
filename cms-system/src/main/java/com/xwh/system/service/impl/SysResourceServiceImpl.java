package com.xwh.system.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.xwh.core.exception.FailException;
import com.xwh.system.entity.SysResource;
import com.xwh.system.entity.SysRoleResource;
import com.xwh.system.mapper.SysResourceMapper;
import com.xwh.system.service.SysResourceService;
import com.xwh.system.service.SysRoleResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    final SysRoleResourceService sysRoleResourceService;

    /**
     * 通过角色id查询所有的接口授权id
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
        sysResourceList.stream().<Map<String, Object>>map(sysResource -> Map.of(
                "is_update", 1,
                "path", sysResource.getPath(),
                "type", sysResource.getType())).forEach(sysResourceMapper::deleteByMap);
    }


    /**
     * 返回权限的服务列表
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> groupService() {
        List<SysResource> sysResourceList = sysResourceMapper.listByGroupController();
        Map<String, List<SysResource>> map = CollStreamUtil.groupByKey(sysResourceList, SysResource::getService);

        return map.entrySet().stream()
                .map(entry -> Map.of(
                        "children", entry.getValue(),
                        "service", entry.getKey(),
                        "controller", entry.getValue().get(0).getController(),
                        "controllerDescription", entry.getValue().get(0).getServiceDesc()))
                .collect(Collectors.toList());
    }


    /**
     *  更新该服务的接口
     * @param service
     */
    @Override
    public void saveResourceIsUpdate(String str, String service) {
        // 将输入 JSON 解析为 SysResource 对象列表
        List<SysResource> apiList = new Gson().fromJson(str, new TypeToken<List<SysResource>>(){}.getType());

        // 查询给定服务和 is_update = 1 的现有 SysResource 对象
        List<SysResource> existingApiList = listByMap(Map.of("is_update", 1,"service", service));

        // 对比识别新的、删除的和编辑的 SysResource 对象
        List<SysResource> newApiList = apiList.stream()
                .filter(api -> StringUtils.isNotBlank(api.getController()))
                .filter(api -> existingApiList.stream().noneMatch(existingApi -> StringUtils.equals(existingApi.getPath() + existingApi.getType(), api.getPath() + api.getType())))
                .peek(api -> {
                    api.setStatus(true);
                    api.setIsUpdate(true);
                    api.setIsPublic(false);
                    save(api);
                    log.info("系统_接口管理_新增接口 {}:{}", api.getType(), api.getPath());
                })
                .collect(Collectors.toList());

        List<SysResource> removedApiList = existingApiList.stream()
                .filter(existingApi -> apiList.stream().noneMatch(api -> StringUtils.equals(existingApi.getPath() + existingApi.getType(), api.getPath() + api.getType())))
                .peek(existingApi -> {
                    removeById(existingApi);
                    log.info("系统_接口管理_删除接口 {}:{}", existingApi.getType(), existingApi.getPath());
                    // Delete all role-resource mappings that contain the removed resource
                    QueryWrapper<SysRoleResource> query = new QueryWrapper<>();
                    query.eq("resource_id", existingApi.getResourceId());
                    sysRoleResourceService.remove(query);
                })
                .collect(Collectors.toList());

        List<SysResource> editedApiList = apiList.stream()
                .filter(api -> StringUtils.isNotBlank(api.getController()))
                .flatMap(api -> existingApiList.stream()
                        .filter(existingApi -> StringUtils.equals(existingApi.getPath() + existingApi.getType(), api.getPath() + api.getType()))
                        .filter(existingApi -> !StringUtils.equals(existingApi.getDescription() + existingApi.getControllerDescription() + existingApi.getServiceDesc(), api.getDescription() + api.getControllerDescription() + api.getServiceDesc()))
                        .filter(existingApi -> existingApi.getIsUpdate())
                        .peek(existingApi -> {
                            api.setResourceId(existingApi.getResourceId());
                            api.setIsUpdate(existingApi.getIsUpdate());
                            api.setStatus(existingApi.getStatus());
                            api.setIsPublic(existingApi.getIsPublic());
                            updateById(api);
                            log.info("系统_接口管理_编辑接口 {}:{}", api.getType(), api.getPath());
                        }))
                .collect(Collectors.toList());
    }

    /**
     *
     * 根据元素的路径和类型检查列表是否包含元素。
     * @author xwh
     * @param sourceList 要检查的列表。
     * @param element 要检查的元素。
     * @param <E> 列表中元素的类型。 @return 如果列表包含该元素则为真，否则为假。
     */
    @Override
    public <E> boolean myListContains(List<E> sourceList, E element) {
        if (sourceList == null || element == null) {
            return false;
        }
        String elementKey = ((SysResource) element).getPath() + ((SysResource) element).getType();
        return sourceList.stream()
                .filter(e -> e instanceof SysResource)
                .map(e -> (SysResource) e)
                .anyMatch(e -> ((e.getPath() + e.getType()).equals(elementKey)));
    }

    @Override
    public boolean add(SysResource sysResource) {
        // 从路径中提取控制器和服务
        String path = sysResource.getPath();
        String[] split = path.split("/");
        String service = split[1];
        String controller = split[2];

        // 使用service和controller查询serviceDesc和controllerDesc
        List<SysResource> list = list(new QueryWrapper<SysResource>().eq("service", service).eq("controller", controller));
        if (!list.isEmpty()) {
            SysResource res = list.get(0);
            sysResource.setServiceDesc(res.getServiceDesc());
            sysResource.setControllerDescription(res.getControllerDescription());
        }

        // 将 isUpdate 设置为 true
        sysResource.setService(service);
        sysResource.setController(controller);

        //检查重复接口
        boolean exists = count(new QueryWrapper<SysResource>().eq("path", path).eq("type", sysResource.getType())) > 0;
        if (exists) {
            throw new FailException("当前接口已存在");
        }
        // 保存界面
        return save(sysResource);
    }

}
