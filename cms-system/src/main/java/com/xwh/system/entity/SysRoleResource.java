package com.xwh.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

/**
 * 角色接口关联表
 *
 * @author xwh
 **/

@Entity
@Getter
@Setter
public class SysRoleResource implements Serializable {
    @Id
    @Column(length = 20)
    String roleId;

    @Id
    @Column(length = 20)
    String resourceId;
}
