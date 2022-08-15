package com.fzcoder.opensource.animeisland.controller;

import com.fzcoder.opensource.animeisland.util.response.R;
import com.fzcoder.opensource.animeisland.dto.VideoItemForm;
import com.fzcoder.opensource.animeisland.entity.VideoItem;
import com.fzcoder.opensource.animeisland.entity.VideoSource;
import com.fzcoder.opensource.animeisland.service.IVideoItemService;
import com.fzcoder.opensource.animeisland.service.IVideoSourceService;
import com.fzcoder.opensource.animeisland.util.IdGenerator;
import com.fzcoder.opensource.animeisland.vo.VideoItemVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/video")
public class VideoItemController {
    private final IVideoItemService videoItemService;
    private final IVideoSourceService videoSourceService;

    @Autowired
    public VideoItemController(@Qualifier("CloudXVideo_VideoItemServiceImpl") IVideoItemService videoItemService,
                               @Qualifier("video_VideoSourceServiceImpl") IVideoSourceService videoSourceService) {
        this.videoItemService = videoItemService;
        this.videoSourceService = videoSourceService;
    }

    @PostMapping("/item")
    public R save(@RequestBody VideoItemForm form) {
        LocalDateTime currentDatetime = LocalDateTime.now();
        VideoItem item = new VideoItem();
        String videoId = IdGenerator.generateVideoItemId();
        item.setId(IdGenerator.generateVideoItemId());
        item.setUid(form.getUid());
        item.setTitle(form.getTitle());
        item.setDescription(form.getDescription());
        item.setPoster(form.getPoster());
        item.setCreateTime(currentDatetime);
        item.setLastModifyTime(currentDatetime);
        item.setDbStatus(0);
        for (VideoSource source : form.getSrcList()) {
            source.setId(IdGenerator.generateVideoItemId());
            source.setVideoId(videoId);
            source.setCreateTime(currentDatetime);
            source.setLastModifyTime(currentDatetime);
            source.setDbStatus(0);
        }
        if (form.getSrcList().size() > 0) {
            if (!videoSourceService.saveBatch(form.getSrcList())) {
                return R.badRequest("添加失败!", null);
            }
        }
        if (videoItemService.save(item)) {
            return R.ok("添加成功!", null);
        } else {
            return R.badRequest("添加失败!", null);
        }
    }

    @PostMapping("/item/batch")
    public R saveBatch(@RequestBody List<VideoItemForm> forms) {
        List<VideoItem> items = new ArrayList<>();
        for (VideoItemForm form : forms) {
            LocalDateTime currentDatetime = LocalDateTime.now();
            VideoItem item = new VideoItem();
            String videoId = IdGenerator.generateVideoItemId();
            item.setId(videoId);
            item.setUid(form.getUid());
            item.setTitle(form.getTitle());
            item.setDescription(form.getDescription());
            item.setPoster(form.getPoster());
            item.setCreateTime(currentDatetime);
            item.setLastModifyTime(currentDatetime);
            item.setDbStatus(0);
            for (VideoSource source : form.getSrcList()) {
                source.setId(IdGenerator.generateVideoItemId());
                source.setVideoId(videoId);
                source.setCreateTime(currentDatetime);
                source.setLastModifyTime(currentDatetime);
                source.setDbStatus(0);
            }
            items.add(item);
            if (form.getSrcList().size() > 0) {
                if (!videoSourceService.saveBatch(form.getSrcList())) {
                    return R.badRequest("添加失败!", null);
                }
            }
        }
        if (videoItemService.saveBatch(items)) {
            return R.ok("添加成功!", null);
        } else {
            return R.badRequest("添加失败!", null);
        }
    }

    @PostMapping("/item/batch/upload")
    public R parseJsonMultipleForm (HttpServletRequest request) {
        return R.ok(this.videoItemService.parseJsonMultipleForm(request));
    }

    @GetMapping("/item/form/{id}")
    public R getVideoItemForm(@PathVariable("id") String id) {
        return R.ok(videoItemService.getById(id));
    }

    @GetMapping("/item/play/{id}")
    public R getVideoItemPlay(@PathVariable("id") String id) {
        return R.ok(videoItemService.getVoById(id));
    }

    @GetMapping("/item/page")
    public R getPage(@RequestParam("uid") String uid,
                     @RequestParam("key") String key,
                     @RequestParam("page_num") long pageNum,
                     @RequestParam("page_size") long pageSize) {
        QueryWrapper<VideoItemVO> qw = new QueryWrapper<>();
        // qw.eq("uid", uid);
        qw.eq("db_status", 0);
        qw.like("title", key);
        qw.like("description", key);
        qw.orderBy(true, false, "create_time");
        return R.ok(videoItemService.voPage(new Page<>(pageNum, pageSize), qw));
    }

    @PutMapping("/item")
    public R update(@RequestBody VideoItem item) {
        item.setLastModifyTime(LocalDateTime.now());
        item.setDbStatus(0);
        if (videoItemService.updateById(item)) {
            return R.ok("修改成功!", null);
        } else {
            return R.badRequest("修改失败!", null);
        }
    }

    @DeleteMapping("/item/{id}")
    public R delete(@PathVariable("id") String id) {
        UpdateWrapper<VideoItem> uw = new UpdateWrapper<>();
        uw.eq("id", id);
        uw.set("last_modify_time", LocalDateTime.now());
        uw.set("db_status", -1);
        if (videoItemService.update(null, uw)) {
            return R.ok("删除成功!", null);
        } else {
            return R.badRequest("删除失败!", null);
        }
    }
    @DeleteMapping("/item/batch")
    public R deleteBatch(@RequestParam("ids") List<String> ids) {
        for (String id : ids) {
            UpdateWrapper<VideoItem> uw = new UpdateWrapper<>();
            uw.eq("id", id);
            uw.set("last_modify_time", LocalDateTime.now());
            uw.set("db_status", -1);
            if (!videoItemService.update(null, uw)) {
                return R.badRequest("删除失败!", null);
            }
        }
        return R.ok("删除成功!", null);
    }

}
