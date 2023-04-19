package com.xwh.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@CreatedBy
	@ApiModelProperty(hidden = true)
	@Column(length = 20)
	private String createBy;

	@LastModifiedBy
	@ApiModelProperty(hidden = true)
	@Column(length = 20)
	private String updateBy;

	@CreatedDate
	@ApiModelProperty(hidden = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@LastModifiedDate
	@ApiModelProperty(hidden = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	public void setCreateTimeAndUpdateTimeNow() {
		Date now = new Date();
		this.createTime = now;
		this.updateTime = now;
	}


//	@Override
//	public String toString() {
//		ToStringBuilder builder = new ToStringBuilder(this);
//		Field[] fields = this.getClass().getDeclaredFields();
//		try {
//			for (Field f : fields) {
//				f.setAccessible(true);
//				builder.append(f.getName(), f.get(this)).append("\n");
//			}
//		} catch (Exception e) {
//			builder.append("toString builder encounter an error");
//		}
//		return builder.toString();
//	}
}
