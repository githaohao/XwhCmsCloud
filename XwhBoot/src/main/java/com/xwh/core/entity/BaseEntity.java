package com.xwh.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

@Data//省略setget方法
@MappedSuperclass //标注父类
@EntityListeners(AuditingEntityListener.class) //jpa数据监听
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) //忽略解析的字段
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@CreatedBy
	@ApiModelProperty(value = "创建人", hidden = true)
	@Column(length = 20)
	private String createBy;

	@LastModifiedBy
	@ApiModelProperty(value = "更新人", hidden = true)
	@Column(length = 20)
	private String updateBy;

	@CreatedDate
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", hidden = true)
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@LastModifiedDate
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新时间", hidden = true)
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	/**
	 * 插入之前执行方法，需要手动调用
	 */
	public void preInsert() {
		this.updateTime = new Date();
		this.createTime = this.updateTime;
	}

	/**
	 * 插入之前执行方法，需要手动调用
	 */
	public void preUpdate() {
		this.updateTime = new Date();
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		Field[] fields = this.getClass().getDeclaredFields();
		try {
			for (Field f : fields) {
				f.setAccessible(true);
				builder.append(f.getName(), f.get(this)).append("\n");
			}
		} catch (Exception e) {
			builder.append("toString builder encounter an error");
		}
		return builder.toString();
	}
}
