/**
 * 
 */
$(function(){
	var shopId = getQueryString('shopId');
	var isEdit = shopId ? true : false;
	var initUrl='/o2o/shopadmin/getshopinitinfo';  //获取店铺区域信息
	var registerShopUrl='/o2o/shopadmin/registershop';
	var shopInfoUrl = "/o2o/shopadmin/getshopbyid?shopId="+ shopId; //根据shopId获取店铺信息
	var editShopUrl='/o2o/shop/modifyshop';
	if(!isEdit){
		getShopInitInfo();
	}else{
		getShopInfo(shopId);
	}
	function getShopInfo(shopId){
		$.getJSON(shopInfoUrl,function(data){
			if(data.success){
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				var shopCategory='<option data-id="'+shop.shopCategory.shopCategoryId+'"selected>'
				+shop.shopCategory.shopCategoryName+'</option>';
				var tempAreaHtml = '';
				data.areaList.map(function(item,index){
					tempAreaHtml+='<option data-id = "'+item.areaId+'">'+
					item.areaName+'</option>';
				});
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled','disabled');
				$('#area').html(tempAreaHtml); //绑定到html下拉列表中
				$("#area option[data-id='"+shop.area.areaId+"']").attr("selected","selected"); // 默认选择当前区里的信息
			}
		});
	}
	alert(initUrl);
	getShopInitInfo();
	function getShopInitInfo(){
		$.getJSON(initUrl,function(data){
			if(data.success){
				var tempHtml='';
				var tempAreaHtml='';
				data.shopCategoryList.map(function(item,index){
					tempHtml+='<option data-id="'
						+ item.shopCategoryId + '">'
					+ item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item,index){
					tempAreaHtml+='<option data-id="'+item.areaId+'">'
					+item.areaName + '</option>';
				}); 
				//获取区域信息
				//把这些获取到的信息 塞进前台我需要的信息当中 可以供选择
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		});
	//第二个方法：获取表单的数据提交
		$('#submit').click(function(){
			var shop ={};
			shop.shopName = $('#shop-name').val();//拿到前端的店铺名字
			shop.shopAddr = $('#shop-addr').val();
			shop.phone = $('#shop-phone').val();
			shop.shopDesc = $('#shop-desc').val();
			shop.shopCategory = {
					shopCategoryId:$('#shop-category').find('option').not(function(){
						return !this.selected;
					}).data('id')
			};
			shop.area = {
					areaId:$('#area').find('option').not(function(){
						return !this.selected;
					}).data('id')
			};
			var shopImg = $('#shop-img')[0].files[0];
			var formData = new FormData();
			formData.append('shopImg',shopImg);
			formData.append('shopStr',JSON.stringify(shop));
			var verifyCodeActual = $('#j_captcha').val();
			if(!verifyCodeActual){ //判断是否为空
				$.toast('请输入验证码！');
				return;
			}
			formData.append('verifyCodeActual', verifyCodeActual);
			
			//通过ajax提交数据
			$.ajax({
				url:(isEdit?editShopUrl:registerShopUrl),
				type:'POST',
				data:formData,
				contentType:false,
				processData:false,
				cache:false,
				success:function(data){
					if(data.success){
						$.toast('提交成功！');
					}else{
						$toast('提交失败! ' +data.errMsg);
					}
					$('#captcha_img').click();
				}
				
			});
		});
		
	}



})


