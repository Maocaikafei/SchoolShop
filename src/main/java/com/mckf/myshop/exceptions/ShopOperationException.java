package com.mckf.myshop.exceptions;

/**
 * @program: MyShop
 * @description：一个自定义异常类，做了很浅的封装
 * @author: 冒菜咖啡~
 * @create: 2020-06-12 14:41
 **/
public class ShopOperationException extends RuntimeException {
    public ShopOperationException(String msg){
        super(msg);
    }
}
