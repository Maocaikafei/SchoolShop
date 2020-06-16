package com.mckf.myshop.dao;

import com.mckf.myshop.BaseTest;
import com.mckf.myshop.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-12 22:15
 **/
public class ShopCategoryDaoTest extends BaseTest {

    @Autowired
    ShopCategoryDao shopCategoryDao;

    @Test
    public void queryShopCategory()
    {
        List<ShopCategory> shopCategoryList=shopCategoryDao.queryShopCategory(new ShopCategory());
        for(ShopCategory shopCategory:shopCategoryList)
            System.out.println(shopCategory.toString());

        assertEquals(2,shopCategoryList.size());
    }

}
