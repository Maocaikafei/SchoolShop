package com.mckf.myshop.service;

import com.mckf.myshop.dto.ProductCategoryExecution;
import com.mckf.myshop.entity.ProductCategory;
import com.mckf.myshop.exceptions.ProductCategoryOperationException;

import java.util.List;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-16 16:33
 **/
public interface ProductCategoryService {
    /**
    * @Description:根据店铺id获取与该店铺有关的商品类别
    * @Param: [shopId]
    * @return: java.util.List<com.mckf.myshop.entity.ProductCategory>
    * @Author: 冒菜咖啡
    * @Date: 2020/6/16 16:34
    */
    List<ProductCategory> getProductCategory(Long shopId);

    /**
    * @Description:批量添加商品分类
    * @Param: [productCategoryList]
    * @return: com.mckf.myshop.dto.ProductCategoryExecution
    * @Author: 冒菜咖啡
    * @Date: 2020/6/16 17:43
    */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
    throws ProductCategoryOperationException;
}
