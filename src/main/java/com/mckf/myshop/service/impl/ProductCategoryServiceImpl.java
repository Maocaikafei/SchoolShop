package com.mckf.myshop.service.impl;

import com.mckf.myshop.dao.ProductCategoryDao;
import com.mckf.myshop.dto.ProductCategoryExecution;
import com.mckf.myshop.entity.ProductCategory;
import com.mckf.myshop.enums.ProductCategoryStateEnum;
import com.mckf.myshop.exceptions.ProductCategoryOperationException;
import com.mckf.myshop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-16 16:35
 **/
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryDao productCategoryDao;

    public List<ProductCategory> getProductCategory(Long shopId) {
        return productCategoryDao.queryProductCategory(shopId);
    }

    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
        throws ProductCategoryOperationException
    {
        if (productCategoryList!=null && productCategoryList.size()>0) {
            try {
                int effectNum=productCategoryDao.batchAddProductCategory(productCategoryList);
                if(effectNum<=0) {
                    throw new ProductCategoryOperationException("批量插入商品类别信息失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("批量插入商品类别信息失败"+e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }


}
