package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.ShopService;

//在这个下面应当注入spring文件 而注入的操作我已经放入BaseTest类中 所以我需要去继承 BaseTest 否则找不到ShopDao
public class ShopDaoTest extends BaseTest{
	@Autowired
	private	ShopDao shopDao;
	@Autowired
	private ShopService shopService;
	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(20L);
		shopCondition.setShopCategory(sc);
		ShopExecution se = shopService.getShopList(shopCondition, 1, 2);
		System.out.println("店铺列表数："+se.getShopList().size());
		System.out.println("店铺列表数："+se.getCount());
	}
	@Test
	public void testQueryShopList() {
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		List <Shop>shopList = shopDao.queryShopList(shopCondition, 0, 5);
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println(shopList);
		System.out.println("店铺总数:"+count);
		System.out.println("----------------------------------------");
		ShopCategory sr = new ShopCategory();
		sr.setShopCategoryId(20L);
		shopCondition.setShopCategory(sr);
		shopList = shopDao.queryShopList(shopCondition, 0, 3);
		System.out.println(shopList);
		count  = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺总数:"+count);
	}
	
	@Test
	@Ignore
	public void testQueryByShopId() {
		long shopId = 15;
		Shop shop =shopDao.queryByShopId(shopId);
		System.out.println(shop.getArea().getAreaName());
		System.out.println(shop.getArea().getAreaId());
		
	}
	
	
	
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(1L);
		shopCategory.setShopCategoryId(10L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺");
		shop.setShopDesc("test");
		shop.setShopAddr("阜阳师范");
		shop.setPhone("1384838438");
		shop.setShopImg("pengliang");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("正在审核");
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
		
	}
	
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(29L);
		shop.setShopDesc("非常棒");
		shop.setShopAddr("阜阳师范学院计算机信息工程");
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
		
	}
	
}
