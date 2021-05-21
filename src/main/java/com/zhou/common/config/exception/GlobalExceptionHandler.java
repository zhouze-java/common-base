package com.zhou.common.config.exception;


import com.zhou.common.exception.*;
import com.zhou.common.model.MessageVO;
import com.zhou.common.util.MessageSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MessageVO requestError(HttpMessageNotReadableException ex) {
		log.error(ex.getMessage());
		return messageSourceService.getMessageVO("error.client.400", ex.getMessage());
	}
	
	/**
	 * 403 禁止访问
	 * @param ex
	 */
	@ExceptionHandler({ForbiddenException.class})
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public MessageVO forbiddenError(ForbiddenException ex) {
		if(StringUtils.isEmpty(ex.getMessage())){
			return messageSourceService.getMessageVO("error.client.403", "");
		}
		return messageSourceService.getMessageVO(ex.getMessage(), "");
	}
	
	/**
	 * 404 找不到异常
	 * @param ex
	 */
	@ExceptionHandler({NotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public MessageVO notFoundError(NotFoundException ex) {
		if(StringUtils.isEmpty(ex.getMessage())){
			return messageSourceService.getMessageVO("error.client.404", "");
		}
		return messageSourceService.getMessageVO(ex.getMessage(), "");
	}

	
	/**
	 * 428 要求缺少先决条件
	 * @param ex
	 */
	@ExceptionHandler({PreconditionRequiredException.class})
	@ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
	@ResponseBody
	public MessageVO preconditionRequiredError(PreconditionRequiredException ex) {
		if(StringUtils.isEmpty(ex.getMessage())){
			return messageSourceService.getMessageVO("error.client.428", "");
		}
		return messageSourceService.getMessageVO(ex.getMessage(), "");
	}

	/**
	 * 400 Bad Requests (参数有误)
	 * @param ex
	 */
	@ExceptionHandler({BadRequestException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MessageVO badRequestError(RuntimeException ex) {
		if(StringUtils.isEmpty(ex.getMessage())){
			return messageSourceService.getMessageVO("error.client.400", "");
		}
		return messageSourceService.getMessageVO(ex.getMessage(), "");
	}

	/**
	 * 422 Unprocessable Entity Exception (请求验证不通过)
	 * @param ex
	 */
	@ExceptionHandler({UnprocessableEntityException.class})
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public MessageVO unprocessableEntityError(UnprocessableEntityException ex) {
		if(StringUtils.isEmpty(ex.getMessage())){
			return messageSourceService.getMessageVO("error.client.422", "");
		}
		return messageSourceService.getMessageVO(ex.getMessage(), "");
	}

	/**
	 * 数据库唯一索引异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({DuplicateKeyException.class})
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public MessageVO duplicateKeyError(RuntimeException ex) {
		if(StringUtils.isEmpty(ex.getMessage())){
			return messageSourceService.getMessageVO("error.client.409", "");
		}
		return messageSourceService.getMessageVO(ex.getMessage(), "");
	}
	
	/**
	 * 已经存在异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({AlreadyExistsException.class})
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public MessageVO alreadyExistsException(AlreadyExistsException ex) {
		if(StringUtils.isEmpty(ex.getMessage())){
			return messageSourceService.getMessageVO("error.client.409", "");
		}
		return messageSourceService.getMessageVO(ex.getMessage(), "");
	}


	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public MessageVO NotSupported(HttpRequestMethodNotSupportedException e) {
		log.error(e.getMessage());
		return messageSourceService.getMessageVO("error.globalExceptionHandler.notSupported", "");
	}
	
	/**
	 * 全局异常
	 * @param re
	 * @return
	 */
	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public MessageVO serverError(Exception re) {
		log.error(re.getMessage());
		log.error("{}", re);
		return messageSourceService.getMessageVO("error.client.500", "");
	}
	
	/**
	 * 参数验证没通过
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public MessageVO processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		ObjectError error = result.getFieldError();
		if(error == null){
			List<ObjectError> errors = result.getAllErrors();
			error = errors.get(0);
		}
		return processFieldError(error);
	}



	private MessageVO processFieldError(ObjectError error) {
		MessageVO message = null;
		if (error != null) {
			message = messageSourceService.getMessageVO(error.getDefaultMessage(), "");
		}
		return message;
	}
}
