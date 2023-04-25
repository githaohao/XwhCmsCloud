package com.xwh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.core.dao.Page;
import com.xwh.core.utils.*;
import com.xwh.system.entity.SysLoginLog;
import com.xwh.system.mapper.SysLoginLogMapper;
import com.xwh.system.service.SysLoginLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.util.Date;

/**
 * @author xiangwenhao
 * @create 2022-02-09 22:42
 **/
@Service
@RequiredArgsConstructor
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {
    final SysLoginLogMapper sysLoginLogMapper;

    /**
     * 保存登录日志
     * @param username
     * @param operType
     * @param resultType
     * @param loginType
     * @param msg
     */
    @Override
    public void saveLoginLog(String username, Integer operType, Integer resultType, Integer loginType, String msg) {
        HttpServletRequest request = ServletUtils.getRequest();
        if (BlankUtils.isNotBlank(username)) {
            SysLoginLog sysLoginLog = new SysLoginLog();
            sysLoginLog.setOperType(operType);
            sysLoginLog.setResultType(resultType);
            sysLoginLog.setRemarks(msg);
            // 获取浏览器类型
            sysLoginLog.setBrowserType(BrowserUtils.checkBrowse(request));
            sysLoginLog.setOperTime(new Date());
            // 获取设备类型以及设备
            if (DeviceUtils.isMobileDevice(request)) {
                sysLoginLog.setEquipment("手机");
            } else {
                sysLoginLog.setEquipment("电脑");
            }
            sysLoginLog.setLoginType(loginType);
            // 获取ip地址
            String ip = StringUtils.getRemoteAddr(request);
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                try {
                    ip = StringUtils.getRealIp();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
            sysLoginLog.setRemoteAddr(ip);
            // 获取用户代理
            sysLoginLog.setUserAgent(BrowserUtils.checkBrowse(request));
            sysLoginLog.setLoginUsername(username);
            save(sysLoginLog);
        }
    }

    /**
     * 查询当前用户的登录日志
     *
     * @return
     */
    @Override
    public Page<SysLoginLog> getPageByUser(Page<SysLoginLog> pageParam) {
        String userId = TokenUtil.getUsernameFromToken();
        QueryWrapper<SysLoginLog> query = new QueryWrapper<>();
        query.eq("login_username", userId).orderByAsc("oper_time");
        Page<SysLoginLog> page = page(pageParam, query);
        return page;
    }
}
