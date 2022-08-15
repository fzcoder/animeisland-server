package com.fzcoder.opensource.animeisland.controller;

import com.fzcoder.opensource.animeisland.util.response.R;
import com.fzcoder.opensource.animeisland.util.IdGenerator;
import com.fzcoder.opensource.animeisland.entity.BangumiGrade;
import com.fzcoder.opensource.animeisland.service.IBangumiGradeService;
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
 * @date 2022/7/25 15:03
 */
@RestController
@RequestMapping("/api/video")
public class BangumiGradeController {
    private final IBangumiGradeService bangumiGradeService;

    @Autowired
    public BangumiGradeController(@Qualifier("video_BangumiGradeServiceImpl") IBangumiGradeService bangumiGradeService) {
        this.bangumiGradeService = bangumiGradeService;
    }

    @PostMapping("/bangumi_grade")
    public R addBangumiGrade(@RequestBody BangumiGrade grade) {
        grade.setId(IdGenerator.generateUUID());
        LocalDateTime currentDatetime = LocalDateTime.now();
        grade.setCreateTime(currentDatetime);
        grade.setLastModifyTime(currentDatetime);
        grade.setDbStatus(0);
        if (bangumiGradeService.save(grade)) {
            return R.ok("", null);
        } else {
            return R.badRequest("", null);
        }
    }

    @GetMapping("/bangumi_grade/{id}")
    public R getBangumiGradeById(@PathVariable("id") String id) {
        return R.ok(bangumiGradeService.getById(id));
    }

    @GetMapping("/bangumi_grade")
    public R getBangumiGradeList(@RequestParam("channel_id") String channelId) {
        QueryWrapper<BangumiGrade> qw = new QueryWrapper<>();
        qw.eq("channel_id", channelId);
        qw.eq("db_status", 0);
        return R.ok(bangumiGradeService.list(qw));
    }

    @GetMapping("/bangumi_grade/page")
    public R getBangumiGradePage(@RequestParam("page_num") long pageNum,
                                 @RequestParam("page_size") long pageSize,
                                 @RequestParam("channel_id") String channelId,
                                 @RequestParam Map<String, Object> params) {
        QueryWrapper<BangumiGrade> qw = new QueryWrapper<>();
        qw.eq("channel_id", channelId);
        qw.eq("db_status", 0);
        qw.like(params.containsKey("key"), "name", params.get("key"));
        qw.orderBy(true,false, "create_time");
        return R.ok(bangumiGradeService.page(new Page<>(pageNum, pageSize), qw));
    }

    @PutMapping("/bangumi_grade")
    public R updateBangumiGrade(@RequestBody BangumiGrade grade) {
        UpdateWrapper<BangumiGrade> uw = new UpdateWrapper<>();
        uw.eq("id", grade.getId());
        uw.set("name", grade.getName());
        uw.set("icon_url", grade.getIconUrl());
        uw.set("last_modify_time", LocalDateTime.now());
        if (bangumiGradeService.update(uw)) {
            return R.ok("修改成功", null);
        } else {
            return R.badRequest("修改失败", null);
        }
    }

    @DeleteMapping("/bangumi_grade/{id}")
    public R deleteBangumiGrade(@PathVariable("id") String id) {
        UpdateWrapper<BangumiGrade> uw = new UpdateWrapper<>();
        uw.set("db_status", -1);
        uw.set("last_modify_time", LocalDateTime.now());
        uw.eq("id", id);
        if (bangumiGradeService.update(uw)) {
            return R.ok("删除成功", null);
        } else {
            return R.badRequest("删除失败", null);
        }
    }

    @DeleteMapping("/bangumi_grade/batch")
    public R deleteBangumiGradeBatch(@RequestParam("ids") List<String> ids) {
        for (String id : ids) {
            UpdateWrapper<BangumiGrade> uw = new UpdateWrapper<>();
            uw.set("db_status", -1);
            uw.set("last_modify_time", LocalDateTime.now());
            uw.eq("id", id);
            if (!bangumiGradeService.update(uw)) {
                return R.badRequest("删除失败", null);
            }
        }
        return R.ok("删除成功", null);
    }
}
