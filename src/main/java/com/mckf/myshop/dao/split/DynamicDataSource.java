package com.mckf.myshop.dao.split;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @program: MyShop
 * @description:用于实现读写分离
 * @author: 冒菜咖啡~
 * @create: 2020-06-14 21:08
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDbType();
    }
}
