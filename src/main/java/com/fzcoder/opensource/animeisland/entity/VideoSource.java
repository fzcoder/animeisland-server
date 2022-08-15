package com.fzcoder.opensource.animeisland.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/8/7 15:11
 */
@Data
@TableName("tb_video_source")
public class VideoSource {
    @TableId("id")
    private String id;
    @TableField("uid")
    private String uid;
    @TableField("video_id")
    private String videoId;
    @TableField("width")
    private Integer width;
    @TableField("height")
    private Integer height;
    @TableField("quality")
    private String quality;
    @TableField("mime_type")
    private String mimeType;
    @TableField("url")
    private String url;
    @TableField("screenshot")
    private String screenshot;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("last_modify_time")
    private LocalDateTime lastModifyTime;
    @TableField("db_status")
    private Integer dbStatus;
}
