package com.heima;

import com.heima.utils.common.BCrypt;
import org.junit.Test;

public class BCryptTest {

    /**
     * 加密
     */
    @Test
    public void testEncode(){
        //产生随机盐
        String salt = BCrypt.gensalt();
        System.out.println(salt);
        //加密
        // $2a$10$PhDUdMVcPDFaKdQOu5x.4uhigBiupt4zek/Ox2N54aYG/mcFFZvYG
        String pwd = BCrypt.hashpw("admin", salt);
        System.out.println(pwd);
    }

    /**
     * 验证
     */
    @Test
    public void testMatch(){
        String pwd = "$2a$10$jnVLy6dZx3EWuNWJDfgss.KWbMQeZc/Ea1hwhsQmi/mMEDd9qNXC2";
        boolean flag = BCrypt.checkpw("admin", pwd);
        System.out.println(flag);
    }
}
