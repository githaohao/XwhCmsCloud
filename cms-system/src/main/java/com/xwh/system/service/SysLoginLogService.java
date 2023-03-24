package com.xwh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwh.core.dao.Page;
import com.xwh.system.entity.SysLoginLog;

/**
 * @author xiangwenhao
 * @create 2022-02-09 22:42
 **/
public interface SysLoginLogService extends IService<SysLoginLog> {
    /**
     * 保存登录日志
     *
     * @param operType
     * @param resultType
     * @param msg
     */
    public void saveLoginLog(String username, Integer operType, Integer resultType, Integer loginType, String msg);

    /**
     * 查询当前用户的登录日志
     *
     * @param query
     * @return
     */
    public Page<SysLoginLog> getPageByUser(Page<SysLoginLog> page);
}
