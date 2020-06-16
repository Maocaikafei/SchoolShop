package com.mckf.myshop.dao;

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
 * @create: 2020-06-10 09:31
 **/
public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea()
    {
        List<Area> areaList=areaDao.queryArea();
        for(Area area:areaList)
            System.out.println(area.getAreaId()+area.getAreaName()+area.getPriority());
        assertEquals(2,areaList.size());
    }
}
