package com.fzcoder.opensource.animeisland.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tb_video_episode")
public class Episode {
    @TableId("id")
    private String id;
    @TableField("title")
    private String title;
    @TableField("bangumi_id")
    private String bangumiId;
    @TableField("video_id")
    private String videoId;
    @TableField("order_in_bangumi")
    private Integer orderInBangumi;
    @TableField("order_name")
    private String orderName;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("last_modify_time")
    private LocalDateTime lastModifyTime;
    @TableField("status")
    private Integer status;
}
