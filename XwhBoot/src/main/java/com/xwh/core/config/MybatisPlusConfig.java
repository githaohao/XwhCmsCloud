package com.xwh.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.xwh.core.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MybatisPlusConfig {

    private final TenantProperties properties;

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        //多租户插件
        if (properties.getEnable()) {
            interceptor.addInnerInterceptor(tenantLineInnerInterceptor());
        }
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 防止全表更新与删除插件: BlockAttackInnerInterceptor
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 乐观锁插件 当要更新一条记录的时候，希望这条记录没有被别人更新
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        return interceptor;
    }


    public TenantLineInnerInterceptor tenantLineInnerInterceptor() {
        return new TenantLineInnerInterceptor(new TenantLineHandler() {
            /**
             * 获取租户ID
             * @return Expression
             */
            @Override
            public Expression getTenantId() {
                String tenant = TokenUtil.getUserTenant();
                if (tenant != null) {
                    return new StringValue(tenant);
                }
                return new NullValue();
            }

            /**
             * 获取多租户的字段名
             * @return String
             */
            @Override
            public String getTenantIdColumn() {
                return properties.getColumn();
            }

            /**
             * 过滤不需要根据租户隔离的表
             * 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
             * @param tableName 表名
             */
            @Override
            public boolean ignoreTable(String tableName) {
//                String isAdmin = TokenUtil.getUserIsAdmin();
                //超级管理员直接管理所有租户
//                if (StringUtils.isNotBlank(isAdmin) && "1".equals(isAdmin)) {
//                    return true;
//                }
                return !properties.getTables().contains(tableName);
            }

        });
    }

}
