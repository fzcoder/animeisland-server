package com.fzcoder.opensource.animeisland.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/7/25 18:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_video_bangumi_tag_item")
public class BangumiTagItem {
    @TableId("id")
    private String id;
    @TableField("bangumi_id")
    private String bangumiId;
    @TableField("tag_id")
    private String tagId;
}
