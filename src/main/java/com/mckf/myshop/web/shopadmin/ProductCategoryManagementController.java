package com.mckf.myshop.web.shopadmin;

import com.mckf.myshop.dao.ProductCategoryDao;
import com.mckf.myshop.dto.ProductCategoryExecution;
import com.mckf.myshop.entity.ProductCategory;
import com.mckf.myshop.entity.Shop;
import com.mckf.myshop.entity.ShopCategory;
import com.mckf.myshop.enums.ProductCategoryStateEnum;
import com.mckf.myshop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-16 16:41
 **/
@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {

    @Autowired
    ProductCategoryService productCategoryService;

    @RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductCategoryList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Shop shop = (Shop) request.getSession().getAttribute("currentShop");
        try {
            if (shop != null && shop.getShopId() != null) {
                List<ProductCategory> productCategoryList = productCategoryService
                        .getProductCategory(shop.getShopId());
                modelMap.put("data", productCategoryList);
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errorMsg", "shop为空");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errorMsg", e.toString());
        }
        return modelMap;
    }

    @RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProductCategory
            (@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request)
    {
        Map<String,Object> modelMap=new HashMap<String,Object>();
        ProductCategoryExecution execution;
        Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
        for(ProductCategory p:productCategoryList) {
            p.setCreateTime(new Date());
            p.setShopId(currentShop.getShopId());
        }
        if (productCategoryList!=null && productCategoryList.size()>0) {
            try {
                execution= productCategoryService.batchAddProductCategory(productCategoryList);
                if (execution.getState()== ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                } else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",execution.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请至少输入一个商品类别");
        }
        return modelMap;
    }
}
