package com.mckf.myshop.util;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-11 15:00
 **/
public class PathUtil {
    /**
    * @Description:返回图片保存的根路径
    * @Param: []
    * @return: java.lang.String
    * @Author: 冒菜咖啡
    * @Date: 2020/6/11 15:32
    */
    public static String getImgBasePath()
    {
        return "E:/myShopImg/";
    }

    /**
    * @Description:返回相对路径
    * @Param: [shopId]
    * @return: java.lang.String
    * @Author: 冒菜咖啡
    * @Date: 2020/6/11 15:33
    */
    public static String getShopImagePath(long shopId)
    {
        String imagePath="upload/shop/"+shopId+"/";
        return imagePath;
    }

}
