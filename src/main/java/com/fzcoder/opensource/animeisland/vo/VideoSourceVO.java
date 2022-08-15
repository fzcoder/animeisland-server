package com.fzcoder.opensource.animeisland.vo;

import lombok.Data;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/8/7 18:25
 */
@Data
public class VideoSourceVO {
    private String id;
    private String uid;
    private String videoId;
    private Integer width;
    private Integer height;
    private String quality;
    private String mimeType;
    private String screenshot;
}
