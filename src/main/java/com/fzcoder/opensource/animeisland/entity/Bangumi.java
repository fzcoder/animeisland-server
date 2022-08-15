package com.fzcoder.opensource.animeisland.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tb_video_bangumi")
public class Bangumi {
    @TableId("id")
    private String id;
    @TableField("uid")
    private String uid;
    @TableField("title")
    private String title;
    @TableField("origin_title")
    private String originTitle;
    @TableField("description")
    private String description;
    @TableField("release_date")
    private String releaseDate;
    @TableField("release_year")
    private String releaseYear;
    @TableField("release_month")
    private String releaseMonth;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("last_modify_time")
    private LocalDateTime lastModifyTime;
    @TableField("cover")
    private String cover;
    @TableField("country")
    private String country;
    @TableField("status")
    private Integer status;
    @TableField("channel_id")
    private String channelId;
    @TableField("type_id")
    private String typeId;
    @TableField("grade_id")
    private String gradeId;
    @TableField("db_status")
    private Integer dbStatus;
}
