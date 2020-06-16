package com.mckf.myshop.service;

import com.mckf.myshop.dto.ShopExecution;
import com.mckf.myshop.entity.Shop;
import com.mckf.myshop.exceptions.ShopOperationException;
import org.apache.ibatis.annotations.Param;

import java.io.File;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-12 10:12
 **/
public interface ShopService {

    /**
     * @Description:添加店铺信息
     * @Param: [shop, ShopImg]
     * @return: com.mckf.myshop.dto.ShopExecution
     * @Author: 冒菜咖啡
     * @Date: 2020/6/12 10:13
     */
    ShopExecution addShop(Shop shop, File ShopImg);

    /**
     * @Description:根据id获取店铺信息
     * @Param: [shopId]
     * @return: com.mckf.myshop.entity.Shop
     * @Author: 冒菜咖啡
     * @Date: 2020/6/15 22:11
     */
    Shop queryShopById(Long shopId);

    /**
     * @Description:更新店铺信息
     * @Param: [shop, img]
     * @return: com.mckf.myshop.dto.ShopExecution
     * @Author: 冒菜咖啡
     * @Date: 2020/6/15 22:18
     */
    ShopExecution modifyShop(Shop shop, File img) throws ShopOperationException;

    /**
     * @Description:（根据用户id，区域id，店铺类别）分页查询店铺
     * @Param: [shopCondition, rowIndex, pageSize]
     * @return: com.mckf.myshop.dto.ShopExecution
     * @Author: 冒菜咖啡
     * @Date: 2020/6/16 12:41
     */
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
}