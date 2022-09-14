package com.fzcoder.opensource.animeisland.controller;

import com.fzcoder.opensource.animeisland.util.Strings;
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

    private <T> QueryWrapper<T> getListQueryWrapper(String uid,
                                                    String keyword,
                                                    String channelId,
                                                    String typeId,
                                                    String gradeId,
                                                    String releaseYear,
                                                    String releaseMonth,
                                                    Integer status) {
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq("uid", uid);
        qw.eq("db_status", 0);
        qw.eq(Strings.isNotEqualAll(channelId), "channel_id", channelId);
        qw.eq(Strings.isNotEqualAll(typeId), "type_id", typeId);
        qw.eq(Strings.isNotEqualAll(gradeId), "grade_id", gradeId);
        qw.eq(Strings.isNotEqualAll(releaseYear), "release_year", releaseYear);
        qw.eq(Strings.isNotEqualAll(releaseMonth), "release_month", releaseMonth);
        qw.eq(!"-1".equals(Integer.toString(status)), "status", status);
        qw.and(w -> w.like("title", keyword).or().like("description", keyword));
        qw.orderByDesc("release_date");
        return qw;
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

    @GetMapping("/bangumi")
    public R getList(@RequestParam("uid") String uid,
                     @RequestParam(value = "key", required = false, defaultValue = "") String keyword,
                     @RequestParam(value = "channel_id", required = false, defaultValue = "all") String channelId,
                     @RequestParam(value = "type_id", required = false, defaultValue = "all") String typeId,
                     @RequestParam(value = "grade_id", required = false, defaultValue = "all") String gradeId,
                     @RequestParam(value = "release_year", required = false, defaultValue = "all") String releaseYear,
                     @RequestParam(value = "release_month", required = false, defaultValue = "all") String releaseMonth,
                     @RequestParam(value = "status", required = false, defaultValue = "-1") Integer status) {
        return R.ok(bangumiService.list(getListQueryWrapper(uid, keyword, channelId, typeId, gradeId, releaseYear, releaseMonth, status)));
    }

    @GetMapping("/bangumi/view/{id}")
    public R getView(@PathVariable("id") String id) {
        return R.ok(bangumiService.getVoById(id));
    }

    @GetMapping("/bangumi/page")
    public R getPage(@RequestParam("uid") String uid,
                     @RequestParam("page_num") long pageNum,
                     @RequestParam("page_size") long pageSize,
                     @RequestParam(value = "key", required = false, defaultValue = "") String keyword,
                     @RequestParam(value = "channel_id", required = false, defaultValue = "all") String channelId,
                     @RequestParam(value = "type_id", required = false, defaultValue = "all") String typeId,
                     @RequestParam(value = "grade_id", required = false, defaultValue = "all") String gradeId,
                     @RequestParam(value = "release_year", required = false, defaultValue = "all") String releaseYear,
                     @RequestParam(value = "release_month", required = false, defaultValue = "all") String releaseMonth,
                     @RequestParam(value = "status", required = false, defaultValue = "-1") Integer status) {
        return R.ok(bangumiService.voPage(new Page<>(pageNum, pageSize),
                getListQueryWrapper(uid, keyword, channelId, typeId, gradeId, releaseYear, releaseMonth, status)));
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
