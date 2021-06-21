package com.zhou.common.support.wechat;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpRedissonConfigImpl;
import org.redisson.api.RedissonClient;

/**
 * @author 周泽
 * @date Create in 14:34 2021/6/9
 * @Description 创建WxCpService 用来调用企业微信的接口
 */
public class WxCpServiceBuilder {

    public static final String WX_CP_SERVICE_KEY_PREFIX = "chat:record:";

    private static final int DEFAULT_AGENT_ID = 1000002;

    /**
     * 获取wxCpService
     * @param redissonClient
     * @param corpId
     * @param cropSecret
     * @return
     */
    public static WxCpService build(RedissonClient redissonClient, String corpId, String cropSecret) {
        WxCpRedissonConfigImpl wxCpRedissonConfig = buildConfigStorage(redissonClient, corpId, cropSecret);
        wxCpRedissonConfig.setAgentId(DEFAULT_AGENT_ID);
        WxCpServiceImpl wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(wxCpRedissonConfig);
        return wxCpService;
    }

    /**
     * 获取wxCpService
     * @param redissonClient
     * @param corpId
     * @param cropSecret
     * @param agentId
     * @return
     */
    public static WxCpService build(RedissonClient redissonClient, String corpId, String cropSecret, Integer agentId) {
        WxCpRedissonConfigImpl configStorage = buildConfigStorage(redissonClient, corpId, cropSecret);
        configStorage.setAgentId(agentId);
        WxCpServiceImpl wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(configStorage);
        return wxCpService;
    }

    /**
     * 获取wxCpService
     * @param redissonClient
     * @param corpId
     * @param cropSecret
     * @param agentId
     * @param token
     * @param aeskey
     * @return
     */
    public static WxCpService build(RedissonClient redissonClient, String corpId, String cropSecret, Integer agentId, String token, String aeskey) {
        WxCpRedissonConfigImpl configStorage = buildConfigStorage(redissonClient, corpId, cropSecret);
        configStorage.setAgentId(agentId);
        configStorage.setToken(token);
        configStorage.setAesKey(aeskey);
        WxCpServiceImpl wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(configStorage);
        return wxCpService;
    }

    /**
     * 创建token存储对象
     * @param redissonClient
     * @param corpId
     * @param cropSecret
     * @return
     */
    private static WxCpRedissonConfigImpl buildConfigStorage(RedissonClient redissonClient, String corpId, String cropSecret){
        WxCpRedissonConfigImpl configStorage = new WxCpRedissonConfigImpl(redissonClient, WX_CP_SERVICE_KEY_PREFIX);
        configStorage.setCorpId(corpId);
        configStorage.setCorpSecret(cropSecret);
        return configStorage;
    }
}
