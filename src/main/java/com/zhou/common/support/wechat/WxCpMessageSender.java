package com.zhou.common.support.wechat;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * @author 周泽
 * @date Create in 10:50 2021/6/21
 * @Description 微信发送消息
 */
public class WxCpMessageSender {

    /**
     * 发送消息
     *
     * @param wxCpService
     * @param content 消息内容
     * @param toUserIds 发送人列表
     * @throws WxErrorException
     */
    public static void sendTextMessage(WxCpService wxCpService, Set<String> toUserIds,String content) throws WxErrorException {
        if (CollectionUtils.isEmpty(toUserIds)) {
            return;
        }

        WxCpMessage message = new WxCpMessage();
        message.setMsgType(WxConsts.KefuMsgType.TEXT);
        message.setToUser(StringUtils.join(toUserIds, "|"));
        message.setContent(content);
        wxCpService.getMessageService().send(message);
    }
}
