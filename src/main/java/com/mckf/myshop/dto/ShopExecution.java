package com.mckf.myshop.dto;

import com.mckf.myshop.entity.Shop;
import com.mckf.myshop.enums.ShopStateEnum;

import java.util.List;

/**
 * @program: MyShop
 * @description:用来存放关于shop增删改查操作的结果
 * @author: 冒菜咖啡~
 * @create: 2020-06-12 09:24
 **/
public class ShopExecution {
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //店铺数量，因为操作的可能不止一个店铺
    private int count;

    private Shop shop;

    private List<Shop> shopList;

    // 店铺操作失败的时候使用的构造器
    public ShopExecution(ShopStateEnum shopStateEnum)
    {
        this.state=shopStateEnum.getState();
        this.stateInfo=shopStateEnum.getStateInfo();
    }

    // 店铺操作成功的时候使用的构造器
    public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }

    // 店铺操作成功的时候使用的构造器
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
