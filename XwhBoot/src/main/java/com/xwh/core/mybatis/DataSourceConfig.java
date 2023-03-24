//package com.xwh.core.mybatis;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//public class DataSourceConfig {
//    @Value("${spring.datasource.type}")
//    private Class<? extends DataSource> dataSourceType;
//
//    @Bean(name = "writeDataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.write")
//    public DataSource writeDataSource() {
//        return DataSourceBuilder.create().type(dataSourceType).build();
//    }
//
//    /**
//     * 有多少个从库就要配置多少个
//     * @return
//     */
//    @Bean(name = "readDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.read")
//    public DataSource readDataSourceOne() {
//        return DataSourceBuilder.create().type(dataSourceType).build();
//    }
//
//}
