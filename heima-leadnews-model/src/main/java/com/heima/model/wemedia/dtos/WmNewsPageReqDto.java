/**
 * @projectName heima-leadnews
 * @package com.heima.model.wemedia.dtos
 * @className com.heima.model.wemedia.dtos.WmNewsPageReqDto
 */
package com.heima.model.wemedia.dtos;

import com.heima.common.dtos.PageRequestDto;
import lombok.Data;

import java.util.Date;

/**
 * WmNewsPageReqDto
 * @description 定义自媒体文章查询条件的Dto
 * @author SongJian
 * @date 2023/3/20 19:34
 * @version
 */
@Data
public class WmNewsPageReqDto extends PageRequestDto {

    /**
     * 状态
     */
    private Short status;
    /**
     * 开始时间
     */
    private Date beginPubDate;
    /**
     * 结束时间
     */
    private Date endPubDate;
    /**
     * 所属频道ID
     */
    private Integer channelId;
    /**
     * 关键字
     */
    private String keyword;
}

