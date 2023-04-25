package com.xwh.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xwh.core.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统租户管理
 *
 * @author xwh
 **/
@Entity
@Getter
@Setter
@Table(name = "sys_tenant")
public class SysTenant extends BaseEntity implements Serializable {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @Column(length = 20)
    String tenantId;
    @Schema(name = "租户名")
    @Column(length = 100)
    String name;
    @Schema(name = "保留字段")
    @Column(length = 100)
    String isDomain;
    @Column(length = 200)
    @Schema(name = "租户介绍")
    String description;
    @Column(length = 20)
    @Schema(name = "租户开关")
    Boolean enabled;
    @Column(length = 2)
    @Schema(name = "租户级别:租戶等級3-普通，5-重要，8-核心")
    Integer magnitude;
    @Column(length = 20)
    @Schema(name = "手机号")
    String phone;
    @Column(length = 50)
    @Schema(name = "邮箱")
    String email;
}
