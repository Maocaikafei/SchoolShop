package com.mckf.myshop.service.impl;

import com.mckf.myshop.dao.ShopDao;
import com.mckf.myshop.dto.ShopExecution;
import com.mckf.myshop.entity.Shop;
import com.mckf.myshop.enums.ShopStateEnum;
import com.mckf.myshop.exceptions.ShopOperationException;
import com.mckf.myshop.service.ShopService;
import com.mckf.myshop.util.ImageUtil;
import com.mckf.myshop.util.PageToRow;
import com.mckf.myshop.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-12 10:13
 **/
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Transactional
    public ShopExecution addShop(Shop shop, File shopImg)
    {
        if (shop==null)
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        try {
            // 给店铺信息赋初始值
            shop.setEnableStatus(ShopStateEnum.CHECK.getState());
            shop.setAdvice(ShopStateEnum.CHECK.getStateInfo());
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //向数据库插入店铺信息
            int effectNum=shopDao.insertShop(shop);
            if (effectNum<=0) {
              throw new ShopOperationException("创建店铺失败");
            }
            else {
                if (shopImg!=null){
                    boolean addImg=true;
                    try {
                        //为店铺添加图片信息
                        addShopImg(shop,shopImg);
                    }catch (Exception e) {
                        addImg=false;
                        throw new ShopOperationException("addShopImg error:"+e.getMessage());
                    }
                    if(addImg=true){
                        //如果图片信息添加成功，就更新店铺信息
                        effectNum=shopDao.updateShop(shop);
                        if(effectNum<=0)
                            throw new ShopOperationException("更新店铺图片地址失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("addShop error："+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    public Shop queryShopById(Long shopId) {
        return shopDao.queryShopById(shopId);
    }

    @Transactional
    public ShopExecution modifyShop(Shop shop, File img) throws ShopOperationException {
        if(shop==null)
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        if (shop.getShopId()==null)
            return new ShopExecution(ShopStateEnum.NULL_SHOPID);
        //如果图片不为空，就更新图片
        if(img!=null) {
            try {
                addShopImg(shop,img);
            } catch (Exception e){
                throw new ShopOperationException("update shopImg error:"+e.getMessage());
            }
        }
        try {
            shop.setLastEditTime(new Date());
            int effectNum=shopDao.updateShop(shop);
            if (effectNum<=0)
               return new ShopExecution(ShopStateEnum.INNER_ERROR);
            else {
                shop=shopDao.queryShopById(shop.getShopId());
                return new ShopExecution(ShopStateEnum.SUCCESS,shop);
            }
        } catch (Exception e){
            throw new ShopOperationException("update shop error:"+e.getMessage());
        }
    }

    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        ShopExecution shopExecution;
        int rowIndex= PageToRow.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        if (shopList == null)
            shopExecution = new ShopExecution(ShopStateEnum.INNER_ERROR);
        else{
            shopExecution = new ShopExecution(ShopStateEnum.SUCCESS, shopList);
            shopExecution.setCount(count);
        }

        return shopExecution;
    }

    private void addShopImg(Shop shop,File shopImg)
    {
        // 获取shop图片目录的相对路径
        String dest= PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr= ImageUtil.generateThumbnail(shopImg,dest);
        shop.setShopImg(shopImgAddr);
    }
}
