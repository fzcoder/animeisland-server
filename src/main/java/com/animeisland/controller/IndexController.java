package com.animeisland.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.animeisland.bean.BaseResponse;
import com.animeisland.bean.BiliResponse;
import com.animeisland.bean.BiliResponse2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class IndexController {

    private static final String BILIBILI_API_RANKING = "https://api.bilibili.com/x/web-interface/ranking/region?rid=33&day=3&original=0";

    private static final String BILIBILI_API_TIMETABLE = "https://bangumi.bilibili.com/web_api/timeline_global";

    private StringBuilder connectServer(String apiURL) {
        try {
            // 从bilibili服务器获取数据
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) { // 读取数据
                result.append(line).append("\n");
            }
            connection.disconnect();
            return result;
        } catch (Exception e) {
            log.error("调用API异常！", e);
            return null;
        }
    }

    @GetMapping("/timetable")
    public Object getTimetable() {
        BiliResponse<List<Map<String, Object>>> biliResponse;
        try {
            biliResponse = new ObjectMapper().readValue(connectServer(BILIBILI_API_TIMETABLE).toString(), BiliResponse.class);
        } catch (Exception e) {
            log.error("调用API异常", e);
            return new BaseResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务端异常！");
        }
        if (!biliResponse.getCode().equals(0)) {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, biliResponse.getMessage());
        } else {
            // 截取本周的新番时间表
            int i = 0;
            while (!biliResponse.getResult().get(i++).get("is_today").equals(1));
            // 获取本日的星期
            int now = (int) biliResponse.getResult().get(i).get("day_of_week");
            // 本周开始时间的下标
            int start = i - ( now - 1);
            // 本周结束时间的下标
            int end = i + (7 - now);
            List<Map<String, Object>> list = new LinkedList<>();
            for (int j = start; j <= end; j++) {
                list.add(biliResponse.getResult().get(j));
            }
            return new BaseResponse(list);
        }
    }

    @GetMapping("/ranking")
    public Object getRankingList(@RequestParam Map<String, Object> params) {
        BiliResponse2<List<Map<String, Object>>> biliResponse;
        try {
            biliResponse = new ObjectMapper().readValue(connectServer(BILIBILI_API_RANKING).toString(), BiliResponse2.class);
        } catch (Exception e) {
            log.error("调用API异常", e);
            return new BaseResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务端异常！");
        }
        if (!biliResponse.getCode().equals(0)) {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, biliResponse.getMessage());
        } else {
            List<Map<String, Object>> list = new LinkedList<>();
            for (Map<String, Object> item : biliResponse.getData()) {
                Map<String, Object> elem = new HashMap<>();
                elem.put("bvid", item.get("bvid"));
                elem.put("title", item.get("title"));
                elem.put("play", item.get("play"));
                elem.put("pts", item.get("pts"));
                list.add(elem);
            }
            return new BaseResponse(list);
        }
    }
}
