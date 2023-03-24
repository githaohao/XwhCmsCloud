package com.xwh.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwh.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description: 系统_用户Mapper接口
 * @date: 2019年12月19 17:17:39
 * @version: 1.0
 */

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

	/**
	 * @Title：updateLoginResult
	 * @Description: 根据登录结果更新登录次数时间信息
	 * @date 2019年12月20日 上午11:39:53
	 * @param @param username
	 * @param @param bool
	 * @return void
	 * @throws
	 */
    void updateLoginResult(@Param("username") String username, @Param("bool") Boolean bool);

}
