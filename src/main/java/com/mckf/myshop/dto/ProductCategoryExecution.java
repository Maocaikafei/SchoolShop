package com.mckf.myshop.dto;

import com.mckf.myshop.enums.ProductCategoryStateEnum;

import java.util.List;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-16 17:35
 **/
public class ProductCategoryExecution {
    // 结果状态
    private int state;
    // 状态标识
    private String stateInfo;

    private List<ProductCategoryExecution> productCategoryList;

    public ProductCategoryExecution() {

    }

    // 操作失败的时候使用的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 操作成功的时候使用的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategoryExecution> productCategoryList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
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

    public List<ProductCategoryExecution> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategoryExecution> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
