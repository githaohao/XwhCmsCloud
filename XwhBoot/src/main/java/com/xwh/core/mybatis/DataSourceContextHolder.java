//package com.xwh.core.mybatis;
//
//
///**
// * @author xiangwenhao
// */
//public class DataSourceContextHolder {
//    private static final ThreadLocal<String> dataSourceLocal = new ThreadLocal<>();
//
//    public static ThreadLocal<String> getDataSourceLocal(){
//        return  dataSourceLocal;
//    }
//
//    /**
//     * 从库 可以有多个
//     */
//    public static void read(){
//        dataSourceLocal.set(TargetDataSource.READ.getCode());
//    }
//
//    /**
//     * 主库 只有一个
//     */
//    public static void write(){
//        dataSourceLocal.set(TargetDataSource.WRITE.getCode());
//    }
//
//    public static String getTargetDataSource(){
//        return dataSourceLocal.get();
//    }
//
//}
