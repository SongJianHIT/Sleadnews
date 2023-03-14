/**
 * @projectName heima-leadnews
 * @package com.heima
 * @className com.heima.RsaTest
 */
package com.heima;

import com.heima.utils.common.RsaUtils;
import org.junit.Test;

import java.security.PrivateKey;

/**
 * RsaTest
 * @description
 * @author SongJian
 * @date 2023/3/14 16:45
 * @version
 */
public class RsaTest {

    String privateKeyPath = "/Users/mac/Desktop/News/passwd/id-rsa";
    @Test
    public void TestRsa() throws Exception {
        //RsaUtils.generateKey("/Users/mac/Desktop/News/passwd/id-rsa.pub", "/Users/mac/Desktop/News/passwd/id-rsa", "weiwei1216*", 2048);
        PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyPath);
        System.out.println(privateKey);
    }
}

