package com.mckf.myshop.service.impl;

import com.mckf.myshop.dao.ShopCategoryDao;
import com.mckf.myshop.entity.ShopCategory;
import com.mckf.myshop.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-12 23:24
 **/
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    ShopCategoryDao shopCategoryDao;

    public List<ShopCategory> queryShopCategory(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }
}
