package com.xwh.core.threads;

import cn.hutool.extra.spring.SpringUtil;
import com.xwh.core.dao.LogDao;
import com.xwh.core.entity.SysLog;
import jakarta.servlet.http.HttpServletRequest;


/**
 * @ClassName: SaveLogThread
 * @Description: 保存日志线程
 */
public class SaveLogThread extends Thread {
	private static final LogDao logDao = SpringUtil.getBean(LogDao.class);

	private final SysLog lv;

	private final HttpServletRequest request;

	public SaveLogThread(SysLog lv, HttpServletRequest request) {
		super(SaveLogThread.class.getSimpleName());
		this.lv = lv;
		this.request = request;
	}

	@Override
	public void run() {
		lv.setCreateTimeAndUpdateTimeNow();
		// 保存日志信息
		logDao.saveLog(lv);
	}
}
