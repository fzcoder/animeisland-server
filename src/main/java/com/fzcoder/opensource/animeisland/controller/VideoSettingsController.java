package com.fzcoder.opensource.animeisland.controller;

import com.fzcoder.opensource.animeisland.util.response.R;
import com.fzcoder.opensource.animeisland.entity.Settings;
import com.fzcoder.opensource.animeisland.service.IVideoSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/7/25 22:44
 */
@RestController
@RequestMapping("/api/video")
public class VideoSettingsController {
    private final IVideoSettingsService videoSettingsService;

    @Autowired
    public VideoSettingsController(@Qualifier("video_VideoSettingsServiceImpl") IVideoSettingsService videoSettingsService) {
        this.videoSettingsService = videoSettingsService;
    }

    /* @GetMapping("/settings")
    public R getVideoSettings(@RequestParam("user_id") String userId) {
        return R.ok(videoSettingsService.getVideoSettingsByUserId(userId));
    }

    @PutMapping("/settings")
    public R updateVideoSettings(@RequestBody Settings settings) {
        if (videoSettingsService.updateById(settings)) {
            return R.ok("", null);
        } else {
            return R.badRequest("", null);
        }
    } */
}
