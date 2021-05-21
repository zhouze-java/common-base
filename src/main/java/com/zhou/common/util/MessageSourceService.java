package com.zhou.common.util;

import com.zhou.common.model.MessageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author 周泽
 * @date Create in 15:06 2021/3/16
 * @Description
 */
@Service
@RequiredArgsConstructor
public class MessageSourceService {

    private final MessageSource messageSource;

    public MessageVO getMessageVO(String messageKey, String documentationUrl){
        String message = getMessage(messageKey);
        return new MessageVO(message, documentationUrl);
    }

    public String getMessage(String messageKey){
        Locale currentLocale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, null, currentLocale);
    }

    public ResponseEntity<MessageVO> getResponseEntity(String messageKey, String documentationUrl, HttpStatus httpStatus){
        MessageVO messageVO = getMessageVO(messageKey, documentationUrl);
        return new ResponseEntity<>(messageVO, httpStatus);
    }
}
