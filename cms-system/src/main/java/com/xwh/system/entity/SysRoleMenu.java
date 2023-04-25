package com.xwh.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;




/**
 * @author xiangwenhao
 */
@Entity
@Getter
@Setter
public class SysRoleMenu implements Serializable {

    @Id
    @Column(length = 20)
    private String roleId;

    @Id
    @Column(length = 20)
	private String menuId;
}
