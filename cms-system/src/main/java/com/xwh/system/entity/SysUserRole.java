package com.xwh.system.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

/**
 * @description: 系统_用户角色
 * @date: 2019年12月20 14:32:42
 * @version: 1.0
 */

@Getter
@Setter
@Entity
public class SysUserRole implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @Id
    @Column(length = 20)
    private String roleId;
    /**
     * 用户id
     */
    @Id
    @Column(length = 20)
    private String userId;

}
