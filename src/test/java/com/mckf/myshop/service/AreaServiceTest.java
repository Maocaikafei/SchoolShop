package com.mckf.myshop.service;

import com.mckf.myshop.BaseTest;
import com.mckf.myshop.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-10 10:16
 **/
public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAreaList()
    {
        List<Area> areaList=areaService.getAreaList();
        assertEquals("兰苑",areaList.get(1).getAreaName());
    }

}
