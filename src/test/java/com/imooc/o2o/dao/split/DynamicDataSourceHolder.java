package com.imooc.o2o.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataSourceHolder {
	private static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
	private static ThreadLocal<String>contextHolder = new ThreadLocal<String>();
	
}
