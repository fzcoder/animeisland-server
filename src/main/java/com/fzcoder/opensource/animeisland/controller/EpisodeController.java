package com.fzcoder.opensource.animeisland.controller;

import com.fzcoder.opensource.animeisland.util.Strings;
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

    private QueryWrapper<Episode> getListQueryWrapper(String key, String bangumiId) {
        QueryWrapper<Episode> wrapper = new QueryWrapper<>();
        wrapper.like(Strings.isEqualEmpty(key), "title", key);
        wrapper.eq("bangumi_id", bangumiId);
        wrapper.orderByAsc("order_in_bangumi");
        return wrapper;
    }

    @Autowired
    public EpisodeController(@Qualifier("CloudXVideo_EpisodeServiceImpl") IEpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @PostMapping("/episode")
    public R save(@RequestBody Episode item) {
        if (episodeService.saveEpisode(item)) {
            return R.ok("添加成功!", null);
        } else {
            return R.badRequest("添加失败!", null);
        }
    }
    @PostMapping("/episode/batch")
    public R saveBatch(@RequestBody List<Episode> items) {
        if (episodeService.saveEpisodes(items)) {
            return R.ok("添加成功!", null);
        } else {
            return R.badRequest("添加失败!", null);
        }
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
    public R getListByBangumiId(@RequestParam("bangumi_id") String bangumiId,
                                @RequestParam(value = "key", required = false, defaultValue = "")String key) {
        return R.ok(episodeService.list(getListQueryWrapper(key, bangumiId)));
    }

    @GetMapping("/episode/page")
    public R getPage(@RequestParam("page_num") long pageNum,
                     @RequestParam("page_size") long pageSize,
                     @RequestParam("bangumi_id") String bangumiId,
                     @RequestParam(value = "key", required = false, defaultValue = "")String key) {
        return R.ok(episodeService.page(
                new Page<>(pageNum, pageSize),
                getListQueryWrapper(key, bangumiId)
        ));
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

    @PutMapping("/episode/order")
    public R updateOrderInBangumi(@RequestBody List<Episode> episodes) {
        if (episodeService.changeOrderInBangumi(episodes)) {
            return R.ok("修改成功!", null);
        } else {
            return R.badRequest("修改失败!", null);
        }
    }

    @DeleteMapping("/episode/{id}")
    public R delete(@PathVariable("id") String id) {
        if (episodeService.deleteEpisodeById(id)) {
            return R.ok("删除成功!", null);
        } else {
            return R.badRequest("删除失败!", null);
        }
    }

    @DeleteMapping("/episode/batch")
    public R deleteBatch(@RequestParam("ids") List<String> ids) {
        if (episodeService.deleteEpisodesByIds(ids)) {
            return R.ok("删除成功!", null);
        } else {
            return R.badRequest("删除失败!", null);
        }
    }
}
