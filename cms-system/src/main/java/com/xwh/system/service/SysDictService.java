package com.xwh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwh.system.entity.SysDict;
import com.xwh.system.entity.SysDictDetail;

import java.util.List;

/**
 * @author xwh
 **/

public interface SysDictService extends IService<SysDict> {

    /**
     * 获取当前字典
     *
     * @param name
     * @return
     */
    public List<SysDictDetail> dict(String name);
}
