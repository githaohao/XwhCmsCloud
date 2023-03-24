package com.xwh.system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
