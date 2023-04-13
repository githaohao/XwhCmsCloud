package com.xwh.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xwh.core.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 接口权限管理
 *
 * @author xwh
 **/
@Entity
@Getter
@Setter
public class SysResource extends BaseEntity implements Serializable {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @Column(length = 20)
    String resourceId;

    @ApiModelProperty(value = "是否随着服务更新而更新")
    @Column(length = 1)
    Boolean isUpdate;

    @ApiModelProperty(value = "访问路径")
    @Column(length = 80)
    @Id
    String path;

    @ApiModelProperty(value = "服务名")
    @Column(length = 30)
    String service;

    @ApiModelProperty(value = "服务介绍")
    @Column(length = 100)
    String serviceDesc;

    @ApiModelProperty(value = "接口描述")
    @Column(length = 100)
    String description;

    @ApiModelProperty(value = "状态: 1启动, 0禁用")
    @Column(length = 1)
    Boolean status;

    @ApiModelProperty(value = "控制器")
    @Column(length = 30)
    String controller;

    @ApiModelProperty(value = "控制器详情")
    @Column(length = 100)
    String controllerDescription;

    @ApiModelProperty(value = "是否公开,不做任何权限认证 1: 公开, 0: 需要认证")
    @Column(length = 1)
    Boolean isPublic;

    @ApiModelProperty(value = "请求类型")
    @Column(length = 8)
    @Id
    String type;
}
