package com.imooc.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.mchange.v2.codegen.CodegenUtils;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	//支持上传商品详情图的最大数量
	private static final int IMAGEMAXCOUNT = 6;
	
	@RequestMapping(value="addproduct",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object>addProduct(HttpServletRequest request){
		Map<String,Object>modelMap = new HashMap<String,Object>();
		//验证码校验
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success",false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//接收前端的参数的变量的初始化，包括商品 ，缩略图 详情列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");  //从前端的request里面获取商品信息（json转换为string数据传入过来）
	
		ImageHolder thumbnail = null;
		List<ImageHolder>productImgList = new ArrayList<ImageHolder>(); //保存商品详情图的文件流 
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext()); //通过request的session获取到文件流
		//判断请求中是否存在文件流
		try {
			//若请求中存在文件流，则取出相关的 文件（包括缩略图和详情图）
			if(multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request, productImgList);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			System.out.println(e.getMessage());
			return modelMap;
		}
		try {
			//尝试获取前端传过来的表单string流并将其转换成product类
			product = mapper.readValue(productStr, Product.class); //通过mapper将json转为飘柔duct的实例
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg",e.toString());
			return modelMap;
		}
		//若Product信息，缩略图以及详情图列表为非空，则开始进行添加商品的操作
		if(product!=null&&thumbnail!=null&&productImgList.size()>0) {
			try {
				//从session中火球当前店铺的id并赋值给product,减少对前端数据的依赖
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				//执行添加操作
				ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
				if(pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","请输入商品信息");
		}
		
		return modelMap;
		
	}
	private ImageHolder handleImage(HttpServletRequest request, List<ImageHolder> productImgList) throws IOException {
		ImageHolder thumbnail;
		//取出缩略图并构建ImageHolder对象
		//取出缩略图并构建ImageHolder对象
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;; //用来处理完文件 流
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile)multipartRequest.getFile("thumbnail");
		thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
		//取出详情图列表并构建List<ImageHolder> 列表对象，最多是上传6张图片
		for(int i = 0;i<IMAGEMAXCOUNT;i++) {
			CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg"+i);
			if(productImgFile!=null) {
				//若取出的第i个图片文件流不为空，则将其加入到详情图 列表
				ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),productImgFile.getInputStream());
				productImgList.add(productImg);
			}else {
				//若取出的第i个详情图片文件 流为空，则终止循环
				break;
			}
		}
		return thumbnail;
	}
	/**
	 * 通过商品id拿到商品信息
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/getproductbyid",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object>getProductById(@RequestParam Long productId){
		Map<String,Object>modelMap = new HashMap<String,Object>();
		//非空判断
		if(productId>-1) {
			//获取商品信息
			Product product = productService.getProductById(productId);
			//获取该店铺下的商品类别列表
			List<ProductCategory>productCategoryList = productCategoryService.getProductCategoryList(product.getShop().getShopId());
			modelMap.put("product", product);
			modelMap.put("productCategoryList",productCategoryList );
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
			
		}
		
		return modelMap;
	}
	
	
	@RequestMapping(value="/modifyproduct")
	@ResponseBody
	private Map<String,Object>modifyProduct(HttpServletRequest request){
		Map<String,Object>modelMap = new HashMap<String,Object>();
		//是商品编辑时候调用还是上下架操作的 时候调用
		//若为前者则进行验证码判断，后者则跳过验证码判断
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		//验证码判断
		if(!statusChange&&!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		
		//接收前端的参数的变量的初始化，包括商品 ，缩略图 详情列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		//String productStr = HttpServletRequestUtil.getString(request, "productStr");  //从前端的request里面获取商品信息（json转换为string数据传入过来）
		ImageHolder thumbnail = null;
		List<ImageHolder>productImgList = new ArrayList<ImageHolder>(); //保存商品详情图的文件流 
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext()); //通过request的session获取到文件流
		//判断请求中是否存在文件流
		try {
			
			//若请求中存在文件流，则取出相关的 文件（包括缩略图和详情图）
			if(multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request, productImgList);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			System.out.println(e.getMessage());
			return modelMap;
		}
		try {
			String productStr = HttpServletRequestUtil.getString(request, "productStr");
			//尝试获取前端传过来的表单string流并将其转换成product类
			product = mapper.readValue(productStr, Product.class); //通过mapper将json转为飘柔duct的实例
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg",e.toString());
			return modelMap;
		}
		//若Product信息，缩略图以及详情图列表为非空，则开始进行添加商品的操作
		if(product!=null&&thumbnail!=null&&productImgList.size()>0) {
			try {
				//从session中火球当前店铺的id并赋值给product,减少对前端数据的依赖
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				//执行添加操作
				ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
				if(pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","请输入商品信息");
		}
		
		return modelMap;
	}
	
	@RequestMapping(value="getproductlistbyshop",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object>getProductListByShop(HttpServletRequest request){
		Map<String,Object>modelMap = new HashMap<String,Object>();
		//获取前台传来的页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		//从前台传过来的每页要求返回的商品数量的上线
		int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
		//从当前session中获取店铺信息，主要获取shopId 
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		//空值判断
		if((pageIndex>-1)&&(pageSize>-1)&&(currentShop!=null)&&currentShop.getShopId()!=null) {
			//获取传入的需要检索的条件，包括是否需要从某个商品类别下以及模糊查询某个商品名去筛选某个店铺下的店铺列表
			//筛选的条件进行排列组合
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			String productName = HttpServletRequestUtil.getString(request, "productName");
			Product productCondition =  compactProductCondition(currentShop.getShopId(),productCategoryId,productName);
			//传入查询条件以及 分页信息查询，返回响应的商店列表以及总数
			ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("couunt", pe.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or  pageIndex or shopId");
		}
		return modelMap;
	}
	private Product compactProductCondition(long shopId, long productCategoryId,String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		//若有指定类别的要求则添加进去
		if(productCategoryId!=-1L) {
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		//若有商品模糊查询的要求则添加
		if(productName!=null) {
			productCondition.setProductName(productName);
		}
		return productCondition;
	}
	
	
	
	
}
