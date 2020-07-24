package com.animeisland.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BiliResponse<T> {

    private Integer code;

    private String message;

    private T result;
}
