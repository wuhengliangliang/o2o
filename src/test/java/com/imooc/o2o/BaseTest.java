package com.imooc.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 用来配置spring和junit整合，junit启动加载spring IOC容器
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//高数junit spring 配置文件所在的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class BaseTest {
	
}
