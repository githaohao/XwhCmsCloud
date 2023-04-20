package com.xwh.core.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseService<T> {

    final BaseMapper<T> mapper;

    public List<T> list(QueryWrapper<T> queryWrapper) {
        return mapper.selectList(queryWrapper);
    }

    // 其他方法（如：添加、修改、删除等）的实现
}
