package com.fzcoder.opensource.animeisland.controller;

import com.fzcoder.opensource.animeisland.util.response.R;
import com.fzcoder.opensource.animeisland.util.IdGenerator;
import com.fzcoder.opensource.animeisland.entity.Channel;
import com.fzcoder.opensource.animeisland.service.IChannelService;
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
 * @date 2022/7/24 21:26
 */
@RestController
@RequestMapping("/api/video")
public class ChannelController {
    private final IChannelService channelService;

    @Autowired
    public ChannelController(@Qualifier("video_ChannelServiceImpl") IChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping("/channel")
    public R addChannel(@RequestBody Channel channel) {
        // Form verification
        // TODO 实现服务端的表单验证
        if (!channelService.isFormValid(channel)) {
            return R.badRequest("Invalid Form", null);
        }
        LocalDateTime currentDateTime = LocalDateTime.now();
        channel.setId(IdGenerator.generateUUID());
        channel.setCreateTime(currentDateTime);
        channel.setLastModifyTime(currentDateTime);
        channel.setDbStatus(0);
        if (channelService.save(channel)) {
            return R.ok("Successfully Added", null);
        } else {
            return R.badRequest("Add Failed", null);
        }
    }

    @GetMapping("/channel/{id}")
    public R getChannelById(@PathVariable("id") String id) {
        return R.ok(channelService.getById(id));
    }

    @GetMapping("/channel")
    public R getUserChannelList(@RequestParam("uid") String uid, @RequestParam Map<String, Object> params) {
        QueryWrapper<Channel> qw = new QueryWrapper<>();
        qw.eq("uid", uid);
        qw.eq("db_status", 0);
        qw.like(params.containsKey("key"), "name", params.get("key"));
        qw.orderBy(true, true, "name");
        return R.ok(channelService.list(qw));
    }

    @GetMapping("/channel/page")
    public R getUserChannelListPage(@RequestParam("page_num") long pageNum,
                                @RequestParam("page_size") long pageSize,
                                @RequestParam("uid") String uid,
                                @RequestParam Map<String, Object> params) {
        QueryWrapper<Channel> qw = new QueryWrapper<>();
        // qw.eq("uid", uid);
        qw.eq("db_status", 0);
        qw.like(params.containsKey("key"), "name", params.get("key"));
        qw.orderBy(true, false, "create_time");
        return R.ok(channelService.page(new Page<>(pageNum, pageSize), qw));
    }

    @PutMapping("/channel")
    public R updateChannel(@RequestBody Channel channel) {
        UpdateWrapper<Channel> uw = new UpdateWrapper<>();
        uw.eq("id", channel.getId());
        uw.set("name", channel.getName());
        uw.set("unique_access_path", channel.getUniqueAccessPath());
        uw.set("description", channel.getDescription());
        uw.set("last_modify_time", LocalDateTime.now());
        if (channelService.update(uw)) {
            return R.ok("Successfully Updated", null);
        } else {
            return R.badRequest("Update Failed", null);
        }
    }

    @DeleteMapping("/channel/{id}")
    public R deleteChannel(@PathVariable("id") String id) {
        UpdateWrapper<Channel> uw = new UpdateWrapper<>();
        uw.eq("id", id);
        uw.set("db_status", -1);
        uw.set("last_modify_time", LocalDateTime.now());
        if (channelService.update(uw)) {
            return R.ok("Successfully Delete", null);
        } else {
            return R.badRequest("Delete Failed", null);
        }
    }

    @DeleteMapping("/channel/batch")
    public R deleteChannelsByIds(@RequestParam("ids") List<String> ids) {
        for (String id : ids) {
            UpdateWrapper<Channel> uw = new UpdateWrapper<>();
            uw.eq("id", id);
            uw.set("db_status", -1);
            uw.set("last_modify_time", LocalDateTime.now());
            if (!channelService.update(uw)) {
                return R.badRequest("Delete Failed", null);
            }
        }
        return R.ok("Successfully Delete", null);
    }
}
