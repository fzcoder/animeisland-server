package com.animeisland.controller;

import com.animeisland.bean.PageQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.animeisland.bean.BaseResponse;
import com.animeisland.entity.Media;
import com.animeisland.entity.Series;
import com.animeisland.service.MediaService;
import com.animeisland.service.SeriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "系列模块接口")
@RequestMapping("/api")
@RestController
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private MediaService mediaService;

    @ApiOperation(value = "创建系列")
    @PostMapping("/admin/series")
    public Object addSeries(@RequestBody Series entity){
        if(seriesService.save(entity)){
            return new BaseResponse(HttpServletResponse.SC_OK, "系列创建成功！");
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "系列创建失败！");
        }
    }

    @ApiOperation(value = "根据id获取系列")
    @GetMapping("/series/{id}")
    public Object getSeries(@PathVariable("id") Integer id) {
        return new BaseResponse(seriesService.getById(id));
    }

    @ApiOperation(value = "获取系列列表")
    @GetMapping("/series")
    public Object getSeriesList(Map<String, Object> params) {
        QueryWrapper<Series> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(true, params, false);
        return new BaseResponse(seriesService.listMaps(queryWrapper));
    }

    @ApiOperation(value = "获取系列列表(分页)")
    @PostMapping("/series")
    public Object getSeriesList(@RequestParam Map<String, Object> params, @RequestBody PageQuery pageQuery){
        QueryWrapper<Series> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(true, params, false);
        queryWrapper.like(true, "name_zh", pageQuery.getKey());
        queryWrapper.orderBy(true, !pageQuery.isReverse(), pageQuery.getOrderBy());
        IPage<Map<String, Object>> page = seriesService.pageMaps(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()), queryWrapper);
        return new BaseResponse(page);
    }

    @ApiOperation(value = "修改系列")
    @PutMapping("/admin/series")
    public Object updateSeries(@RequestBody Series series) {
        if (seriesService.updateById(series)) {
            return new BaseResponse(HttpServletResponse.SC_OK, "修改成功！");
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "修改失败！");
        }
    }

    private Boolean updateSeriesInMedia(Serializable id) {
        QueryWrapper<Media> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("series_id", id);
        List<Media> list = mediaService.list(queryWrapper);
        // 注意：mybatis-plus的批量操作不能传入空集合
        if (list.isEmpty()) {
            return true;
        } else {
            for (Media item : list) {
                item.setHasSeries(false);
                item.setSeriesId(0);
            }
            return mediaService.updateBatchById(list);
        }
    }

    @ApiOperation(value = "删除系列")
    @DeleteMapping("/admin/series/{id}")
    public Object deleteSeries(@PathVariable("id") Integer id) {
        if (seriesService.removeById(id) && updateSeriesInMedia(id)) {
            return new BaseResponse(HttpServletResponse.SC_OK, "删除成功！");
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "删除失败！");
        }
    }

    @ApiOperation(value = "批量删除系列")
    @DeleteMapping("/admin/series")
    public Object deleteSeries(@RequestParam Map<Integer, Serializable> params) {
        if (seriesService.removeByIds(new ArrayList<>(params.values()))) {
            for (Serializable id : params.values()) {
                if (!updateSeriesInMedia(id)) {
                    return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "批量删除异常！");
                }
            }
            return new BaseResponse(HttpServletResponse.SC_OK, "批量删除成功！");
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "批量删除失败！");
        }
    }
}
