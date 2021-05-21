package com.zhou.common.config.constants;

/**
 * @author 周泽
 * @date Create in 21:41 2020/11/4
 * @Description Grpc状态码定义
 */
public class ResponseCodeConstants {

    /**
     * 请求成功
     */
    public final static int SUCCESS = 10000;

    /**
     * 状态正常可用
     */
    public final static int AVAILABLE = 10001;

    /**
     * 过期了
     */
    public final static int EXPIRE = 10002;

    /**
     * 审核未通过
     */
    public final static int AUDIT_NOT_PASS = 10003;

    /**
     * 商家状态已关闭
     */
    public final static int NOT_AVAILABLE = 10004;

    /**
     * 服务端错误
     */
    public final static int SERVER_ERROR = 10005;

    /**
     * 找不到对应资源
     */
    public final static int RESOURCE_NOT_FOUND = 10006;

    /**
     *  缺少先决条件
     */
    public final static int PRECONDITION_REQUIRED = 10007;

    /**
     * 未发起申请
     */
    public final static int NOT_APPLY = 10008;

    /**
     * 待审核
     */
    public final static int TO_AUDIT = 10009;

    /**
     * 红包抢完了
     */
    public final static int RED_PACKET_EMPTY = 10010;

    /**
     * 重试
     */
    public final static int RED_PACKET_RETRY = 10011;

    /**
     * 客户端参数错误
     */
    public final static int PARAM_ERROR = 10012;

    /**
     * 条件没通过
     */
    public final static int FAILED_VALIDATION = 10013;

}
