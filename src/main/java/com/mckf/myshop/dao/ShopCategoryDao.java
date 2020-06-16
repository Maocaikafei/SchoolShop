package com.mckf.myshop.dao;

import com.mckf.myshop.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-12 22:02
 **/
public interface ShopCategoryDao {
    /**
    * @Description:获取父类为指定类型的商铺分类信息
    * @Param: [shopCategoryCondition]
    * @return: java.util.List<com.mckf.myshop.entity.ShopCategory>
    * @Author: 冒菜咖啡
    * @Date: 2020/6/16 17:25
    */
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);

}
