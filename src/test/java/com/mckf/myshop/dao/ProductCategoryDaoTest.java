package com.mckf.myshop.dao;

import com.mckf.myshop.BaseTest;
import com.mckf.myshop.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-16 16:31
 **/
public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    ProductCategoryDao productCategoryDao;

    @Test
    public void testQueryProductCategory()
    {
        List<ProductCategory> productCategoryList=productCategoryDao.queryProductCategory(1L);
        for(ProductCategory p:productCategoryList)
            System.out.println(p.toString());
    }
}
