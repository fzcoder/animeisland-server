package com.fzcoder.opensource.animeisland.dto;

import com.fzcoder.opensource.animeisland.entity.VideoSource;
import lombok.Data;

import java.util.List;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/8/7 18:55
 */
@Data
public class VideoItemForm {
    private String id;
    private String uid;
    private String title;
    private String poster;
    private String description;
    private List<VideoSource> srcList;
}
