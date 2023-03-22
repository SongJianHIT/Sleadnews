/**
 * @projectName heima-leadnews
 * @package PACKAGE_NAME
 * @className PACKAGE_NAME.TecentTest
 */

import com.alibaba.fastjson.JSONObject;
import com.heima.common.tecent.ImageDetection;
import com.heima.common.tecent.TextDetection;
import com.heima.wemedia.WemediaApplication;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * TecentTest
 * @description
 * @author SongJian
 * @date 2023/3/22 19:20
 * @version
 */
@SpringBootTest(classes = WemediaApplication.class)
@RunWith(SpringRunner.class)
public class TecentTest {

    @Autowired
    private TextDetection textDetection;
    @Autowired
    private ImageDetection imageDetection;

    @Test
    public void textTest() throws TencentCloudSDKException {
        JSONObject result_json = textDetection.greenTextDetection("冰毒");
        String result = (String) result_json.get("Suggestion");

        System.out.println(result);
    }

    @Test
    public void imageTest() throws TencentCloudSDKException {
        JSONObject result_json = imageDetection.greenImageDetection("http://124.71.6.117:9000/leadnewsedia/2023/03/22/ce7301bc056244189ad964dfe07edb15.jpeg");
        String result = (String) result_json.get("Suggestion");
        System.out.println(result);
    }
}

