package com.animeisland.bean;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "PageQuery对象", description = "分页请求提交的实体")
public class PageQuery implements Serializable {

    private String key;

    private long pageNum;

    private long pageSize;

    private String[] orderBy;

    private boolean reverse;
}
