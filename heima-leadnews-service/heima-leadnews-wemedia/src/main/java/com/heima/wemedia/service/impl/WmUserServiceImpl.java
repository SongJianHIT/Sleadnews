/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia.service.impl
 * @className com.heima.wemedia.service.impl.WmUserServiceImpl
 */
package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.dtos.AppHttpCodeEnum;
import com.heima.common.dtos.ResponseResult;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.wemedia.dtos.WmLoginDto;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.BCrypt;
import com.heima.utils.common.JwtUtils;
import com.heima.utils.common.RsaUtils;
import com.heima.wemedia.mapper.WmUserMapper;
import com.heima.wemedia.service.WmUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * WmUserServiceImpl
 * @description
 * @author SongJian
 * @date 2023/3/20 10:21
 * @version
 */
@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {

    @Value("${leadnews.jwt.privateKeyPath}")
    private String privateKeyPath;

    @Value("${leadnews.jwt.expire}")
    private Integer expire;

    /**
     * 自媒体登入
     * @param wmLoginDto
     * @return
     */
    @Override
    public ResponseResult login(WmLoginDto wmLoginDto) {
        // 参数校验
        if (StringUtils.isEmpty(wmLoginDto.getName()) || StringUtils.isEmpty(wmLoginDto.getPassword())) {
            throw new LeadNewsException(AppHttpCodeEnum.PARAM_INVALID);
        }

        // 用户登入
        // 查询用户是否存在
        WmUser user = getOne(new QueryWrapper<WmUser>().eq("name", wmLoginDto.getName()));

        if (user == null) {
            throw new LeadNewsException(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
        }

        // 判断密码是否正确
        // 第一个参数：用户登入输入密码；第二个参数：数据库存储的真实密码
        if(!BCrypt.checkpw(wmLoginDto.getPassword(), user.getPassword())) {
            // 校验失败
            throw new LeadNewsException(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        // 生成 Token
        try {
            // 私钥对象
            PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyPath);
            // 去掉敏感数据
            user.setPassword(null);
            // 生成 jwt
            String token = JwtUtils.generateTokenExpireInMinutes(user, privateKey, expire);
            // 封装返回结果
            Map map = new HashMap<>();
            map.put("token", token);
            map.put("user", user);
            return ResponseResult.okResult(map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加载私钥失败");
        }
    }
}

