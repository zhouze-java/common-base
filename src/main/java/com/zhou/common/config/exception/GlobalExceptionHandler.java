package com.zhou.common.config.exception;


import com.zhou.common.exception.*;
import com.zhou.common.model.Result;
import com.zhou.common.model.enums.ResultCodeEnum;
import com.zhou.common.util.MessageSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @author 周
 * @date Create in 11:14 2019/12/26
 * @Description 其实我小时候的梦想并不是要当什么程序员，
 * 我只是幻想自己是地主家的少爷，家有良田万顷，
 * 终日不学无术，没事领着-群狗奴才上街去调戏一下良家少女。
 * 然后这个类的作用就是全局异常处理.
 */
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	
	private final MessageSourceService messageSourceService;

	/**
	 * 请求参数转换出错
	 * @param ex
	 */
	@ExceptionHandler({HttpMessageNotReadableException.class})
	@ResponseBody
	public Result<?> requestError(HttpMessageNotReadableException ex) {
		log.error(ex.getMessage());
		return new Result<>(ResultCodeEnum.BAD_REQUEST.getCode(), ex.getMessage());
	}
	
	/**
	 * 403 禁止访问
	 * @param ex
	 */
	@ExceptionHandler({ForbiddenException.class})
	@ResponseBody
	public Result<?> forbiddenError(ForbiddenException ex) {
		if(StringUtils.isEmpty(ex.getMessage())){
			return new Result<>(ResultCodeEnum.FORBIDDEN.getCode());
		}
		return new Result<>(ResultCodeEnum.FORBIDDEN.getCode(), ex.getMessage());
	}
	
	/**
	 * 404 找不到异常
	 * @param ex
	 */
	@ExceptionHandler({NotFoundException.class})
	@ResponseBody
	public Result<?> notFoundError(NotFoundException ex) {
		if(StringUtils.isEmpty(ex.getMessage())){
			return new Result<>(ResultCodeEnum.NOT_FOUND.getCode());
		}
		return new Result<>(ResultCodeEnum.NOT_FOUND.getCode(), ex.getMessage());
	}

	
	/**
	 * 428 要求缺少先决条件
	 * @param ex
	 */
	@ExceptionHandler({PreconditionRequiredException.class})
	@ResponseBody
	public Result<?> preconditionRequiredError(PreconditionRequiredException ex) {
		if(StringUtils.isEmpty(ex.getMessage())){
			return new Result<>(ResultCodeEnum.PRECONDITION_REQUIRED.getCode());
		}
		return new Result<>(ResultCodeEnum.PRECONDITION_REQUIRED.getCode(),"");
	}

	/**
	 * 400 Bad Requests (参数有误)
	 * @param ex
	 */
	@ExceptionHandler({BadRequestException.class})
	@ResponseBody
	public Result<?> badRequestError(RuntimeException ex) {
		if(StringUtils.isEmpty(ex.getMessage())){
			return new Result<>(ResultCodeEnum.BAD_REQUEST.getCode());
		}
		return new Result<>(ResultCodeEnum.BAD_REQUEST.getCode(), ex.getMessage());
	}

	/**
	 * 422 Unprocessable Entity Exception (请求验证不通过)
	 * @param ex
	 */
	@ExceptionHandler({UnprocessableEntityException.class})
	@ResponseBody
	public Result<?> unprocessableEntityError(UnprocessableEntityException ex) {
		if(StringUtils.isEmpty(ex.getMessage())){
			return new Result<>(ResultCodeEnum.UNPROCESSABLE_ENTITY.getCode());
		}
		return new Result<>(ResultCodeEnum.UNPROCESSABLE_ENTITY.getCode(), ex.getMessage());
	}

	/**
	 * 数据库唯一索引异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({DuplicateKeyException.class})
	@ResponseBody
	public Result<?> duplicateKeyError(RuntimeException ex) {
		if(StringUtils.isEmpty(ex.getMessage())){
			return new Result<>(ResultCodeEnum.ALREADY_EXISTS_EXCEPTION.getCode());
		}
		return new Result<>(ResultCodeEnum.ALREADY_EXISTS_EXCEPTION.getCode(), ex.getMessage());
	}
	
	/**
	 * 已经存在异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({AlreadyExistsException.class})
	@ResponseBody
	public Result<?> alreadyExistsException(AlreadyExistsException ex) {
		if(StringUtils.isEmpty(ex.getMessage())){
			return new Result<>(ResultCodeEnum.ALREADY_EXISTS_EXCEPTION.getCode());
		}
		return new Result<>(ResultCodeEnum.ALREADY_EXISTS_EXCEPTION.getCode(), ex.getMessage());
	}


	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	@ResponseBody
	public Result<?> NotSupported(HttpRequestMethodNotSupportedException e) {
		return new Result<>(ResultCodeEnum.REQUEST_METHOD_NOT_SUPPORTED.getCode());
	}
	
	/**
	 * 全局异常
	 * @param re
	 * @return
	 */
	@ExceptionHandler({Exception.class})
	@ResponseBody
	public Result<?> serverError(Exception re) {
		log.error(re.getMessage());
		log.error("{}", re);
		return new Result<>(ResultCodeEnum.SERVER_ERROR.getCode());

	}
	
	/**
	 * 参数验证没通过
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public Result<?> processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		ObjectError error = result.getFieldError();
		if(error == null){
			List<ObjectError> errors = result.getAllErrors();
			error = errors.get(0);
		}
		return processFieldError(error);
	}



	private Result<?> processFieldError(ObjectError error) {
		Result<?> result = null;
		if (error != null) {
			result = new Result<>(ResultCodeEnum.UNPROCESSABLE_ENTITY.getCode(), error.getDefaultMessage());
		}
		return result;
	}
}
