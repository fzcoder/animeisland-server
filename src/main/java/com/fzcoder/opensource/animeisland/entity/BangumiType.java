package com.fzcoder.opensource.animeisland.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/7/25 14:27
 */
@Data
@TableName("tb_video_bangumi_type")
public class BangumiType {
    @TableId("id")
    private String id;
    @TableField("name")
    private String name;
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
