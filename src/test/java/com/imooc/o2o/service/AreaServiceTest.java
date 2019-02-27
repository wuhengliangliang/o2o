package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;

public class AreaServiceTest extends BaseTest {
	@Autowired
	private AreaService areaService;//用注解 表明，一旦AreaServiceTest（当前类用到了 AreaService）运行时候我们的spring就会去往里面注入他的实现类
	@Test
	public void testGetAreaList() {
		List<Area> areaList = areaService.getAreaList();
		assertEquals("东苑",areaList.get(0).getAreaName());
		for(Iterator iterator = areaList.iterator();iterator.hasNext();) {
			String i = iterator.next().toString();
			System.out.println(i);
		}
	}

}
