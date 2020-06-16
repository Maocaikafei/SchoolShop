package com.mckf.myshop.dao;

import com.mckf.myshop.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: MyShop
 * @description: 店铺
 * @author: 冒菜咖啡~
 * @create: 2020-06-11 13:25
 **/
public interface ShopDao {

    /**
    * @Description:根据店铺id获取店铺信息
    * @Param: [shopId]
    * @return: com.mckf.myshop.entity.Shop
    * @Author: 冒菜咖啡
    * @Date: 2020/6/15 21:29
    */
    Shop queryShopById(Long shopId);

    /**
    * @Description:新增店铺
    * @Param: [shop]
    * @return: int 表示影响的行数，返回-1表示插入失败（Mybaties返回的)
    * @Author: 冒菜咖啡
    * @Date: 2020/6/11 13:26
    */
    int insertShop(Shop shop);

    /**
    * @Description:更新店铺信息
    * @Param: [shop]
    * @return: int
    * @Author: 冒菜咖啡
    * @Date: 2020/6/11 13:59
    */
    int updateShop(Shop shop);

    /**
    * @Description:（根据用户id，区域id，店铺类别）分页查询店铺
    * @Param: [shopCondition 存放查询条件, rowIndex 查询的起始行号, pageSize 每页存放的数据数量]
    * @return: java.util.List<com.mckf.myshop.entity.Shop>
    * @Author: 冒菜咖啡
    * @Date: 2020/6/16 10:50
    */
    List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,@Param("rowIndex")int rowIndex,
                           @Param("pageSize")int pageSize);

    /**
    * @Description:（根据用户id，区域id，店铺类别）查询的店铺总数
    * @Param: [shopCondition]
    * @return: int
    * @Author: 冒菜咖啡
    * @Date: 2020/6/16 10:54
    */
    int queryShopCount(@Param("shopCondition")Shop shopCondition);
}