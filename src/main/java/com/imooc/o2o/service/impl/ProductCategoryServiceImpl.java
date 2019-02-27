package com.imooc.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductCategoryService;

@Service //用sping来托管这个类
public  class ProductCategoryServiceImpl implements ProductCategoryService{
	@Autowired
	private ProductDao productDao;
	@Autowired //sping 运行时候动态注入 productCategoryDao mybatis 的实现类
	private ProductCategoryDao productCategoryDao;  //商品类别信息  早dao层，然后 在xml文件中生成sql查询
	@Override
	public List<ProductCategory> getProductCategoryList(Long shopId) {
		
		return productCategoryDao.queryProductCategoryList(shopId);
	}
	@Override
	@Transactional //实现 店铺类别的增加
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {
		if(productCategoryList!=null&&productCategoryList.size()>0) {
			try {
				int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if(effectedNum<=0) {
					throw new ProductCategoryOperationException("店铺类别创建失败");
				}else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
			}catch(Exception e) {
				throw new ProductCategoryOperationException("batchAddProductCategory error:"+e.getMessage());
			}
		}else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
		
	}
	@Override
	@Transactional
	//实现店铺类别的删除
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		// todo 将此商品类别下的商品类别id置为空
		try {
			int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
			if(effectedNum<0) {
				throw new ProductCategoryOperationException("商品类别更新失败");
			}
		}catch (Exception e) {
			throw new ProductCategoryOperationException("delete productCategory error :"+e.toString());
		}
		//删除该productCategory
		try {
			int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if(effectedNum<0) {
				throw new ProductCategoryOperationException("商品类别删除失败");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		}catch(Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory error:"+e.getMessage()); 
		}
		
	}
	
	
}
