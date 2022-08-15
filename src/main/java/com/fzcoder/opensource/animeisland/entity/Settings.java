package com.fzcoder.opensource.animeisland.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/7/25 22:01
 */
@Data
@TableName("tb_video_settings")
public class Settings {
    @TableId("id")
    private String id;
    @TableField("user_id")
    private String userId;
    @TableField("website_host")
    private String websiteHost;
    @TableField("website_brand")
    private String websiteBrand;
}
