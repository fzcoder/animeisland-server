package com.fzcoder.opensource.animeisland.vo;

import com.fzcoder.opensource.animeisland.entity.BangumiGrade;
import com.fzcoder.opensource.animeisland.entity.BangumiTag;
import com.fzcoder.opensource.animeisland.entity.BangumiType;
import com.fzcoder.opensource.animeisland.entity.Channel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/8/6 4:48
 */
@Data
public class BangumiVO {
    private String id;
    private String uid;
    private String title;
    private String originTitle;
    private String description;
    private String releaseDate;
    private String releaseYear;
    private String releaseMonth;
    private LocalDateTime createTime;
    private LocalDateTime lastModifyTime;
    private String cover;
    private String country;
    private Integer status;
    private Channel channel;
    private BangumiType type;
    private BangumiGrade grade;
    private List<BangumiTag> tags;
}
