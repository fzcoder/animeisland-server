package com.fzcoder.opensource.animeisland.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tb_video_series")
public class Series {
    @TableId("id")
    private String id;
    @TableField("uid")
    private String uid;
    @TableField("name")
    private String name;
    @TableField("description")
    private String description;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("last_modify_time")
    private LocalDateTime lastModifyTime;
    @TableField("db_status")
    private Integer dbStatus;
}
