package com.imooc.o2o.dto;
/**
 * 封装json对象，所有返回结果都使用他
 * @author Administrator
 *
 */
public class Result<T> {	
	private boolean success;//是否成功标志
	
	private T data;//成功时候返回的到数据 (某一个店铺下面的商品类别)
	
	private String errMsg;//错误信息
	
	private int errorCode;
	
	public Result() {
		
	}
	//成功时的构造器
	public Result(boolean success,T data) {
		this.success = success;
		this.data = data;
	}
	//错误时的构造器(失败就没有列表的数据)
	public Result(boolean success,int errorCode,String errorMSg) {
		this.success = success;
		this.errorCode = errorCode;
		this.errMsg = errorMSg;
		
	}
	
	public boolean isSuccess() {
		return success;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
	
}
