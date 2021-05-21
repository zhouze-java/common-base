package com.zhou.common.support.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 周泽
 * @date Create in 13:59 2021/3/16
 * @Description token 信息接口
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {

    private Long userId;

}
