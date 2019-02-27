package com.imooc.o2o.web.shopadmin;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jaiimageio.impl.common.ImageUtil;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.FileUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagermentController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	
	//店铺管理
	@RequestMapping(value="/getshopmanagementinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object>getShopManagementInfo(HttpServletRequest request){
		Map<String,Object>modelMap = new HashMap<String,Object>();
		long shopId = HttpServletRequestUtil.getLong(request,"shopId"); //从前端拿到店铺id
		if(shopId<=0) { //如果前端没有店铺id
			Object currentShopObj = request.getSession().getAttribute("currentShop");//获取当前店铺
			if(currentShopObj==null) {
				modelMap.put("redirect",false );
				modelMap.put("url","/o2o/shopadmin/shoplist");
			}else {
				Shop currentShop = (Shop)currentShopObj;
				modelMap.put("redirect", false); //有权限以后就不用重定向了
				modelMap.put("shopId", currentShop.getShopId()); //得到店铺id
			}
		}else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
	}
	// 店铺的列表展示
	@RequestMapping(value="/getshoplist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object>getShopList(HttpServletRequest request){
		Map<String,Object>modelMap = new HashMap<String,Object>();
		PersonInfo user = new PersonInfo();
		user.setUserId(1L);
		user.setName("彭亮");
		request.getSession().setAttribute("user", user);
		user = (PersonInfo)request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("user", user);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		
		return modelMap;
	}
	
	
	@RequestMapping(value="/getshopbyid",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object>getShopById(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");//获取前端传进来的shopId
		if(shopId>-1) {
			try {
			Shop shop = shopService.getByShopId(shopId);
			List<Area>areaList = areaService.getAreaList();
			modelMap.put("shop", shop);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
			}catch(Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
		
		
	}
	
	
	@RequestMapping(value="/getshopinitinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object>getShopInitInfo(){
		Map<String,Object>modelMap = new HashMap<String,Object>();
		List<ShopCategory>shopCategoryList = new ArrayList<ShopCategory>();
		List<Area>areaList = new ArrayList<Area>();
		
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory()); //获取到他的全部的对象
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			//这里面是异常的信息
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			
			e.printStackTrace();
		}
		return modelMap;
		
		
	}
	@RequestMapping(value="/registershop",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object>registerShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}
		
		
		//1.接收并转化为相应的参数，包括店铺信息以及图片信息，下面这是json的转换操作
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr,Shop.class);
			
		}catch(Exception e){
			modelMap.put("success",false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		//2注册店铺
		if(shop!=null&&shopImg!=null) {
			PersonInfo owner =(PersonInfo)request.getSession().getAttribute("user");
			owner.setUserId(1L);
			shop.setOwner(owner);
			ShopExecution se ;
//			File shopImgFile = new File(FileUtil.getImgBasePath()+FileUtil.getRandomFileName()); //获取随机的新的文件
			try {
				ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
				se = shopService.addShop(shop,imageHolder);
				if(se.getState() ==ShopStateEnum.CHECK.getState()) { //判断状态是否和设置的状态相等
					modelMap.put("success",true);
					//一旦创建店铺成功，需要看到一个用户可以操作几个店铺（从session中获取）
					@SuppressWarnings({ "unchecked", "unused" })
					//一旦创建成功就把店铺信息保存到session会话中
					List<Shop>shopList = (List<Shop>)request.getSession().getAttribute("shopList");
					//该用户可以操作的用户列表
					if(shopList ==null||shopList.size()==0) {  //第一个创建的店铺
						shopList = new ArrayList<Shop>();
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList",shopList);
				
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				
				System.out.println(e);
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请您输入您的店铺信息");
			return modelMap;
		}
		
		
		
	}
		//3.返回结果
		
//		private static void inputStreamToFile(InputStream ins,File file){
//			FileOutputStream os = null;
//			try{
//				os = new FileOutputStream(file);
//				int bytesRead = 0;
//				byte[] buffer = new byte[1024];
//				while((bytesRead=ins.read(buffer))!=-1) {
//					os.write(buffer, 0, bytesRead);
//				}
//			}catch(Exception e) {
//				throw new RuntimeException("调用inputStreamToFile产生异常： "+e.getMessage());
//			}finally {
//				try {
//					if(os!=null) {
//						os.close();
//					}
//					if(ins!=null) {
//						ins.close();
//					}
//				}catch(IOException e) {
//					throw new RuntimeException("调用inputStreamToFile关闭io产生异常： "+e.getMessage());
//				}
//			}
//			
//		}	
	
	@RequestMapping(value="/modifyshop",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object>modifyShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}
		
		
		//1.接收并转化为相应的参数，包括店铺信息以及图片信息，下面这是json的转换操作
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr,Shop.class);
			
		}catch(Exception e){
			modelMap.put("success",false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		} //图片是可上传可不上传的 这个与店铺注册有所不同
		
		//2修改店铺信息
		if(shop!=null&&shop.getShopId()!=null) {
			//修改店铺信息是不需要修改用户信息
			ShopExecution se ;
//			File shopImgFile = new File(FileUtil.getImgBasePath()+FileUtil.getRandomFileName()); //获取随机的新的文件
			try {
				if(shopImg==null) {
					se = shopService.modifyShop(shop,null);

				}else {
					ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
					se = shopService.modifyShop(shop,imageHolder);
				}
				
				if(se.getState() ==ShopStateEnum.SUCCESS.getState()) { //判断状态是否和设置的状态相等
					modelMap.put("success",true); //如果成功就正确
				}else {
					modelMap.put("success", false); 
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (ShopOperationException e) {
				
				System.out.println(e);
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请您输入您的店铺id");
			return modelMap;
		}	
		
	}
	
	

}
