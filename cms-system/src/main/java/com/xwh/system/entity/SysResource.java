package com.xwh.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xwh.core.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

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

    @Schema(name = "是否随着服务更新而更新")
    @Column(length = 1)
    Boolean isUpdate;

    @Schema(name = "访问路径")
    @Column(length = 80)
    @Id
    String path;

    @Schema(name = "服务名")
    @Column(length = 30)
    String service;

    @Schema(name = "服务介绍")
    @Column(length = 100)
    String serviceDesc;

    @Schema(name = "接口描述")
    @Column(length = 100)
    String description;

    @Schema(name = "状态: 1启动, 0禁用")
    @Column(length = 1)
    Boolean status;

    @Schema(name = "控制器")
    @Column(length = 30)
    String controller;

    @Schema(name = "控制器详情")
    @Column(length = 100)
    String controllerDescription;

    @Schema(name = "是否公开,不做任何权限认证 1: 公开, 0: 需要认证")
    @Column(length = 1)
    Boolean isPublic;

    @Schema(name = "请求类型")
    @Column(length = 8)
    @Id
    String type;
}
