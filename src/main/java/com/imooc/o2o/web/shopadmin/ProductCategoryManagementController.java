package com.imooc.o2o.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.dto.Result;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductCategoryService;
@Controller // 
@RequestMapping("/shopadmin")//表明店家管理系统
public class ProductCategoryManagementController {
	@Autowired //动态注入
	private ProductCategoryService productCategoryService;
	@RequestMapping(value="/getproductcategorylist",method=RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>>getProductCategoryList(HttpServletRequest request){
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		List<ProductCategory>list = null;
		if(currentShop!=null&&currentShop.getShopId()>0) { //如果为空 对店铺没有操作权限
			list  = productCategoryService.getProductCategoryList(currentShop.getShopId());
			return new Result<List<ProductCategory>>(true,list); //查询结果集合放封装在result中
		}else {
			ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<List<ProductCategory>>(false,ps.getState(),ps.getStateInfo());			
		}
	}
	//添加商品类别
	@RequestMapping(value="/addproductcategorys",method=RequestMethod.POST)
	@ResponseBody   //从前端传回一个productCategoryList  @RequestBody批量传入  //自动接收前台传来的productCategorytList
	private Map<String,Object>addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,HttpServletRequest request){
		Map<String,Object>modelMap = new HashMap<String,Object>();
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		for (ProductCategory pc : productCategoryList) {
			pc.setShopId(currentShop.getShopId());
		}
		if(productCategoryList!=null&&productCategoryList.size()>0) {
			try {
				ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
				if(pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo()); //错误的状态信息
				}
			}catch(ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少输入一个商品类别");
		}
		
		return modelMap;	
		
	}	
	
	//删除商品类别
		@RequestMapping(value="/removeproductcategory",method=RequestMethod.POST)
		@ResponseBody   //从前端传回一个productCategoryList  @RequestBody批量传入  //自动接收前台传来的productCategorytList
		private Map<String,Object>removeProductCategory(Long productCategoryId,HttpServletRequest request){
			Map<String,Object>modelMap = new HashMap<String,Object>();
			if(productCategoryId!=null&&productCategoryId>0) {
				try {
					Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
					ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
					if(pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()) {
						modelMap.put("success", true); // 如果正确返回给前台
					}else {
						modelMap.put("success", false);
						modelMap.put("errMsg", pe.getStateInfo()); //错误的状态信息
					}
				}catch(ProductCategoryOperationException e) {
					modelMap.put("success", false);
					modelMap.put("errMsg", e.toString());
					return modelMap;
				}
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "请至少选择一个商品类别");
			}
			
			return modelMap;	
			
		}	
	
	
	
}
