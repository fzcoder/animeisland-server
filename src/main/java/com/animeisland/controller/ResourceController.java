package com.animeisland.controller;

import com.animeisland.entity.Video;
import com.animeisland.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("/api/resource")
@PropertySource(value = {"classpath:application.properties"}, encoding = "utf-8")
public class ResourceController {

    @Autowired
    private VideoService videoService;

    @Value("${resource.server.url}")
    private String baseURL;

    @GetMapping("/video/{id}")
    public Object getVideoResource(@PathVariable("id") Serializable id, @RequestParam("quality") String quality) {
        Video video = videoService.getById(id);
        return baseURL + video.getFilepath() + "/" + quality + "/" + video.getFilename();
    }
}
