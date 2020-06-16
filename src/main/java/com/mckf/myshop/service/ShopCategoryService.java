package com.mckf.myshop.service;

import com.mckf.myshop.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-12 23:23
 **/
public interface ShopCategoryService {
    List<ShopCategory> queryShopCategory(ShopCategory shopCategoryCondition);

}
