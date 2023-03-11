package com.radovan.spring.exceptions;

import javax.management.RuntimeErrorException;

public class ExistingSizeException extends RuntimeErrorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExistingSizeException(Error e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

}
