package com.mckf.myshop.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: MyShop
 * @description:提供数据源的类型
 * @author: 冒菜咖啡~
 * @create: 2020-06-14 21:15
 **/
public class DynamicDataSourceHolder {
    private static Logger logger= LoggerFactory.getLogger(DynamicDataSourceHolder.class);
    //contextHolder用来保存Key，即指定DataSource类型
    private static ThreadLocal<String> contextHolder=new ThreadLocal<String>();
    public static final String DB_MASTER="master";
    public static final String DB_SLAVE="slave";

    /**
    * @Description:获取线程的contextHolder
    * @Param: []
    * @return: java.lang.String
    * @Author: 冒菜咖啡
    * @Date: 2020/6/14 21:26
    */
    public static String getDbType(){
        String db=contextHolder.get();
        if(db==null){
            //如果db为空，返回DB_MASTER，因为master库可读可写
            db=DB_MASTER;
        }
        return db;
    }

    /**
    * @Description:设置线程的contextHolder
    * @Param: []
    * @return: void
    * @Author: 冒菜咖啡
    * @Date: 2020/6/14 21:26
    */
    public static void setDbType(String str){
        logger.info("使用的数据源是："+str);
        contextHolder.set(str);
    }

    /**
    * @Description:清理数据
    * @Param: []
    * @return: void
    * @Author: 冒菜咖啡
    * @Date: 2020/6/14 21:28
    */
    public static void clearDbType(){
        contextHolder.remove();
    }
}
