package com.animeisland.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author frank fang
 * @since 2020-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("vms_media")
@ApiModel(value="Media对象", description="")
public class Media implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id，作为唯一的表示")
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    @ApiModelProperty(value = "中文名称")
    @TableField("name_zh")
    private String nameZh;

    @ApiModelProperty(value = "原名")
    @TableField("name_orgin")
    private String nameOrgin;

    @ApiModelProperty(value = "名称首字母")
    @TableField("name_firstchar")
    private String nameFirstchar;

    @ApiModelProperty(value = "所属目录")
    @TableField("category_name")
    private String categoryName;

    @ApiModelProperty(value = "类型")
    @TableField("type_name")
    private String typeName;

    @ApiModelProperty(value = "总集数")
    @TableField("total")
    private Integer total;

    @ApiModelProperty(value = "上映时间")
    @TableField("release_time")
    private String releaseTime;

    @ApiModelProperty(value = "简介")
    @TableField("introduction")
    private String introduction;

    @ApiModelProperty(value = "分级")
    @TableField("level")
    private String level;

    @ApiModelProperty(value = "标签，不同标签以\",\"隔开")
    @TableField("tags")
    private String tags;

    @ApiModelProperty(value = "状态，0代表未上映，1代表正在连载，2代表已完结")
    @TableField("status")
    private int status;

    @ApiModelProperty(value = "更新日期，0-6分别代表周一到周日")
    @TableField("update_date")
    private int updateDate;

    @ApiModelProperty(value = "封面链接")
    @TableField("cover_url")
    private String coverUrl;

    @ApiModelProperty(value = "视频截图链接")
    @TableField("screenshot_url")
    private String screenshotUrl;

    @ApiModelProperty(value = "权重，代表推荐程度，从0开始无限增加")
    @TableField("weight")
    private Integer weight;

    @ApiModelProperty(value = "是否上首页推荐，0代表不上，1代表上")
    @TableField("homepage_recommend")
    private Boolean homepageRecommend;

    @ApiModelProperty(value = "是否属于某一系列，0代表不属于，1代表属于")
    @TableField("has_series")
    private Boolean hasSeries;

    @ApiModelProperty(value = "所属系列id")
    @TableField("series_id")
    private Integer seriesId;

    @ApiModelProperty(value = "在系列中的名称")
    @TableField("name_in_series")
    private String nameInSeries;

    @ApiModelProperty(value = "在系列中的名称")
    @TableField("order_in_series")
    private int orderInSeries;

}
