package com.fzcoder.opensource.animeisland.controller;

import com.fzcoder.opensource.animeisland.util.response.R;
import com.fzcoder.opensource.animeisland.util.IdGenerator;
import com.fzcoder.opensource.animeisland.entity.BangumiTag;
import com.fzcoder.opensource.animeisland.entity.BangumiTagItem;
import com.fzcoder.opensource.animeisland.service.IBangumiTagService;
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
 * @date 2022/7/25 15:27
 */
@RestController
@RequestMapping("/api/video")
public class BangumiTagController {
    private final IBangumiTagService bangumiTagService;

    @Autowired
    public BangumiTagController(@Qualifier("video_BangumiTagServiceImpl") IBangumiTagService bangumiTagService) {
        this.bangumiTagService = bangumiTagService;
    }

    @PostMapping("/bangumi_tag")
    public R addBangumiTag(@RequestBody BangumiTag tag) {
        tag.setId(IdGenerator.generateUUID());
        LocalDateTime currentDatetime = LocalDateTime.now();
        tag.setCreateTime(currentDatetime);
        tag.setLastModifyTime(currentDatetime);
        tag.setDbStatus(0);
        if (bangumiTagService.save(tag)) {
            return R.ok("", null);
        } else {
            return R.badRequest("", null);
        }
    }

    @GetMapping("/bangumi_tag/{id}")
    public R getBangumiTagById(@PathVariable("id") String id) {
        return R.ok(bangumiTagService.getById(id));
    }

    @GetMapping("/bangumi_tag")
    public R getBangumiTagList(@RequestParam Map<String, Object> params) {
        if (params.containsKey("bangumi_id")) {
            return R.ok(bangumiTagService.getTagListByBangumi(params.get("bangumi_id").toString()));
        } else {
            QueryWrapper<BangumiTag> qw = new QueryWrapper<>();
            qw.eq(params.containsKey("channel_id"), "channel_id", params.get("channel_id"));
            qw.eq("db_status", 0);
            return R.ok(bangumiTagService.list(qw));
        }
    }

    @GetMapping("/bangumi_tag/page")
    public R getBangumiTagPage(@RequestParam("page_num") long pageNum,
                                @RequestParam("page_size") long pageSize,
                                @RequestParam("channel_id") String channelId,
                                @RequestParam Map<String, Object> params) {
        QueryWrapper<BangumiTag> qw = new QueryWrapper<>();
        qw.eq("channel_id", channelId);
        qw.eq("db_status", 0);
        qw.like(params.containsKey("key"), "name", params.get("key"));
        qw.orderBy(true,false, "create_time");
        return R.ok(bangumiTagService.page(new Page<>(pageNum, pageSize), qw));
    }

    @PutMapping("/bangumi_tag")
    public R updateBangumiTag(@RequestBody BangumiTag tag) {
        UpdateWrapper<BangumiTag> uw = new UpdateWrapper<>();
        uw.eq("id", tag.getId());
        uw.set("last_modify_time", LocalDateTime.now());
        uw.set("name", tag.getName()); // bug fix (#2)
        uw.set("color_hex", tag.getColorHex());
        if (bangumiTagService.update(uw)) {
            return R.ok("修改成功", null);
        } else {
            return R.badRequest("修改失败", null);
        }
    }

    @DeleteMapping("/bangumi_tag/{id}")
    public R deleteBangumiTag(@PathVariable("id") String id) {
        UpdateWrapper<BangumiTag> uw = new UpdateWrapper<>();
        uw.set("db_status", -1);
        uw.set("last_modify_time", LocalDateTime.now());
        uw.eq("id", id);
        if (bangumiTagService.update(uw)) {
            // TODO 实现在'tb_video_bangumi_tag_item'表中删除对应tag_id=id的记录
            return R.ok("", null);
        } else {
            return R.badRequest("", null);
        }
    }

    @DeleteMapping("/bangumi_tag/batch")
    public R deleteBangumiTagBatch(@RequestParam("ids") List<String> ids) {
        for (String id : ids) {
            UpdateWrapper<BangumiTag> uw = new UpdateWrapper<>();
            uw.set("db_status", -1);
            uw.set("last_modify_time", LocalDateTime.now());
            uw.eq("id", id);
            if (!bangumiTagService.update(uw)) {
                return R.badRequest("删除失败", null);
            }
            // TODO 实现在'tb_video_bangumi_tag_item'表中删除对应tag_id=id的记录
        }
        return R.ok("删除成功", null);
    }

    @PostMapping("/bangumi_tag_item")
    public R addBangumiTagItem(@RequestBody BangumiTagItem tagItem) {
        tagItem.setId(IdGenerator.generateUUID());
        if (bangumiTagService.saveBangumiTagItem(tagItem)) {
            return R.ok("", null);
        } else {
            return R.badRequest("", null);
        }
    }

    @PostMapping("/bangumi_tag_item/batch")
    public R addBangumiTagItems(@RequestParam("bangumi_id") String bangumiId, @RequestBody List<String> tagIds) {
        if (bangumiTagService.saveBangumiTagItems(bangumiId, tagIds)) {
            return R.ok("添加成功", null);
        } else {
            return R.badRequest("添加失败", null);
        }
    }

    @PutMapping("/bangumi_tag_item/batch")
    public R updateBangumiTagItems(@RequestParam("bangumi_id") String bangumiId, @RequestBody List<String> tagIds) {
        if (bangumiTagService.saveBangumiTagItems(bangumiId, tagIds)) {
            return R.ok("修改成功", null);
        } else {
            return R.badRequest("修改失败", null);
        }
    }
}
