package com.fzcoder.opensource.animeisland.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/8/20 1:11
 */
@Data
@TableName("tb_video_series_item")
public class SeriesItem {
    @TableId("id")
    private String id;
    @TableField("name")
    private String name;
    @TableField("series_id")
    private String seriesId;
    @TableField("bangumi_id")
    private String bangumiId;
    @TableField("order_in_series")
    private Integer orderInSeries;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("last_modify_time")
    private LocalDateTime lastModifyTime;
}
