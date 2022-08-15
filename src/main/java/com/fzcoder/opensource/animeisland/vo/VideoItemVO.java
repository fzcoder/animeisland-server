package com.fzcoder.opensource.animeisland.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/8/7 16:23
 */
@Data
public class VideoItemVO {
    private String id;
    private String uid;
    private String title;
    private String poster;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime lastModifyTime;
    private List<VideoSourceVO> srcList;
}
