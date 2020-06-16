package com.mckf.myshop.service;

import com.mckf.myshop.BaseTest;
import com.mckf.myshop.dto.ProductCategoryExecution;
import com.mckf.myshop.entity.ProductCategory;
import com.mckf.myshop.enums.ProductCategoryStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-16 20:07
 **/
public class ProductCategoryServiceTest extends BaseTest {
    @Autowired
    ProductCategoryService productCategoryService;

    @Test
    public void testBatchAddProductCategory()
    {
        ProductCategory productCategory1=new ProductCategory();
        productCategory1.setProductCategoryName("包子");
        productCategory1.setPriority(5);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(1L);
        ProductCategory productCategory2=new ProductCategory();
        productCategory2.setProductCategoryName("稀粥");
        productCategory2.setPriority(3);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(1L);
        List<ProductCategory> productCategoryList=new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory1);
        productCategoryList.add(productCategory2);
        ProductCategoryExecution productCategoryExecution=productCategoryService
                .batchAddProductCategory(productCategoryList);
        assertEquals(ProductCategoryStateEnum.SUCCESS.getState(),productCategoryExecution.getState());

    }

}
