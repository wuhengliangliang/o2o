package com.imooc.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.o2o.dao.ShopCategoryDao;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.ShopCategoryService;

@Service //sping注入 当成对象管理起来
public class ShopCategoryServiceImpl implements ShopCategoryService {
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		
		return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}
	
	
	@Override
	public ShopCategory getShopCategoryById(Long shopCategoryId) throws IOException {
		List<ShopCategory>shopCategoryList = new ArrayList<ShopCategory>();
		shopCategoryList = getFirstLevelShopCategoryList();
		shopCategoryList.addAll(getAllSecondLevelShopCategory());
		for (ShopCategory sc : shopCategoryList) {
			if (shopCategoryId == sc.getShopCategoryId()) {
				return sc;
			}
		}
		ShopCategory sc = shopCategoryDao.queryShopCategoryById(shopCategoryId);
		if (sc != null) {
			return sc;
		} else {
			return null;
		}

	}


	private Collection<? extends ShopCategory> getAllSecondLevelShopCategory() {
		// TODO Auto-generated method stub
		return null;
	}


	private List<ShopCategory> getFirstLevelShopCategoryList() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
