package com.zhou.common.exception;

/**
 * 404 找不到
 * @author shouping
 *
 */
public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = -5447692349297847968L;

	public NotFoundException() {
		super();
	}
	
	public NotFoundException(String message) {
		super(message);
	}
}
