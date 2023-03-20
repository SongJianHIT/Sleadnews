/**
 * @projectName heima-leadnews
 * @package com.heima.model.wemedia.dtos
 * @className com.heima.model.wemedia.dtos.WmLoginDto
 */
package com.heima.model.wemedia.dtos;

import lombok.Data;

/**
 * WmLoginDto
 * @description 自媒体登入 dto
 * @author SongJian
 * @date 2023/3/20 10:26
 * @version
 */
@Data
public class WmLoginDto {

    /**
     * 用户名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
}
