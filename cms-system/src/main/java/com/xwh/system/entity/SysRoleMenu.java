package com.xwh.system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
