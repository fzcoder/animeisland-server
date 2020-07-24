package com.animeisland.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BiliResponse2<T> {

    private Integer code;

    private String message;

    private int ttl;

    private T data;
}
