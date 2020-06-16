package com.mckf.myshop.dao;

import com.mckf.myshop.entity.Area;

import java.util.List;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-10 09:19
 **/
public interface AreaDao {
    /**
    * @Description:获取区域列表
    * @Param: []
    * @return: java.util.List<com.mckf.myshop.entity.Area>
    * @Author: 冒菜咖啡
    * @Date: 2020/6/10 9:19
    */
    List<Area> queryArea();
}
