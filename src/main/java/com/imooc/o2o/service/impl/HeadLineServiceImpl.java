package com.imooc.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.HeadLineDao;
import com.imooc.o2o.dto.HeadLineExecution;
import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.enums.HeadLineStateEnum;
import com.imooc.o2o.service.HeadLineService;
import com.imooc.o2o.util.FileUtil;
import com.imooc.o2o.util.ImageUtil;

@Service
public class HeadLineServiceImpl implements HeadLineService {

	@Autowired
	private HeadLineDao headLineDao;


	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition)
			throws IOException {
	
//		List<HeadLine> headLineList = null;
//		ObjectMapper mapper = new ObjectMapper();
//		String key = HLLISTKEY;
//		if (headLineCondition.getEnableStatus() != null) {
//			key = key + "_" + headLineCondition.getEnableStatus();
//		}
//		if (!jedisKeys.exists(key)) {
//			headLineList = headLineDao.queryHeadLine(headLineCondition);
//			String jsonString = mapper.writeValueAsString(headLineList);
//			jedisStrings.set(key, jsonString);
//		} else {
//			String jsonString = jedisStrings.get(key);
//			JavaType javaType = mapper.getTypeFactory()
//					.constructParametricType(ArrayList.class, HeadLine.class);
//			headLineList = mapper.readValue(jsonString, javaType);
//		}
		return headLineDao.queryHeadLine(headLineCondition);
	}



}
