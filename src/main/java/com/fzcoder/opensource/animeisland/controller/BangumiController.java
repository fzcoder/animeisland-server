package com.fzcoder.opensource.animeisland.controller;

import com.fzcoder.opensource.animeisland.util.response.R;
import com.fzcoder.opensource.animeisland.entity.Bangumi;
import com.fzcoder.opensource.animeisland.service.IBangumiService;
import com.fzcoder.opensource.animeisland.util.IdGenerator;
import com.fzcoder.opensource.animeisland.vo.BangumiVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/video")
public class BangumiController {
    private final IBangumiService bangumiService;

    @Autowired
    public BangumiController(@Qualifier("CloudXBangumi_BangumiServiceImpl") IBangumiService bangumiService) {
        this.bangumiService = bangumiService;
    }

    @PostMapping("/bangumi")
    public R save(@RequestBody Bangumi bangumi) {
        LocalDateTime currentDatetime = LocalDateTime.now();
        String id = IdGenerator.generateBangumiId();
        bangumi.setId(id);
        bangumi.setCreateTime(currentDatetime);
        bangumi.setLastModifyTime(currentDatetime);
        bangumi.setDbStatus(0);
        String[] dates = bangumi.getReleaseDate().split("-");
        if (dates.length >= 2) {
            bangumi.setReleaseYear(dates[0]);
            bangumi.setReleaseMonth(dates[1]);
        }
        if (bangumiService.save(bangumi)) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", id);
            return R.ok("添加成功!", data);
        } else {
            return R.badRequest("添加失败", null);
        }
    }

    @GetMapping("/bangumi/{id}")
    public R getForm(@PathVariable("id") String id) {
        return R.ok(bangumiService.getById(id));
    }

    @GetMapping("/bangumi/view/{id}")
    public R getView(@PathVariable("id") String id) {
        return R.ok(bangumiService.getVoById(id));
    }

    @GetMapping("/bangumi/page")
    public R getPage(@RequestParam("uid") String uid,
                     @RequestParam("key") String key,
                     @RequestParam("page_num") long pageNum,
                     @RequestParam("page_size") long pageSize,
                     @RequestParam Map<String, Object> params) {
        QueryWrapper<BangumiVO> qw = new QueryWrapper<>();
        // qw.eq("uid", uid);
        qw.eq("db_status", 0);
        qw.eq(params.containsKey("channel_id") && !"all".equals(params.get("channel_id")),
                "channel_id",
                params.get("channel_id"));
        qw.eq(params.containsKey("type_id") && !"all".equals(params.get("type_id")),
                "type_id",
                params.get("type_id"));
        qw.eq(params.containsKey("grade_id") && !"all".equals(params.get("grade_id")),
                "grade_id",
                params.get("grade_id"));
        qw.eq(params.containsKey("release_year") && !"all".equals(params.get("release_year")),
                "release_year",
                params.get("release_year"));
        qw.eq(params.containsKey("release_month") && !"all".equals(params.get("release_month")),
                "release_month",
                params.get("release_month"));
        qw.eq(params.containsKey("status") && !"-1".equals(params.get("status")),
                "status",
                params.get("status"));
        qw.like("title", key);
        qw.like("description", key);
        qw.orderBy(true, false, "create_time");
        return R.ok(bangumiService.voPage(new Page<>(pageNum, pageSize), qw));
    }

    @PutMapping("/bangumi")
    public R update(@RequestBody Bangumi bangumi) {
        String[] dates = bangumi.getReleaseDate().split("-");
        if (dates.length >= 2) {
            bangumi.setReleaseYear(dates[0]);
            bangumi.setReleaseMonth(dates[1]);
        }
        bangumi.setLastModifyTime(LocalDateTime.now());
        if (bangumiService.updateById(bangumi)) {
            return R.ok("修改成功!", null);
        } else {
            return R.badRequest("修改失败!", null);
        }
    }

    @DeleteMapping("/bangumi/{id}")
    public R delete(@PathVariable("id") String id) {
        UpdateWrapper<Bangumi> uw = new UpdateWrapper<>();
        uw.eq("id", id);
        uw.set("db_status", -1);
        uw.set("last_modify_time", LocalDateTime.now());
        if (bangumiService.update(null, uw)) {
            return R.ok("删除成功!", null);
        } else {
            return R.badRequest("删除失败!", null);
        }
    }

    @DeleteMapping("/bangumi/batch")
    public R deleteBangumiGradeBatch(@RequestParam("ids") List<String> ids) {
        for (String id : ids) {
            UpdateWrapper<Bangumi> uw = new UpdateWrapper<>();
            uw.set("db_status", -1);
            uw.set("last_modify_time", LocalDateTime.now());
            uw.eq("id", id);
            if (!bangumiService.update(uw)) {
                return R.badRequest("删除失败", null);
            }
        }
        return R.ok("删除成功", null);
    }
}
