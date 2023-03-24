//package com.xwh.core.mybatis;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//
///**
// * @author xiangwenhao
// */
//@Configuration
//public class MyBatisConfig{
//
//    @Value("${spring.datasource.readSize}")
//    private String dataSourceSize;
//
//    @Autowired
//    private DataSource writeDataSource;
//
//    @Autowired
//    private DataSource readDataSource;
//
//    //直接点属性,说我没初始化,加个get方法就可以,这是在逗我
//    private DataSource getWriteDataSource(){
//        return writeDataSource;
//    }
//
//    private DataSource getReadDataSource(){
//        return readDataSource;
//    }
//
//    /**
//     * AbstractRoutingDataSource 这破玩意 继承了AbstractDataSource ,AbstractDataSource又实现了DataSource
//     * 所以可以直接丢去构建 SqlSessionFactory
//     * @return
//     */
//    @Bean
//    public AbstractRoutingDataSource dataSourceProxy(){
//        int size = Integer.parseInt(dataSourceSize);
//        MyRoutingDataSource proxy = new MyRoutingDataSource(size);
//        Map<Object,Object> dataSourceMap = new HashMap<>();
//        DataSource writeSource = getWriteDataSource();
//        DataSource readSource = getReadDataSource();
//
//        dataSourceMap.put(TargetDataSource.WRITE.getCode(),writeSource);
//        dataSourceMap.put(TargetDataSource.READ.getCode(),readSource);
//
//        proxy.setDefaultTargetDataSource(writeDataSource);
//        proxy.setTargetDataSources(dataSourceMap);
//        return proxy;
//    }
//
//
////    @Bean(name = "SqlSessionFactoryBeanExt")
////    public SqlSessionFactory sqlSessionFactorys() throws Exception {
////        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
////        bean.setDataSource(this.dataSourceProxy());
////        bean.setMapperLocations(this.getResources("classpath:**/mapper/*.xml"));
////        // 对象驼峰转下划线
////        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
////        return bean.getObject();
////    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//    private Resource[] getResources(String path) throws IOException {
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        return resolver.getResources(path);
//    }
//
//    private Resource getResource(String path) {
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        return resolver.getResource(path);
//    }
//}
