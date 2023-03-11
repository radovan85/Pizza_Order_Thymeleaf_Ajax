package com.radovan.spring.exceptions;

import javax.management.RuntimeErrorException;

public class CartItemsNumberException extends RuntimeErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CartItemsNumberException(Error e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

}
