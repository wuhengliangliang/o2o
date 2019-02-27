package com.imooc.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;

//加入Service注解告诉spring ioc 是需要托管的
@Service
public class AreaServiceImpl implements AreaService {
	//将AreaDao的实现注入到里面
	@Autowired
	private AreaDao areaDao;
	@Override
	public List<Area> getAreaList() {
		return areaDao.queryArea(); //从数据库中取出我们的区域列表
	}

}
