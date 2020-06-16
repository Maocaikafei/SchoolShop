package com.mckf.myshop.service.impl;

import com.mckf.myshop.dao.AreaDao;
import com.mckf.myshop.entity.Area;
import com.mckf.myshop.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-10 10:13
 **/
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    AreaDao areaDao;

    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
