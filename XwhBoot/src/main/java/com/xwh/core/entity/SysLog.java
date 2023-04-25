package com.xwh.core.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xiangwenhao
 */
@Setter
@Getter
@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SysLog extends BaseEntity  implements Serializable {

	@Id
	private Long id;
	/**
	 * 用户操作
	 */
	private String operation;
	/**
	 * 日志内容
	 */
	private String content;
	/**
	 * 操作IP
	 */
	private String ip;
	/**
	 * 操作的方式
	 */
	private String method;
	/**
	 * 日志类型（1：接入日志；2：错误日志）
	 */
	private Integer type;

}
