package com.mckf.myshop.web.shopadmin;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mckf.myshop.dto.ShopExecution;
import com.mckf.myshop.entity.Area;
import com.mckf.myshop.entity.PersonInfo;
import com.mckf.myshop.entity.Shop;
import com.mckf.myshop.entity.ShopCategory;
import com.mckf.myshop.enums.ShopStateEnum;
import com.mckf.myshop.service.AreaService;
import com.mckf.myshop.service.ShopCategoryService;
import com.mckf.myshop.service.ShopService;
import com.mckf.myshop.util.CodeUtil;
import com.mckf.myshop.util.HttpServletRequestUtil;
import com.mckf.myshop.util.ImageUtil;
import com.mckf.myshop.web.superadmin.AreaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-12 17:21
 **/
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController
{
    @Autowired
    ShopService shopService;
    @Autowired
    AreaService areaService;
    @Autowired
    ShopCategoryService shopCategoryService;

    Logger logger = LoggerFactory.getLogger(ShopManagementController.class);


    /**
    * @Description:通过userId获取店铺列表
    * @Param: [request]
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Author: 冒菜咖啡
    * @Date: 2020/6/16 13:12
    */
    @RequestMapping(value = "getshoplist",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopList(HttpServletRequest request)
    {
        logger.debug("触发了getShopList");
        Map<String,Object> modelMap=new HashMap<String,Object>();
        PersonInfo user=new PersonInfo();
        user.setUserId(1L);//因为登录尚未实现，所以session部分手动写了
        request.getSession().setAttribute("user",user);
        user= (PersonInfo) request.getSession().getAttribute("user");
        Shop shopCondition=new Shop();
        shopCondition.setOwner(user);
        try {
            ShopExecution shopExecution=shopService.getShopList(shopCondition,0,100);
            // 列出店铺成功之后，将店铺放入session中作为权限验证依据，即该帐号只能操作它自己的店铺
            request.getSession().setAttribute("shopList",shopExecution.getShopList());
            modelMap.put("shopList",shopExecution.getShopList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        logger.debug("触发了getShopManagementInfo");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId <= 0) {
            //如果没有传入shopId,就检查session里是否有currentShop
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {
                //如果session里没有currentShop，说明是非法访问，重定向到店铺列表
                modelMap.put("redirect", true);
                modelMap.put("url", "shopadmin/getshoplist");
            } else {
                //如果session里有currentShop，添加到返回结果中
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        } else {
            //如果有传入shopId，将其添加到session中
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("shopId", shopId);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }
    /**
    * @Description:获取店铺初始化信息（区域，类型等选项列表）
    * @Param: []
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Author: 冒菜咖啡
    * @Date: 2020/6/15 23:30
    */
    @RequestMapping(value = "getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopInitInfo()
    {
        logger.debug("触发了getShopInitInfo");
        Map<String,Object> modelMap=new HashMap<String,Object>();
        List<Area> areaList=new ArrayList<Area>();
        List<ShopCategory> shopCategoryList=new ArrayList<ShopCategory>();
        try{
            areaList=areaService.getAreaList();
            shopCategoryList=shopCategoryService.queryShopCategory(new ShopCategory());
            modelMap.put("success",true);
            modelMap.put("areaList",areaList);
            modelMap.put("shopCategoryList",shopCategoryList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg","获取列初始化信息失败");
        }
        return modelMap;
    }


    /**
    * @Description:注册店铺
    * @Param: [request]
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Author: 冒菜咖啡
    * @Date: 2020/6/15 23:30
    */
    @RequestMapping(value = "registershop",method = RequestMethod.POST)
    private Map<String,Object> registerShop(HttpServletRequest request)
    {
        Map<String,Object> modelMap=new HashMap<String,Object>();
        //检查验证码
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 1.接收并转化相应的参数，包括店铺信息以及图片信息
        String shopStr=HttpServletRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper=new ObjectMapper();
        Shop shop=null;
        try {
            shop=mapper.readValue(shopStr,Shop.class);
        } catch (IOException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }

        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }

        // 2.注册店铺
        if(shop!=null && shopImg!=null) {
            PersonInfo owner= (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            File file=ImageUtil.transferCommonsMultipartFileToFile(shopImg);
            ShopExecution shopExecution=shopService.addShop(shop,file);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "店铺信息有误");
        }
        return modelMap;
    }

    /**
    * @Description:根据店铺id获取店铺信息
    * @Param: [request]
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Author: 冒菜咖啡
    * @Date: 2020/6/15 23:31
    */
    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopById(HttpServletRequest request){
        logger.debug("触发了getShopById");
        Map<String,Object> modelMap=new HashMap<String,Object>();
        long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        logger.debug("shopId:"+shopId);
        if(shopId==-1) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "shopId为空");
            return modelMap;
        }
        try {
            Shop shop=shopService.queryShopById(shopId);
            List<Area> areaList=areaService.getAreaList();
            modelMap.put("success", true);
            modelMap.put("shop", shop);
            logger.debug("shop:"+shop.toString());
            modelMap.put("areaList", areaList);
            }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    @RequestMapping(value = "modifyshop",method = RequestMethod.POST)
    private Map<String,Object> modifyShop(HttpServletRequest request)
    {
        logger.debug("触发了modifyShop");
        Map<String,Object> modelMap=new HashMap<String,Object>();
        //检查验证码
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 1.接收并转化相应的参数，包括店铺信息以及图片信息
        String shopStr=HttpServletRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper=new ObjectMapper();
        Shop shop=null;
        try {
            shop=mapper.readValue(shopStr,Shop.class);
        } catch (IOException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }

        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }

        // 2.修改店铺信息
        if(shop!=null && shop.getShopId()!=null) {
            try {
                File file=null;
                if(shopImg!=null)
                    ImageUtil.transferCommonsMultipartFileToFile(shopImg);
                ShopExecution shopExecution=shopService.modifyShop(shop,file);
                if(shopExecution.getState()== ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "修改店铺信息失败");
                }
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺id");
        }
        return modelMap;
    }

    @RequestMapping(value = "shopoperation")
    private String shopOperation()
    {
        return "/shop/shopoperation";
    }
    @RequestMapping(value = "shoplist")
    private String shopList()
    {
        return "/shop/shoplist";
    }
    @RequestMapping(value = "shopmanagement")
    private String shopManagement()
    {
        return "/shop/shopmanagement";
    }
    @RequestMapping(value = "productcategorymanagement")
    private String productCategoryManagement()
    {
        return "/shop/productcategorymanagement";
    }
    @RequestMapping(value = "addproductcategorys")
    private String addProductCategorys()
    {
        return "/shop/productcategorymanagement";
    }
}


