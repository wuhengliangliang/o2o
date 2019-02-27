package com.imooc.o2o.web.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.web.superadmin.AreaController;

/**
 * 主要用来解析路由并转发到相应的html中
 * @author Administrator
 *
 */

@Controller
@RequestMapping("shopadmin")
public class ShopAdminController {
	
	@RequestMapping(value = "shopoperation", method = RequestMethod.GET)
	
	private String shopOperation() {
		
		return "shop/shopoperation";
	}
	
	@RequestMapping(value = "shoplist", method = RequestMethod.GET)
	
	private String shopList() {
		
		return "shop/shoplist";
	}

	@RequestMapping(value = "shopmanagement", method = RequestMethod.GET)

	private String shopManagement() {
		
		return "shop/shopmanagement";
	}

	@RequestMapping(value = "productcategorymanagement", method = RequestMethod.GET)

	private String productCategoryManage() {
		//转发至商品类别管理页面
		return "shop/productcategorymanagement";
	}
	
	@RequestMapping(value = "/productoperation")

	private String productOperation() {
		//转发至商品添加/编辑页面
		return "shop/productoperation";
	}
	
	@RequestMapping(value = "/productmanage")

	private String productManage() {
		//转发至商品添加/编辑页面
		return "shop/productmanage";
	}
}
