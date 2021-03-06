package com.mckf.myshop;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @program: MyShop
 * @description:配置spring和junit整合，junit启动时加载springIOC容器
 * @author: 冒菜咖啡~
 * @create: 2020-06-10 09:33
 **/

@RunWith(SpringJUnit4ClassRunner.class)
//告知junit spring配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class BaseTest {
}
