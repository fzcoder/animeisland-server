package com.animeisland.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "BaseResponse对象", description = "基本返回数据类型")
public class BaseResponse {

    @ApiModelProperty(value = "HTTP状态码")
    private int status;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "数据")
    private Object data;

    public BaseResponse(int status, String message) {
        this.setStatus(status);
        this.setMessage(message);
    }

    public BaseResponse(Object data) {
        this.setStatus(HttpServletResponse.SC_OK);
        this.setData(data);
    }
}
