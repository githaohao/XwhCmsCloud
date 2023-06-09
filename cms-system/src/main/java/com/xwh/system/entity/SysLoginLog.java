package com.xwh.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xwh.core.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.Date;

/**
 * @description: 用户登录日志
 * @date: 2020年01月11 10:01:55
 * @version: 1.0
 */

@Getter
@Setter
@Entity
public class SysLoginLog extends BaseEntity implements Serializable{

	@Id
	@TableId(type = IdType.ASSIGN_ID)
	@Column(length = 20)
	private String logId;

	@Schema(name = "操作类型（1,登录;2,登出）")
	@Column(length = 1)
    private Integer operType;

	@Schema(name = " 登录结果类型(1,成功；2失败)")
	@Column(length = 1)
    private Integer resultType;

	@Schema(name = "操作IP地址")
	@Column(length = 30)
    private String remoteAddr;

	@Schema(name = "用户代理")
	@Column(length = 20)
    private String userAgent;

	@Schema(name = "操作时间（登录/退出的时间）")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

	@Schema(name = "地点")
	@Column(length = 30)
    private String address;

	@Schema(name = "浏览器")
	@Column(length = 30)
    private String browserType;

	@Schema(name = "登录类型 1=后台登录")
	@Column(length = 1)
    private Integer loginType;

	@Schema(name = "设备")
	@Column(length = 10)
    private String equipment;

	@Schema(name = "登录用户名")
	@Column(length = 20)
    private String loginUsername;

	@Schema(name = "操作备注")
	@Column(length = 100)
	String remarks;
}
