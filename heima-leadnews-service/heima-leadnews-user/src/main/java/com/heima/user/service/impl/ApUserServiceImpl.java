/**
 * @projectName heima-leadnews
 * @package com.heima.user.service.impl
 * @className com.heima.user.service.impl.ApUserServiceImpl
 */
package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.ApUserService;
import com.heima.utils.common.BCrypt;
import com.heima.utils.common.JwtUtils;
import com.heima.utils.common.RsaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * ApUserServiceImpl
 * @description
 * @author SongJian
 * @date 2023/3/14 16:13
 * @version
 */

/**
 * ServiceImpl 相当于 IService 的实现
 */
@Service
@Transactional
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {

    @Value("${leadnews.jwt.privateKeyPath}")
    private String privateKeyPath;

    @Value("${leadnews.jwt.expire}")
    private Integer expire;

    /**
     * 用户登入接口
     * @param loginDto
     * @return
     */
    @Override
    public ResponseResult login(LoginDto loginDto) {

        // 判断用户还是游客
        if (StringUtils.isNotEmpty(loginDto.getPhone()) && StringUtils.isNotEmpty(loginDto.getPassword())) {
            // 用户登入
            // 查询用户是否存在
            ApUser user = getOne(new QueryWrapper<ApUser>().eq("phone", loginDto.getPhone()));
            if (user == null) {
                throw new RuntimeException("用户不存在");
            }

            // 判断密码是否正确
            // 第一个参数：用户登入输入密码；第二个参数：数据库存储的真实密码
            if(!BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {
                // 校验失败
                throw new RuntimeException("密码错误");
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
        } else {
            // 游客登入
            // 生成 Token
            try {
                // 私钥对象
                PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyPath);
                // 创建user，id=0为游客
                ApUser apUser = new ApUser();
                apUser.setId(0);
                // 生成 jwt
                String token = JwtUtils.generateTokenExpireInMinutes(apUser, privateKey, expire);
                // 封装返回结果
                Map map = new HashMap<>();
                map.put("token", token);
                map.put("user", apUser);
                return ResponseResult.okResult(map);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("加载私钥失败");
            }
        }
    }
}
