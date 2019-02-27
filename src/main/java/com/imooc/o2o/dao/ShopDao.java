package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 插入店铺
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	/**
	 * 更新店铺信息
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
	
	/**
	 * 查询商店列表 分页查询店铺，可输入的条件有:店铺名，店铺状态，店铺类别，区域id,owner
	 * @param shopCondition
	 * @param rowIndex 从第几行开始取数据
	 * @param pageSize  返回多少行数据
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,@Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);
	/**
	 * 查询店铺数量
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition")Shop shopCondition);
	
	/**
	 * 返回queryShopList总数
	 * @param employeeId
	 * @return
	 */
	List<Shop> queryByEmployeeId(long employeeId);
	/**
	 * 通过shop id 查询店铺
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);
	
}
