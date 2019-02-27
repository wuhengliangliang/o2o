package com.imooc.o2o.service;

import java.io.InputStream;
import java.util.List;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exceptions.ProductOperationException;

public interface ProductService {
	/**
	 * 商品信息处理
	 * 
	 * @param product 
	 * @param thumbnail
	 * @return
	 */
	
	ProductExecution addProduct(Product product,ImageHolder thumbnail,List<ImageHolder>productImgList)throws ProductOperationException;

	 /**
	  * 添加商品的分页
	  * @param productCondition
	  * @param pageIndex
	  * @param pageSize
	  * @return
	  */
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
 	
	/**
	 * 通过商品id查询唯一的商品信息
	 * @param productId
	 * @return
	 */
	Product getProductById(long productId);
	
	/**
	 * 修改商品信息以及图片处理
	 * @param product
	 * @param thumbnail
	 * @param productImgHolderList
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution modifyProduct(Product product,ImageHolder thumbnail,List<ImageHolder>productImgHolderList) throws ProductOperationException;
	
	
}
