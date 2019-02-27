package com.imooc.o2o.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.o2o.dto.HeadLineExecution;
import com.imooc.o2o.entity.HeadLine;

public interface HeadLineService {

	/**
	 * 跟据传入的条件返回指定的
	 * @param headLineCondition
	 * @return
	 * @throws IOException
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition)
			throws IOException;
//
//	/**
//	 * 
//	 * @param headLine
//	 * @param thumbnail
//	 * @return
//	 */
//	HeadLineExecution addHeadLine(HeadLine headLine,
//			CommonsMultipartFile thumbnail);
//
//	/**
//	 * 
//	 * @param headLine
//	 * @param thumbnail
//	 * @param thumbnailChange
//	 * @return
//	 */
//	HeadLineExecution modifyHeadLine(HeadLine headLine,
//			CommonsMultipartFile thumbnail);
//
//	/**
//	 * 
//	 * @param headLineId
//	 * @return
//	 */
//	HeadLineExecution removeHeadLine(long headLineId);
//
//	/**
//	 * 
//	 * @param headLineIdList
//	 * @return
//	 */
//	HeadLineExecution removeHeadLineList(List<Long> headLineIdList);

}
