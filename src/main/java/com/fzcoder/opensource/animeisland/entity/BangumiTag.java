package com.fzcoder.opensource.animeisland.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/7/25 14:28
 */
@Data
@TableName("tb_video_bangumi_tag")
public class BangumiTag {
    @TableId("id")
    private String id;
    @TableField("name")
    private String name;
    @TableField("color_hex")
    private String colorHex;
    @TableField("channel_id")
    private String channelId;
    @TableField("user_id")
    private String userId;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("last_modify_time")
    private LocalDateTime lastModifyTime;
    @TableField("db_status")
    private Integer dbStatus;
}
