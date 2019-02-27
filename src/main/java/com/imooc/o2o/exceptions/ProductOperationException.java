package com.imooc.o2o.exceptions;

public class ProductOperationException extends RuntimeException {

	/**
	 *  商品信息的异常处理
	 */
	private static final long serialVersionUID = 5076172298827469013L;

	public ProductOperationException(String msg) {
		super(msg);
	}
}
