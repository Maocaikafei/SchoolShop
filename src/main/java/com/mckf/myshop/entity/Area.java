package com.mckf.myshop.entity;

import java.util.Date;

/**
 * @program: MyShop
 * @description:区域实体类
 * @author: 冒菜咖啡~
 * @create: 2020-06-09 16:52
 **/
public class Area {
    private Integer areaId;
    private String areaName;
    //权重
    private Integer priority;
    private Date creatTime;
    private Date lastEditTime;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
