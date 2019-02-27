package com.imooc.o2o.service;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;
	@Test
	public void testModifyShop()throws ShopOperationException,FileNotFoundException{
		Shop shop = new Shop();
		shop.setShopId(29L);
		shop.setShopName("修改后的店铺名称是彭亮");
	}
	
	
//	@Test
//	public void testAddShop() throws Exception {
//		Shop shop = new Shop();
//		PersonInfo owner = new PersonInfo();
//		owner.setUserId(1L);
//		shop.setOwner(owner);
//		Area area = new Area();
//		shop.setArea(area);
//		ShopCategory sc = new ShopCategory();
//		sc.setShopCategoryId(1L);
//		shop.setShopName("mytest1");
//		shop.setShopDesc("mytest1");
//		shop.setShopAddr("testaddr1");
//		shop.setPhone("13810524526");
//		shop.setShopImg("test1");
//		shop.setLongitude(1D);
//		shop.setLatitude(1D);
//		shop.setCreateTime(new Date());
//		shop.setLastEditTime(new Date());
//		shop.setEnableStatus(0);
//		shop.setAdvice("审核中");
//		shop.setArea(area);
//		shop.setShopCategory(sc);
//		ShopExecution se = shopService.addShop(shop);
//		assertEquals("mytest1", se.getShop().getShopName());
//	}


	@Test
	public void testGetByEmployeeId() throws Exception {
		long employeeId = 1;
		ShopExecution shopExecution = shopService.getByEmployeeId(employeeId);
		List<Shop> shopList = shopExecution.getShopList();
		for (Shop shop : shopList) {
			System.out.println(shop);
		}
	}

	@Ignore
	@Test
	public void testGetByShopId() throws Exception {
		long shopId = 1;
		Shop shop = shopService.getByShopId(shopId);
		System.out.println(shop);
	}

}

