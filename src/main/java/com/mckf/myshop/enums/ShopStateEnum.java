package com.mckf.myshop.enums;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-12 09:33
 **/
public enum ShopStateEnum {
    CHECK(0, "审核中"), OFFLINE(-1, "非法店铺"), SUCCESS(1, "操作成功"),
    PASS(2, "通过认证"),INNER_ERROR(-1001,"内部系统错误"),
    NULL_SHOPID(-1002, "ShopId为空"),NULL_SHOP(-1003, "shop信息为空");

    private int state;
    private String stateInfo;

    //我一开始还在疑惑，既然这个构造函数是private的，干嘛还要传参，反正都用不到
    //原来不传参的话 上面的那些枚举都会报错
    private ShopStateEnum(int state,String stateInfo)
    {
        this.state=state;
        this.stateInfo=stateInfo;
    };

    public static ShopStateEnum stateOf(int state)
    {
        for(ShopStateEnum stateEnum:values())
        {
            if(stateEnum.getState()==state)
                return stateEnum;
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
