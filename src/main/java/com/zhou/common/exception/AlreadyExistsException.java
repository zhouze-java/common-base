package com.zhou.common.exception;

/**
 * 409 已经存在异常
 * @author shouping
 *
 */
public class AlreadyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = -5447692349297847968L;

	public AlreadyExistsException() {
		super();
	}
	
	public AlreadyExistsException(String message) {
		super(message);
	}
}
