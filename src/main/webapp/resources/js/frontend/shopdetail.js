$(function() {
	var loading = false;
	//分页允许返回的最大的条数，超过此数则禁止访问后台
	var maxItems = 20;
	//默认一页返回的商品总数
	var pageSize = 10;
	//列出与商品列表的url
	var listUrl = '/o2o/frontend/listproductsbyshop';
	//从地址栏里获取shopId
	var pageNum = 1;
	var shopId = getQueryString('shopId');
	var productCategoryId = '';
	var productName = '';
	//获取本店铺信息以及商品类别信息列表的url
	var searchDivUrl = '/o2o/frontend/listshopdetailpageinfo?shopId='
			+ shopId;
	//渲染出店铺基本信息以及商品类别列表以供搜索
//	getSearchDivDate();
//	//预先加载10条商品信息
//	addItems(pageSize,pageNum);
	//给兑换礼品的a标签赋值兑换礼品的url
//	$('#exchangelist').attr('href','/o2o/frontend/awardlist?shopId=' + shopId);
	
	//获取本店铺信息以及商品类别信息列表
	function getSearchDivData() {
		var url = searchDivUrl;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								var shop = data.shop;
								$('#shop-cover-pic').attr('src', shop.shopImg);
								$('#shop-update-time').html(
										new Date(shop.lastEditTime)
												.Format("yyyy-MM-dd"));
								$('#shop-name').html(shop.shopName);
								$('#shop-desc').html(shop.shopDesc);
								$('#shop-addr').html(shop.shopAddr);
								$('#shop-phone').html(shop.phone);
								//获取后台返回的该店铺的商品列表
								var productCategoryList = data.productCategoryList;
								var html = '';
								productCategoryList
										.map(function(item, index) {
											html += '<a href="#" class="button" data-product-search-id='
													+ item.productCategoryId
													+ '>'
													+ item.productCategoryName
													+ '</a>';
										});
								$('#shopdetail-button-div').html(html);
							}
						});
	}
	getSearchDivData();

	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&productCategoryId=' + productCategoryId
				+ '&productName=' + productName + '&shopId=' + shopId;
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count;
				var html = '';
				data.productList.map(function(item, index) {
					html += '' + '<div class="card" data-product-id='
							+ item.productId + '>'
							+ '<div class="card-header">' + item.productName
							+ '</div>' + '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ item.imgAddr + '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.productDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
							+ '更新</p>' + '<span>点击查看</span>' + '</div>'
							+ '</div>';
				});
				$('.list-div').append(html);
				var total = $('.list-div .card').length;
				if (total >= maxItems) {
					// 影藏提示符
					$('.infinite-scroll-preloader').hide();
				}else{
					//总数没有达到上限时候就显示出来
					$('.infinite-scroll-preloader').show();
				}
				//页码加一
				pageNum += 1;
				//加载结束，可以再次加载了
				loading = false;
				//刷新页面，显示新加载的额店铺
				$.refreshScroller();
			}
		});
	}

	addItems(pageSize, pageNum);

	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});

	$('#shopdetail-button-div').on(
			'click',
			'.button',
			function(e) {
				productCategoryId = e.target.dataset.productSearchId;
				if (productCategoryId) {
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						productCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
				}
			});

	$('.list-div')
			.on(
					'click',
					'.card',
					function(e) {
						var productId = e.currentTarget.dataset.productId;
						window.location.href = '/o2o/frontend/productdetail?productId='
								+ productId;
					});
	//需要查询的商品名字发生变化后，重置页码，清空原先的商品列表，安装新的名字去加载
	$('#search').on('change', function(e) {
		productName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});
