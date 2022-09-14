package com.fzcoder.opensource.animeisland.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzcoder.opensource.animeisland.entity.Series;
import com.fzcoder.opensource.animeisland.service.ISeriesService;
import com.fzcoder.opensource.animeisland.util.IdGenerator;
import com.fzcoder.opensource.animeisland.util.response.R;
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
public class SeriesController {
    private final ISeriesService SeriesService;

    @Autowired
    public SeriesController(@Qualifier("video_SeriesServiceImpl") ISeriesService SeriesService) {
        this.SeriesService = SeriesService;
    }

    @PostMapping("/series")
    public R addSeries(@RequestBody Series form) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        form.setId(IdGenerator.generateUUID());
        form.setCreateTime(currentDateTime);
        form.setLastModifyTime(currentDateTime);
        form.setDbStatus(0);
        if (SeriesService.save(form)) {
            return R.ok("Successfully Added", null);
        } else {
            return R.badRequest("Add Failed", null);
        }
    }

    @GetMapping("/series/form/{id}")
    public R getSeriesFormById(@PathVariable("id") String id) {
        return R.ok(SeriesService.getById(id));
    }

    @GetMapping("/series")
    public R getSeriesList(@RequestParam Map<String, Object> params) {
        QueryWrapper<Series> qw = new QueryWrapper<>();
        // qw.eq(params.containsKey("uid"), "uid", params.get("uid"));
        qw.eq("db_status", 0);
        qw.like(params.containsKey("key"), "name", params.get("key"));
        qw.orderBy(true, true, "name");
        return R.ok(SeriesService.list(qw));
    }

    @GetMapping("/series/page")
    public R getSeriesPage(@RequestParam("page_num") long pageNum,
                                @RequestParam("page_size") long pageSize,
                                @RequestParam Map<String, Object> params) {
        QueryWrapper<Series> qw = new QueryWrapper<>();
        qw.eq(params.containsKey("uid"), "uid", params.get("uid"));
        qw.eq("db_status", 0);
        qw.like(params.containsKey("key"), "name", params.get("key"));
        qw.orderBy(true, false, "create_time");
        return R.ok(SeriesService.page(new Page<>(pageNum, pageSize), qw));
    }

    @PutMapping("/series")
    public R updateSeries(@RequestBody Series form) {
        UpdateWrapper<Series> uw = new UpdateWrapper<>();
        uw.eq("id", form.getId());
        uw.set("name", form.getName());
        uw.set("description", form.getDescription());
        uw.set("last_modify_time", LocalDateTime.now());
        if (SeriesService.update(uw)) {
            return R.ok("Successfully Updated", null);
        } else {
            return R.badRequest("Update Failed", null);
        }
    }

    @DeleteMapping("/series/{id}")
    public R deleteSeries(@PathVariable("id") String id) {
        UpdateWrapper<Series> uw = new UpdateWrapper<>();
        uw.eq("id", id);
        uw.set("db_status", -1);
        uw.set("last_modify_time", LocalDateTime.now());
        if (SeriesService.update(uw)) {
            return R.ok("Successfully Delete", null);
        } else {
            return R.badRequest("Delete Failed", null);
        }
    }

    @DeleteMapping("/series/batch")
    public R deleteSeriesByIds(@RequestParam("ids") List<String> ids) {
        for (String id : ids) {
            UpdateWrapper<Series> uw = new UpdateWrapper<>();
            uw.eq("id", id);
            uw.set("db_status", -1);
            uw.set("last_modify_time", LocalDateTime.now());
            if (!SeriesService.update(uw)) {
                return R.badRequest("Delete Failed", null);
            }
        }
        return R.ok("Successfully Delete", null);
    }
}
