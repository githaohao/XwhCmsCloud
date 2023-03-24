package com.xwh.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.xwh.system.entity.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author xiangwenhao
 * @description: 用户登录日志Mapper接口
 * @date: 2020年01月11 10:01:55
 * @version: 1.0
 */
@Mapper
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {

	/**
	 * @Title：list
	 * @Description: 用户登录日志分页查询
	 * @date 2020年01月11 10:01:55
	 * @param @param page
	 * @param @param map
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
    List<Map<String, Object>> list(@Param("page") Page<SysLoginLog> page, Map<String, Object> map);

	/**
	 * @Title：getAllIds
	 * @Description: 获取所有的日志id
	 * @date 2020年1月3日 下午4:42:24
	 * @param @return
	 * @return List<String>
	 * @throws
	 */
    List<String> getAllIds();
}
