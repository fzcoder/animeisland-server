package com.fzcoder.opensource.animeisland.controller;

import com.fzcoder.opensource.animeisland.util.response.R;
import com.fzcoder.opensource.animeisland.util.IdGenerator;
import com.fzcoder.opensource.animeisland.entity.BangumiType;
import com.fzcoder.opensource.animeisland.service.IBangumiTypeService;
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
 * @date 2022/7/25 15:15
 */
@RestController
@RequestMapping("/api/video")
public class BangumiTypeController {
    private final IBangumiTypeService bangumiTypeService;

    @Autowired
    public BangumiTypeController(@Qualifier("video_BangumiTypeServiceImpl") IBangumiTypeService bangumiTypeService) {
        this.bangumiTypeService = bangumiTypeService;
    }

    @PostMapping("/bangumi_type")
    public R addBangumiType(@RequestBody BangumiType grade) {
        grade.setId(IdGenerator.generateUUID());
        LocalDateTime currentDatetime = LocalDateTime.now();
        grade.setCreateTime(currentDatetime);
        grade.setLastModifyTime(currentDatetime);
        grade.setDbStatus(0);
        if (bangumiTypeService.save(grade)) {
            return R.ok("", null);
        } else {
            return R.badRequest("", null);
        }
    }

    @GetMapping("/bangumi_type/{id}")
    public R getBangumiTypeById(@PathVariable("id") String id) {
        return R.ok(bangumiTypeService.getById(id));
    }

    @GetMapping("/bangumi_type")
    public R getBangumiTypeList(@RequestParam("channel_id") String channelId) {
        QueryWrapper<BangumiType> qw = new QueryWrapper<>();
        qw.eq("channel_id", channelId);
        qw.eq("db_status", 0);
        return R.ok(bangumiTypeService.list(qw));
    }

    @GetMapping("/bangumi_type/page")
    public R getBangumiTypePage(@RequestParam("page_num") long pageNum,
                                 @RequestParam("page_size") long pageSize,
                                 @RequestParam("channel_id") String channelId,
                                 @RequestParam Map<String, Object> params) {
        QueryWrapper<BangumiType> qw = new QueryWrapper<>();
        qw.eq("channel_id", channelId);
        qw.eq("db_status", 0);
        qw.like(params.containsKey("key"), "name", params.get("key"));
        qw.orderBy(true,false, "create_time");
        return R.ok(bangumiTypeService.page(new Page<>(pageNum, pageSize), qw));
    }

    @PutMapping("/bangumi_type")
    public R updateBangumiType(@RequestBody BangumiType type) {
        UpdateWrapper<BangumiType> uw = new UpdateWrapper<>();
        uw.eq("id", type.getId());
        uw.set("name", type.getName());
        uw.set("last_modify_time", LocalDateTime.now());
        if (bangumiTypeService.update(uw)) {
            return R.ok("修改成功", null);
        } else {
            return R.badRequest("修改失败", null);
        }
    }

    @DeleteMapping("/bangumi_type/{id}")
    public R deleteBangumiType(@PathVariable("id") String id) {
        UpdateWrapper<BangumiType> uw = new UpdateWrapper<>();
        uw.set("db_status", -1);
        uw.set("last_modify_time", LocalDateTime.now());
        uw.eq("id", id);
        if (bangumiTypeService.update(uw)) {
            return R.ok("删除成功", null);
        } else {
            return R.badRequest("删除失败", null);
        }
    }

    @DeleteMapping("/bangumi_type/batch")
    public R deleteBangumiTypeBatch(@RequestParam("ids") List<String> ids) {
        for (String id : ids) {
            UpdateWrapper<BangumiType> uw = new UpdateWrapper<>();
            uw.set("db_status", -1);
            uw.set("last_modify_time", LocalDateTime.now());
            uw.eq("id", id);
            if (!bangumiTypeService.update(uw)) {
                return R.badRequest("删除失败", null);
            }
        }
        return R.ok("删除成功", null);
    }
}
