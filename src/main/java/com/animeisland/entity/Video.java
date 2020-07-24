package com.animeisland.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("vms_video")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="Video对象", description="")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id，作为唯一标识")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "所属media的id")
    @TableField("media_id")
    private Integer mediaId;

    @ApiModelProperty(value = "文件所在路径")
    @TableField("filepath")
    private String filepath;

    @ApiModelProperty(value = "文件名称")
    @TableField("filename")
    private String filename;

    @ApiModelProperty(value = "所含有的画质，不同画质以逗号隔开")
    @TableField("quality")
    private String quality;

    @ApiModelProperty(value = "视频在media中的顺序")
    @TableField("order_in_media")
    private int orderInMedia;

    @ApiModelProperty(value = "集数")
    @TableField("part_number")
    private String partNumber;

    @ApiModelProperty(value = "单位")
    @TableField("part_unit")
    private String partUnit;

}
