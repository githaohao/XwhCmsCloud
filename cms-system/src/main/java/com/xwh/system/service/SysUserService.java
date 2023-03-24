package com.xwh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwh.system.entity.SysUser;

import java.util.Map;

/**
 * @author xiangwenhao
 * @create 2022-02-09 22:39
 **/
public interface SysUserService extends IService<SysUser> {


    /**
     * @param @param sysUser
     * @return void
     * @throws
     * @Title：saveUserInfo
     * @Description: 保存用户信息
     * @date 2019年12月26日 下午4:51:51
     */
    public void saveUserInfo(SysUser sysUser);

    /**
     * 更新用户的角色列表
     */
    public void saveUserRoleInfo(String userId, String roleId);

    // 登录获取用户信息
    public SysUser login(String username, String password);


    /**
     * @param @param  username
     * @param @param  password
     * @param @param  menuPerms
     * @param @return
     * @return Map<String, Object>
     * @throws
     * @Title：getLoginDatas
     * @Description: 获取登录后的信息
     * @author: xiangwenhao
     * @date 2020年01月20日 下午2:06:23
     */
    public Map<String, Object> getLoginDatas(String username, boolean menuPerms);

    /**
     * @param @param username
     * @param @param bool
     * @return void
     * @throws
     * @Title：updateLoginResult
     * @Description: 根据登录结果更新登录次数时间信息
     * @author: xiangwenhao
     */
    public void updateLoginResult(String username, Boolean bool);

    public SysUser selectByUsername(String username);

    /**
     * 根据username和password查询
     *
     * @param username
     * @param password
     * @return
     */
    public SysUser getByUsernameAndPassword(String username, String password);

    /**
     * 根据email和password查询
     *
     * @param email
     * @param password
     * @return
     */
    public SysUser getByEmailAndPassword(String email, String password);

    /**
     * 根据mobile和password查询
     *
     * @param mobile
     * @param password
     * @return
     */
    public SysUser getByMobileAndPassword(String mobile, String password);

    /**
     * 修改用户 密码
     *
     * @param user
     * @param newpwd
     */
    public void updatePwd(SysUser user, String newpwd);

    /**
     * 通过 id 删除用户相关的
     *
     * @param split
     */
    public void delByIds(String[] split);

}
