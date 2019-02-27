package com.imooc.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.FileUtil;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;

@Service //这个注解表明sping 要当成service 管理起来
public  class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;  //动态注入他们的实现类
	@Autowired
	private ProductImgDao productImgDao;
	
	@Override
	@Transactional //通过spring的事物管理 来回滚事物
	
	
	
	/**
	 * 操作步骤
	 * 1.处理缩略图，获取缩略图相对路径并赋值给product，添加商品
	 * 
	 * 2.往tb_product写入 商品信息，获取productId
	 * 
	 * 3.结合productId批量处理商品详情图
	 * 
	 * 4.往商品详情列表批量插入tb_product_img中
	 * 
	 */
	
	//  添加商品
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
			throws ProductOperationException {
		// TODO Auto-generated method stub
		//必须标明  product属于哪个店铺
		if(product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null) {
			//给商品设置 默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			//默认为上架 的状态 这样就可以在前端展示
			product.setEnableStatus(1); //上架
			if(thumbnail!=null) {
				addThumbnail(product,thumbnail); //缩略图的处理
			}
			try {
				//创建商品
				int effectedNum = productDao.insertProduct(product); //插入几个商品
				if(effectedNum<=0) {
					throw new ProductOperationException("创建商品失败：");
				}
			}catch(Exception e) {
				throw new ProductOperationException("创建商品失败"+e.toString());
			}
			//若商品详情图片不为空，则添加 
			if(productImgHolderList!=null&&productImgHolderList.size()>0) {
				addProductImgList(product,productImgHolderList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS,product);
		}else {
			//传参为空则返回空值错误信息
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
			
	}

	/**
	 * 添加缩略图
	 * 
	 * @param product
	 * @param thumbnail
	 */
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		// TODO Auto-generated method stub
		String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest); //生成相应的缩略图，返回图片的相对路径
		product.setImgAddr(thumbnailAddr);
	}
	
	
	private void addProductImgList(Product product,List<ImageHolder>productImgHolerList) {
		//存储图片路径，这里会直接存放到相应的店铺的文件夹下面
		String dest  = FileUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg>productImgList = new ArrayList<ProductImg>();
		//遍历图片一次去处理，并且加进productImg的实体类当中
		for(ImageHolder productImgHolder:productImgHolerList) {
			String imgAddr = ImageUtil.generateNormalImg(productImgHolder,dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		//如果确实是有图片需要添加的，就执行批量添加操作
		if(productImgList.size()>0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if(effectedNum<=0) {
					throw new ProductOperationException("创建商品详情图片失败");
				}
			}catch(Exception e){
				throw new ProductOperationException("创建商品详情图片失败"+e.toString());
			}
		}
		
	}

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		//页面装换成数据库的行码，并调用dao层取回指定页码的商品列表
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product>productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		//基于同样的查询条件返回该查询 条件下的商品总数
		int count = productDao.queryProductCount(productCondition);
		ProductExecution pe = new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}

	//通过商品id查询商品
	@Override
	public Product getProductById(long productId) {
		
		return productDao.queryProductById(productId);
	}

	@Override
	@Transactional
	//1.若缩略图参数有值，则处理缩略图，
	//2.若原先存在缩略图则先删除再添加新的缩略图
	//将tb_product_img 下面的该商品原先的商品详情图记录全部删除
	// 更新tb_product的信息
	
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
			List<ImageHolder> productImgHolderList) throws ProductOperationException {
		//空值判断
		if(product!=null&& product.getShop()!=null&&product.getShop().getShopId()!=null) {
			//给商品设置默认属性
			product.setLastEditTime(new Date());
			//若商品缩略图不为空且原有缩略图不为空则删除原有缩略图再添加
			if(thumbnail!=null) {
				//先获取一遍原有信息，因为原来的信息里面有原图片地址
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if(tempProduct.getImgAddr()!=null) {
					FileUtil.deleteFile(tempProduct.getImgAddr());
				}
				addThumbnail(tempProduct, thumbnail);
			}
			//如果有新存入的商品详情图，将原先的删除，并添加新的图片
			if(productImgHolderList!=null&&productImgHolderList.size()>0) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product,productImgHolderList);
			}
			 try {
				 //更新商品信息
				 int effectedNum = productDao.updateProduct(product);
				 if(effectedNum<=0) {
					 throw new ProductOperationException("更新商品信息失败");
				 }
				 return new ProductExecution(ProductStateEnum.SUCCESS,product);
			 }catch(Exception e) {
				 throw new ProductOperationException("创建商品详情图片失败"+e.toString());
			 }
		}else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
		
	}

	private void deleteProductImgList(Long productId) {
		// 根据怕productId获取原来的图片
		List<ProductImg>productImgList = productImgDao.queryProductImgList(productId);
		//现在，去除以前的图片
		for(ProductImg productImg:productImgList) {
			FileUtil.deleteFile(productImg.getImgAddr());//获取路径 删除图片
		}
		//删除数据库里面原有的图片信息
		productImgDao.deleteProductImgByProductId(productId);
	}

	
	

}
