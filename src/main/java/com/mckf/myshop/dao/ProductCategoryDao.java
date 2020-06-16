package com.mckf.myshop.dao;

import com.mckf.myshop.entity.ProductCategory;
import com.mckf.myshop.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-16 16:25
 **/
public interface ProductCategoryDao {
    /**
    * @Description:根据店铺id获取店铺的商品分类
    * @Param: [shopId]
    * @return: java.util.List<com.mckf.myshop.entity.ProductCategory>
    * @Author: 冒菜咖啡
    * @Date: 2020/6/16 17:32
    */
    List<ProductCategory> queryProductCategory(Long shopId);

    /**
     * @Description:批量添加商品分类
     * @Param: [shopCategoryList]
     * @return: int
     * @Author: 冒菜咖啡
     * @Date: 2020/6/16 17:26
     */
    int batchAddProductCategory(@Param("productCategoryList")List<ProductCategory> productCategoryList);
}
