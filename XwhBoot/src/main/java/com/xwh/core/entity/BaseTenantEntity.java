package com.xwh.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 租户字段表
 *
 * @author xwh
 **/
@Data//省略setget方法
@MappedSuperclass //标注父类
@EntityListeners(AuditingEntityListener.class) //jpa数据监听
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) //忽略解析的字段
public class BaseTenantEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(name = "租户ID", hidden = true)
    @Column(length = 30)
    @TableField("tenant_id")
    private String tenantId;

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
