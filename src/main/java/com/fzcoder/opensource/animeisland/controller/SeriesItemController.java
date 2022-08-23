package com.fzcoder.opensource.animeisland.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzcoder.opensource.animeisland.entity.SeriesItem;
import com.fzcoder.opensource.animeisland.service.ISeriesItemService;
import com.fzcoder.opensource.animeisland.util.Strings;
import com.fzcoder.opensource.animeisland.util.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/video")
public class SeriesItemController {
    private final ISeriesItemService seriesItemService;

    private QueryWrapper<SeriesItem> getRequestWrapper(String key, String seriesId, String bangumiId) {
        QueryWrapper<SeriesItem> wrapper = new QueryWrapper<>();
        wrapper.like(Strings.isNotEqualEmpty(key), "name", key);
        wrapper.eq(Strings.isNotEqualAll(seriesId), "series_id", seriesId);
        wrapper.eq(Strings.isNotEqualAll(bangumiId), "bangumi_id", bangumiId);
        wrapper.orderByAsc("order_in_series");
        return wrapper;
    }

    @Autowired
    public SeriesItemController(@Qualifier("video_SeriesItemServiceImpl") ISeriesItemService seriesItemService) {
        this.seriesItemService = seriesItemService;
    }

    @PostMapping("/series_item")
    public R save(@RequestBody SeriesItem item) {
        if (seriesItemService.saveSeriesItem(item)) {
            return R.ok("OK", null);
        } else {
            return R.badRequest("Save Failed", null);
        }
    }

    @GetMapping("/series_item/{id}")
    public R getForm(@PathVariable("id") String id) {
        return R.ok(seriesItemService.getById(id));
    }

    @GetMapping("/series_item/view/{id}")
    public R getView(@PathVariable("id") String id) {
        return R.ok(seriesItemService.getById(id));
    }

    @GetMapping("/series_item")
    public R getList(@RequestParam(value = "key", required = false, defaultValue = Strings.Val_EMPTY) String key,
                     @RequestParam(value = "series_id", required = false, defaultValue = Strings.Val_ALL) String seriesId,
                     @RequestParam(value = "bangumi_id", required = false, defaultValue = Strings.Val_ALL) String bangumiId,
                     @RequestParam(value = "by", required = false, defaultValue = "params") String by) {
        if ("bangumi_id".equals(by)) {
            if (Strings.isEqualAll(bangumiId)) {
                return R.badRequest("Missing parameter 'bangumi_id'");
            }
            return R.ok(seriesItemService.getListByBangumiId(bangumiId));
        } else {
            return R.ok(seriesItemService.list(
                    getRequestWrapper(key, seriesId, bangumiId)
            ));
        }
    }

    @GetMapping("/series_item/page")
    public R getPage(@RequestParam("page_num") long pageNum,
                     @RequestParam("page_size") long pageSize,
                     @RequestParam(value = "key", required = false, defaultValue = Strings.Val_EMPTY) String key,
                     @RequestParam(value = "series_id", required = false, defaultValue = Strings.Val_ALL) String seriesId,
                     @RequestParam(value = "bangumi_id", required = false, defaultValue = Strings.Val_ALL) String bangumiId) {
        return R.ok(seriesItemService.page(
                new Page<>(pageNum, pageSize),
                getRequestWrapper(key, seriesId, bangumiId)
        ));
    }

    @PutMapping("/series_item")
    public R update(@RequestBody SeriesItem item) {
        item.setLastModifyTime(LocalDateTime.now());
        if (seriesItemService.updateById(item)) {
            return R.ok("OK", null);
        } else {
            return R.badRequest("Failed", null);
        }
    }

    @PutMapping("/series_item/order")
    public R updateOrderInSeries(@RequestBody List<SeriesItem> seriesItems) {
        if (seriesItemService.changeOrderInSeries(seriesItems)) {
            return R.ok("OK", null);
        } else {
            return R.badRequest("Failed", null);
        }
    }

    @DeleteMapping("/series_item/{id}")
    public R delete(@PathVariable("id") String id) {
        if (seriesItemService.deleteSeriesItemById(id)) {
            return R.ok("OK", null);
        } else {
            return R.badRequest("Failed", null);
        }
    }

    @DeleteMapping("/series_item/batch")
    public R deleteBatch(@RequestParam("ids") List<String> ids) {
        if (seriesItemService.deleteSeriesItemsByIds(ids)) {
            return R.ok("OK", null);
        } else {
            return R.badRequest("Failed", null);
        }
    }
}
