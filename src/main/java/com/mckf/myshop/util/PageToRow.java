package com.mckf.myshop.util;

/**
 * @program: MyShop
 * @description:将前端传过来的页码转换为数据库中对应的行数
 * @author: 冒菜咖啡~
 * @create: 2020-06-16 12:47
 **/
public class PageToRow {
    public static int calculateRowIndex(int pageIndex,int pageSize){
        return (pageIndex>0) ? (pageIndex-1)*pageSize : 0;
    }
}
