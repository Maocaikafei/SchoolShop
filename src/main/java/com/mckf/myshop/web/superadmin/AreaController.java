package com.mckf.myshop.web.superadmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mckf.myshop.entity.Area;
import com.mckf.myshop.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-10 10:24
 **/
@Controller
//涉及前端的都用小写
@RequestMapping("/superadmin")
public class AreaController {
    Logger logger = LoggerFactory.getLogger(AreaController.class);
    @Autowired
    AreaService areaService;
    @RequestMapping(value = "/listarea",method = RequestMethod.GET)
    //将返回结果转换为json对象
    @ResponseBody
    private Map<String,Object> listArea()
    {
        logger.info("===start===");
        long startTime=System.currentTimeMillis();
        Map<String,Object> modelMap=new HashMap<String,Object>();
        List<Area> areaList=new ArrayList<Area>();
        try
        {
           areaList=areaService.getAreaList();
           modelMap.put("rows",areaList);
            modelMap.put("total",areaList.size());
        }catch (Exception e)
        {
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
        }
        logger.error("test error");
        long endTime=System.currentTimeMillis();
        logger.debug("costTime:[{}ms]",endTime-startTime);
        logger.info("===end===");
        return modelMap;
    }
}
