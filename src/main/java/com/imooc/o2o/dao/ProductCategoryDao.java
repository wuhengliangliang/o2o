package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.ProductCategory;
/**
 * 通过shop id ?查询店铺商铺类别
 * 
 * @author Administrator
 * @param long
 * 	shopId
 * @return List<ProductCategory>
 */
//商品管理的接口  实现的过程在  mapper mybatis的接口的sql的实现
public interface ProductCategoryDao {
	/**
	 * 通过shopId 查询到商铺类别
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);
	/**
	 * 批量增加商品类别
	 *  在dao中实现sql语句
	 * @param shopId
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory>productCategorylist);
	
	/**
	 * 删除指定商品类别
	 * 由于是两个参数 mybatis认不出来 ，只能勇敢 @Param 来作为参数标记 
	 * @param productCategoryId
	 * @param shopId
	 * @return effectedNum
	 */
	
	int deleteProductCategory(@Param("productCategoryId")long productCategoryId,@Param("shopId")long shopId);
	
	
}
