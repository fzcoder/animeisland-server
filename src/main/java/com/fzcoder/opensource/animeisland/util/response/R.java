package com.fzcoder.opensource.animeisland.util.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class R implements Serializable {
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("data")
    private Object data;

    private R(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static R ok(Object data) {
        return ok("", data);
    }

    public static R ok(String msg, Object data) {
        return new R(StatusCode.SC_OK, msg, data);
    }

    public static R badRequest(Object data) {
        return badRequest("", data);
    }

    public static R badRequest(String msg, Object data) {
        return new R(StatusCode.SC_BAD_REQUEST, msg, data);
    }

    public static R unauthorized(Object data) {
        return unauthorized("", data);
    }

    public static R unauthorized(String msg, Object data) {
        return new R(StatusCode.SC_UNAUTHORIZED, msg, data);
    }

    public static R notFound(Object data) {
        return notFound("", data);
    }

    public static R notFound(String msg, Object data) {
        return new R(StatusCode.SC_NOT_FOUND, msg, data);
    }

    public static R internalServerError(Object data) {
        return internalServerError("", data);
    }

    public static R internalServerError(String msg, Object data) {
        return new R(StatusCode.SC_INTERNAL_SERVER_ERROR, msg, data);
    }
}
