package com.fzcoder.opensource.animeisland.controller;

import com.fzcoder.opensource.animeisland.util.response.R;
import com.fzcoder.opensource.animeisland.entity.Episode;
import com.fzcoder.opensource.animeisland.service.IEpisodeService;
import com.fzcoder.opensource.animeisland.util.IdGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/video")
public class EpisodeController {
    private final IEpisodeService episodeService;

    @Autowired
    public EpisodeController(@Qualifier("CloudXVideo_EpisodeServiceImpl") IEpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @PostMapping("/episode")
    public R save(@RequestBody Episode item) {
        LocalDateTime currentDatetime = LocalDateTime.now();
        item.setId(IdGenerator.generateEpisodeId());
        item.setCreateTime(currentDatetime);
        item.setLastModifyTime(currentDatetime);
        if (episodeService.save(item)) {
            return R.ok("添加成功!", null);
        } else {
            return R.badRequest("添加失败!", null);
        }
    }

    @PostMapping("/episode/batch")
    public R saveBatch(@RequestBody List<Episode> items) {
        if (items.size() < 1) {
            return R.badRequest("上传的表单不能为空!", null);
        }
        // 查询已添加的剧集数量
        String bangumiId = items.get(0).getBangumiId();
        QueryWrapper<Episode> qw = new QueryWrapper<>();
        qw.eq("bangumi_id", bangumiId);
        int offset = episodeService.count(qw);
        for (Episode item : items) {
            LocalDateTime currentDatetime = LocalDateTime.now();
            item.setId(IdGenerator.generateEpisodeId());
            item.setCreateTime(currentDatetime);
            item.setLastModifyTime(currentDatetime);
            item.setOrderInBangumi(item.getOrderInBangumi() + offset);
        }
        if (episodeService.saveBatch(items)) {
            return R.ok("添加成功!", null);
        } else {
            return R.badRequest("添加失败!", null);
        }
    }

    @PostMapping("/episode/batch/upload")
    public R parseJsonForms(HttpServletRequest request) {
        return R.ok(episodeService.parseJsonForms(request));
    }

    @GetMapping("/episode/form/{id}")
    public R getForm(@PathVariable("id") String id) {
        return R.ok(episodeService.getById(id));
    }

    @GetMapping("/episode/view/{id}")
    public R getView(@PathVariable("id") String id) {
        return R.ok(episodeService.getById(id));
    }

    @GetMapping("/episode/list/form")
    public R getListByBangumiId(@RequestParam("bangumi_id") String bangumiId) {
        QueryWrapper<Episode> qw = new QueryWrapper<>();
        qw.eq("bangumi_id", bangumiId);
        qw.eq("status", 0);
        qw.orderBy(true, true, "order_in_bangumi");
        return R.ok(episodeService.list(qw));
    }

    @GetMapping("/episode/page")
    public R getPage(@RequestParam("page_num") long pageNum,
                     @RequestParam("page_size") long pageSize,
                     @RequestParam Map<String, Object> params) {
        QueryWrapper<Episode> qw = new QueryWrapper<>();
        qw.eq("status", 0);
        qw.eq(params.containsKey("bangumi_id"), "bangumi_id", params.get("bangumi_id"));
        qw.like(params.containsKey("key"), "title", params.get("key"));
        qw.orderBy(true, true, "order_in_bangumi");
        return R.ok(episodeService.page(new Page<>(pageNum, pageSize), qw));
    }

    @PutMapping("/episode")
    public R update(@RequestBody Episode item) {
        item.setLastModifyTime(LocalDateTime.now());
        if (episodeService.updateById(item)) {
            return R.ok("修改成功!", null);
        } else {
            return R.badRequest("修改失败!", null);
        }
    }

    @PutMapping("/episode/batch")
    public R updateBatch(@RequestBody List<Episode> episodes) {
        for (Episode e : episodes) {
            e.setLastModifyTime(LocalDateTime.now());
        }
        if (episodeService.updateBatchById(episodes)) {
            return R.ok("修改成功!", null);
        } else {
            return R.badRequest("修改失败!", null);
        }
    }

    @DeleteMapping("/episode/{id}")
    public R delete(@PathVariable("id") String id) {
        UpdateWrapper<Episode> uw = new UpdateWrapper<>();
        uw.eq("id", id);
        uw.set("last_modify_time", LocalDateTime.now());
        uw.set("status", -1);
        if (episodeService.update(null, uw)) {
            return R.ok("删除成功!", null);
        } else {
            return R.badRequest("删除失败!", null);
        }
    }

    @DeleteMapping("/episode/batch")
    public R deleteBatch(@RequestParam("ids") List<String> ids) {
        for (String id : ids) {
            UpdateWrapper<Episode> uw = new UpdateWrapper<>();
            uw.set("status", -1);
            uw.set("last_modify_time", LocalDateTime.now());
            uw.eq("id", id);
            if (!episodeService.update(uw)) {
                return R.badRequest("删除失败", null);
            }
        }
        return R.ok("删除成功", null);
    }
}
