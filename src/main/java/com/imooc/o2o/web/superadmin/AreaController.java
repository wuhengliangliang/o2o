package com.imooc.o2o.web.superadmin;

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

@Controller
@RequestMapping("/superadmin")
public class AreaController {
	Logger logger = LoggerFactory.getLogger(AreaController.class);
	
	
	@Autowired //告诉spring中 我想用这个对象 将这个对象注入尽来
	private AreaService areaService;
	
	@RequestMapping(value = "/listareas", method = RequestMethod.GET)
	@ResponseBody//自动转换为json对象发送给前端
	private Map<String, Object> listAreas() {
		//在方法开头提供日志信息
		logger.info("====statr===");
		long startTime = System.currentTimeMillis();
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Area> list = new ArrayList<Area>();
		try {
			list = areaService.getAreaList();
			modelMap.put("rows", list);
			modelMap.put("total", list.size());

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		logger.error("test error");
		long endTime = System.currentTimeMillis();
		logger.debug("costTime:[{}]",endTime-startTime);
		logger.info("===end===");
		return modelMap;
	}
	@RequestMapping(value = "/listareas1", method = RequestMethod.GET)
	@ResponseBody//自动转换为json对象发送给前端
	private Map<String, Object> listAreas1() {
		//在方法开头提供日志信息
		logger.info("====statr===");
		long startTime = System.currentTimeMillis();
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Area> list = new ArrayList<Area>();
		try {
			list = areaService.getAreaList();
			modelMap.put("rows", list);
			modelMap.put("total", list.size());

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		logger.error("test error");
		long endTime = System.currentTimeMillis();
		logger.debug("costTime:[{}]",endTime-startTime);
		logger.info("===end===");
		return modelMap;
	}
}