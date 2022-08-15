package com.fzcoder.opensource.animeisland.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/7/24 19:22
 */
@Data
@TableName("tb_video_channel")
public class Channel implements Serializable {
    private static final long serialVersionUID = -4146049629718605945L;
    @TableId("id")
    private String id;
    @TableField("name")
    private String name;
    @TableField("uid")
    private String uid;
    @TableField("description")
    private String description;
    @TableField("unique_access_path")
    private String uniqueAccessPath;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("last_modify_time")
    private LocalDateTime lastModifyTime;
    @TableField("db_status")
    private Integer dbStatus;
}
