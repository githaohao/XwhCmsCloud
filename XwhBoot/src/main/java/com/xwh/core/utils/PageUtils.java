package com.xwh.core.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xwh.core.dao.Page;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class PageUtils {

    public static <E> E convert(Object oldClass, Class<E> newClass) {
        // 判断oldClass 是否为空!
        if (oldClass == null) {
            return null;
        }
        // 判断newClass 是否为空
        if (newClass == null) {
            return null;
        }
        try {
            // 创建新的对象实例
            E newInstance = newClass.newInstance();
            // 把原对象数据拷贝到新的对象
            BeanUtils.copyProperties(oldClass, newInstance);
            // 返回新对象
            return newInstance;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T, V> List<V> listVoStream(List<T> oldList, Class<V> v) {
        List<V> voList = oldList.stream().map(item -> {
            try {
                return (V) PageUtils.convert(item, v.newInstance().getClass());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return voList;
    }

    public static <T, E> IPage<T> copy(IPage page, List<E> sourceList, Class<T> targetClazz) {
        IPage pageResult = new Page(page.getCurrent(),page.getSize());
        pageResult.setPages(page.getPages());
        List<T> records = PageUtils.listVoStream(sourceList, targetClazz);
        pageResult.setRecords(records);
        return pageResult;
    }
    public static <T, E> IPage<T> copy(IPage page, Class<T> targetClazz) {
        return copy(page,page.getRecords(),targetClazz);
    }

}
