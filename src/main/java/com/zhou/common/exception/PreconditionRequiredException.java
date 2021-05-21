package com.zhou.common.exception;

/**
 * 428 要求缺少先决条件
 * @author shouping
 *
 */
public class PreconditionRequiredException extends RuntimeException {

	private static final long serialVersionUID = -5447692349297847968L;

	public PreconditionRequiredException() {
		super();
	}
	
	public PreconditionRequiredException(String message) {
		super(message);
	}
}
