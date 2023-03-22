/**
 * @projectName heima-leadnews
 * @package com.heima.common.tecent
 * @className com.heima.common.tecent.ImageDetection
 */
package com.heima.common.tecent;

/**
 * ImageDetection
 * @description
 * @author SongJian
 * @date 2023/3/22 19:20
 * @version
 */
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ims.v20201229.ImsClient;
import com.tencentcloudapi.ims.v20201229.models.*;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "tencentcloud")
public class ImageDetection {
    private String secretId;
    private String secretKey;

    public JSONObject greenImageDetection(String imageUrl) throws TencentCloudSDKException {
        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        Credential cred = new Credential(secretId, secretKey);

        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("ims.tencentcloudapi.com");

        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        // 实例化要请求产品的client对象,clientProfile是可选的
        ImsClient client = new ImsClient(cred, "ap-guangzhou", clientProfile);

        // 实例化一个请求对象,每个接口都会对应一个request对象
        ImageModerationRequest req = new ImageModerationRequest();
        //设置图片url地址
        req.setFileUrl(imageUrl);

        // 返回的resp是一个ImageModerationResponse的实例，与请求对象对应
        ImageModerationResponse resp = client.ImageModeration(req);

        // 输出json格式的字符串回包
        String result = ImageModerationResponse.toJsonString(resp);

        return JSON.parseObject(result);
    }

}

