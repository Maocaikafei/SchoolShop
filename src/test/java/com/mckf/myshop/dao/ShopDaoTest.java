package com.mckf.myshop.dao;

import com.mckf.myshop.BaseTest;
import com.mckf.myshop.entity.Area;
import com.mckf.myshop.entity.PersonInfo;
import com.mckf.myshop.entity.Shop;
import com.mckf.myshop.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-11 13:49
 **/
public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    public void testQueryShopListAndQueryShopCount()
    {
        ShopCategory shopCategory=new ShopCategory();
        shopCategory.setShopCategoryId(1L);
        Shop shop=new Shop();
        shop.setShopCategory(shopCategory);
        List<Shop> shopList=shopDao.queryShopList(shop,0,2);
        for(Shop s:shopList)
            System.out.println(s.toString());

        System.out.println("页内数据条数："+shopList.size());
        int count=shopDao.queryShopCount(shop);
        System.out.println("满足条件数据条数："+count);
    }

    @Test
    public void testQueryShopById()
    {
        Shop shop=shopDao.queryShopById(1L);
        System.out.println(shop.toString());
    }

    @Test
    public void testInsertShop()
    {
        Shop shop=new Shop();
        PersonInfo owner=new PersonInfo();
        Area area=new Area();
        ShopCategory shopCategory=new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setArea(area);
        shop.setAdvice("审核中");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setLastEditTime(new Date());
        shop.setOwner(owner);
        shop.setPhone("test");
        shop.setPriority(1);
        shop.setShopAddr("test");
        shop.setShopCategory(shopCategory);
        shop.setShopDesc("test");
        shop.setShopImg("test");
        shop.setShopName("测试的店铺6");

        int effectedNum=shopDao.insertShop(shop);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testUpdateShop()
    {
        Shop shop=new Shop();
        shop.setLastEditTime(new Date());
        shop.setShopAddr("测试地址");
        shop.setShopDesc("测试描述");
        shop.setShopId(1L);
        int effectedNum=shopDao.updateShop(shop);
        assertEquals(1,effectedNum);
    }

}
