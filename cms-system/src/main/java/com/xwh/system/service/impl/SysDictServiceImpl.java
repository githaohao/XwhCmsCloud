package com.xwh.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.system.entity.SysDict;
import com.xwh.system.entity.SysDictDetail;
import com.xwh.system.mapper.SysDictDetailMapper;
import com.xwh.system.mapper.SysDictMapper;
import com.xwh.system.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xwh
 **/

@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService{

    final SysDictDetailMapper sysDictDetailMapper;

    /**
     * 获取当前字典
     *
     * @param name
     * @return
     */
    @Override
    public List<SysDictDetail> dict(String name) {
        return sysDictDetailMapper.selectByMap(Map.of("dict_id",name));
    }
}
