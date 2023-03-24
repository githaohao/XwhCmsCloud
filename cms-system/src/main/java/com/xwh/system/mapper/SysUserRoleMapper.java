package com.xwh.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwh.system.entity.SysRole;
import com.xwh.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 系统_用户角色Mapper接口
 * @date: 2019年12月19 15:26:08
 * @version: 1.0
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

	/**
	 * @Title：list
	 * @Description: 系统_用户角色分页查询
	 * @date 2019年12月19 15:26:08
	 * @param @param page
	 * @param @param map
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
//	public List<Map<String, Object>> list(@Param("page") Page<SysUserRole> page, Map<String, Object> map);

	/**
	 * @Title：listByUserId
	 * @Description: 根据用户id获得角色列表
	 * @date 2019年12月20日 下午2:03:32
	 * @param @param userId
	 * @param @param roleId
	 * @param @return
	 * @return List<SysRole>
	 * @throws
	 */
	List<SysRole> listByUserId(@Param("userId") String userId, @Param("roleId") String roleId);
	/**
	 * roleIdListByUserId
	 * @Description: 根据用户id来获取用户所拥有的角色id
	 * @date 2019年12月20日 下午2:01:37
	 * @param @param userId
	 * @param @return
	 * @return List<SysRole>
	 * @throws
	 */
//	public List<String> roleIdListByUserId(@Param("userId") String userId);

	/**
	 * @Title：deleteBatchByRoleIds
	 * @Description: 根据roleid list集合来删除
	 * @date 2019年12月27日 下午2:05:07
	 * @param @param roleIdList
	 * @param @param userId
	 * @return void
	 * @throws
	 */
//	public void deleteBatchByRoleIds(@Param("roleIdList") List<String> roleIdList, @Param("userId") String userId);

}
