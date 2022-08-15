package com.fzcoder.opensource.animeisland.controller;

import com.fzcoder.opensource.animeisland.util.response.R;
import com.fzcoder.opensource.animeisland.util.IdGenerator;
import com.fzcoder.opensource.animeisland.entity.VideoSource;
import com.fzcoder.opensource.animeisland.service.IVideoSourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/8/7 18:59
 */
@RestController
@RequestMapping("/api/video")
public class VideoSourceController {
    private final IVideoSourceService videoSourceService;

    @Autowired
    public VideoSourceController(@Qualifier("video_VideoSourceServiceImpl") IVideoSourceService videoSourceService) {
        this.videoSourceService = videoSourceService;
    }

    @PostMapping("/source")
    public R addVideoSource(@RequestBody VideoSource source) {
        source.setId(IdGenerator.generateUUID());
        LocalDateTime currentDatetime = LocalDateTime.now();
        source.setCreateTime(currentDatetime);
        source.setLastModifyTime(currentDatetime);
        source.setDbStatus(0);
        if (videoSourceService.save(source)) {
            return R.ok("添加成功", null);
        } else {
            return R.badRequest("添加失败", null);
        }
    }

    @PostMapping("/source/batch")
    public R addVideoSourceBatch(@RequestBody List<VideoSource> sourceList) {
        for (VideoSource source : sourceList) {
            source.setId(IdGenerator.generateUUID());
            LocalDateTime currentDatetime = LocalDateTime.now();
            source.setCreateTime(currentDatetime);
            source.setLastModifyTime(currentDatetime);
            source.setDbStatus(0);
        }
        if (videoSourceService.saveBatch(sourceList)) {
            return R.ok("添加成功", null);
        } else {
            return R.badRequest("添加失败", null);
        }
    }

    @GetMapping("/source/{id}")
    public R getVideoSourceForm(@PathVariable("id") String id) {
        return R.ok(videoSourceService.getById(id));
    }

    @GetMapping("/source/play/{id}")
    public R getVideoSourcePlay(@PathVariable("id") String id) {
        return R.ok(videoSourceService.getWithSignatureUrlById(id));
    }

    @GetMapping("/source/page")
    public R getVideoSourcePage(@RequestParam("key") String keyword,
                                @RequestParam("page_num") long pageNum,
                                @RequestParam("page_size") long pageSize,
                                @RequestParam Map<String, Object> params) {
        QueryWrapper<VideoSource> qw = new QueryWrapper<>();
        qw.eq("db_status", 0);
        if (params.containsKey("video_id") && !"all".equals(params.get("video_id").toString())) {
            qw.eq("video_id", params.get("video_id").toString());
        }
        qw.like("quality", keyword);
        qw.orderBy(true, false, "create_time");
        return R.ok(videoSourceService.page(new Page<>(pageNum, pageSize), qw));
    }

    @PutMapping("/source")
    public R updateVideoSource(@RequestBody VideoSource source) {
        source.setLastModifyTime(LocalDateTime.now());
        source.setDbStatus(0);
        if (videoSourceService.updateById(source)) {
            return R.ok("修改成功", null);
        } else {
            return R.badRequest("修改失败", null);
        }
    }

    @DeleteMapping("/source/{id}")
    public R deleteVideoSource(@PathVariable("id") String id) {
        UpdateWrapper<VideoSource> uw = new UpdateWrapper<>();
        uw.set("last_modify_time", LocalDateTime.now());
        uw.set("db_status", -1);
        uw.eq("id", id);
        if (videoSourceService.update(uw)) {
            return R.ok("删除成功", null);
        } else {
            return R.badRequest("删除失败", null);
        }
    }

    @DeleteMapping("/source/batch")
    public R deleteVideoSourceBatch(@RequestParam("ids") List<String> ids) {
        for (String id : ids) {
            UpdateWrapper<VideoSource> uw = new UpdateWrapper<>();
            uw.set("last_modify_time", LocalDateTime.now());
            uw.set("db_status", -1);
            uw.eq("id", id);
            if (!videoSourceService.update(uw)) {
                return R.badRequest("删除失败", null);
            }
        }
        return R.ok("删除成功", null);
    }
}
