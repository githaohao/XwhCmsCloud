package com.xwh.system.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xwh.core.entity.BaseTenantEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


/**
 * 系统:用户
 *
 * @author xiangwenhao
 */
@Entity
@Setter
@Getter
public class SysUser extends BaseTenantEntity implements Serializable {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @Column(length = 20)
    @ApiModelProperty(value = "用户id")
    private String userId;


    @ApiModelProperty(value = "用户名称")
    @Column(length = 50)
    private String username;

    @ApiModelProperty(value = "用户姓名")
    @Column(length = 30)
    private String name;

    @ApiModelProperty(value = "昵称")
    @Column(length = 50)
    private String nickname;

    @ApiModelProperty(value = "头像地址")
    @Column(length = 100)
    private String avatar;

    /**
     * 用户状态【1启用、0禁用】
     */
    @ColumnDefault("1")
    @ApiModelProperty(value = "状态1=启动2=禁用")
    @Column(length = 20)
    private Boolean status;

    /**
     * 密码
     */
    @JSONField(serialize = false)
    @Column(length = 20)
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 是否是超级管理员
     */
    @ApiModelProperty(value = "是否是超级管理员")
    @Column(length = 1)
    @ColumnDefault("0")
    private Integer isAdmin;

    @ApiModelProperty(value = "邮箱")
    @Column(length = 50)
    private String email;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @Column(length = 30)
    private String mobile;


    @ApiModelProperty(value = "是否锁定【1是、0否】")
    @Column(length = 1)
    private Boolean isLock;
    /**
     * 锁定时间
     */
    @ApiModelProperty(value = "锁定到时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lockTime;

    /**
     * 登录次数
     */
    @ApiModelProperty(value = "登录次数")
    private Long loginCount;

    /**
     * 登录Ip
     */
    @ApiModelProperty(value = "登录Ip")
    @Column(length = 30)
    private String loginIp;

    /**
     * 登录时间
     */
    @ApiModelProperty(value = "登录时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;


    @ApiModelProperty(value = "用户角色id")
    @Column(length = 200)
    private String roleId;

}
