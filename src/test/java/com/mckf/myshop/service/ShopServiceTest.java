package com.mckf.myshop.service;

import com.mckf.myshop.BaseTest;
import com.mckf.myshop.dto.ShopExecution;
import com.mckf.myshop.entity.Area;
import com.mckf.myshop.entity.PersonInfo;
import com.mckf.myshop.entity.Shop;
import com.mckf.myshop.entity.ShopCategory;
import com.mckf.myshop.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Date;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-12 14:44
 **/
public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void testUpdateShop()
    {
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopName("君自来");
        shop.setShopDesc("客栈哟");
        shop.setShopAddr("襄阳");
        shop.setPhone("1333333333");
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg=new File("F:/PS4/Horizon01.png");
        ShopExecution shopExecution=shopService.modifyShop(shop,shopImg);
        assertEquals(ShopStateEnum.SUCCESS.getState(),shopExecution.getState());
    }
    @Test
    public void addShop() throws InterruptedException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺5");
        shop.setShopDesc("test3");
        shop.setShopAddr("test3");
        shop.setPhone("test3");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg=new File("F:/PS4/Horizon01.png");
        ShopExecution shopExecution=shopService.addShop(shop,shopImg);
        assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getState());
    }

}
