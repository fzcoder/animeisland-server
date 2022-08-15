package com.fzcoder.opensource.animeisland.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName("tb_video_item")
@NoArgsConstructor
@AllArgsConstructor
public class VideoItem {
    @TableId("id")
    private String id;
    @TableField("uid")
    private String uid;
    @TableField("title")
    private String title;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("last_modify_time")
    private LocalDateTime lastModifyTime;
    @TableField("description")
    private String description;
    @TableField("poster")
    private String poster;
    @TableField("db_status")
    private Integer dbStatus;
}
