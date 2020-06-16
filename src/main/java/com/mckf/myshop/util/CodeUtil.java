package com.mckf.myshop.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-13 10:27
 **/
public class CodeUtil {
    /**
    * @Description:检查验证码是否正确
    * @Param: [request]
    * @return: boolean
    * @Author: 冒菜咖啡
    * @Date: 2020/6/13 10:28
    */
    public static boolean checkVerifyCode(HttpServletRequest request) {
        String verifyCodeExpected = (String) request.getSession()
                .getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
        if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
            return false;
        }
        return true;
    }
}
