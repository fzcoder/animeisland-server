package com.animeisland.controller;

import com.animeisland.bean.PageQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.animeisland.bean.BaseResponse;
import com.animeisland.entity.Video;
import com.animeisland.service.VideoService;
import com.animeisland.utils.IdGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = "视频模块接口")
@RequestMapping("/api")
@RestController
@PropertySource(value = {"classpath:application.properties"}, encoding = "utf-8")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Value("${resource.server.url}")
    private String baseURL;

    /**
     * 安全的id生成器
     * @param entity
     * @return
     */
    private Boolean safeIdGenerator(Video entity) {
        String id = IdGenerator.videoIdGenerator(entity.getMediaId(), entity.getOrderInMedia());
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(true, "id", id);
        if (videoService.count(queryWrapper) != 0) {
            return false;
        } else {
            entity.setId(id);
            return true;
        }
    }

    @ApiOperation(value = "添加视频")
    @PostMapping("/admin/video")
    public Object addVideo(@RequestBody Video entity){
        if (!safeIdGenerator(entity)) {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "视频已存在！");
        }
        if (videoService.save(entity)){
            return new BaseResponse(HttpServletResponse.SC_OK, "视频添加成功!");
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "视频添加失败!");
        }
    }

    @ApiOperation(value = "批量添加视频")
    @PostMapping("/admin/video/batch")
    public Object addVideoList(@RequestBody List<Video> entityList) {
        if (entityList.isEmpty()) {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "上传视频不能为空!");
        } else {
            // 每次添加的数量
            final int batchSize = 1;
            for (Video item : entityList) {
                if (!safeIdGenerator(item)) {
                    return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "视频已存在！");
                }
            }
            // 批量添加数据
            if (videoService.saveBatch(entityList, batchSize)) {
                return new BaseResponse(HttpServletResponse.SC_OK, "视频添加成功!");
            } else {
                return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "视频添加失败!");
            }
        }
    }

    @ApiOperation(value = "获取视频")
    @GetMapping("/video/{id}")
    public Object getVideoMap(@PathVariable("id") String id) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        String[] sqlSelect = {"id", "title", "media_id", "quality", "part_number", "part_unit"};
        queryWrapper.eq(true, "id", id);
        queryWrapper.select(sqlSelect);
        queryWrapper.orderBy(true, true, "order_in_media");
        Map<String, Object> video = videoService.getMap(queryWrapper);
        String quality = video.get("quality").toString();
        video.replace("quality", quality.split(","));
        return new BaseResponse(video);
    }

    @ApiOperation(value = "获取视频（管理系统）")
    @GetMapping("/admin/video/{id}")
    public Object getVideo(@PathVariable("id") String id) {
        return new BaseResponse(videoService.getById(id));
    }

    @ApiOperation(value = "根据id获取视频列表（管理系统）")
    @GetMapping("/admin/video")
    public Object getByIds(@RequestParam Map<Integer, String> params) {
        return new BaseResponse(videoService.listByIds(new ArrayList<>(params.values())));
    }

    @GetMapping("/admin/video/download/info")
    public void downloadVideoInfo(@RequestParam Map<String, Object> params, HttpServletResponse response) {
        String fileName = "video.json";
        File file = new File(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            FileOutputStream os = new FileOutputStream(file, false);
            OutputStreamWriter writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            queryWrapper.allEq(true, params, false);
            queryWrapper.orderBy(true, true, "order_in_media");
            List<Video> list = videoService.list(queryWrapper);
            writer.append(objectMapper.writeValueAsString(list));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置下载文件格式
        response.setContentType("application/octet-stream");
        // 设置文件名
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try {
            log.info("正在进行文件流传输...");
            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            bis.close();
            log.info("文件传输完成！");
        } catch (Exception e) {
            log.error("文件传输出现异常！");
            e.printStackTrace();
        }
        // 删除文件
        if (!file.delete()) {
            log.warn("文件删除失败！");
        }
    }

    @ApiOperation(value = "获取视频列表")
    @GetMapping("/video")
    public Object getList(@RequestParam Map<String, Object> params) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(true, params, false);
        queryWrapper.orderByAsc(true, "order_in_media");
        String[] sqlSelect = {"id", "title", "quality", "part_number", "part_unit"};
        queryWrapper.select(sqlSelect);
        return new BaseResponse(videoService.listMaps(queryWrapper));
    }
    @ApiOperation(value = "获取视频列表（分页）")
    @PostMapping("/video")
    public Object getVideoPage(@RequestParam Map<String, Object> params, @RequestBody PageQuery pageQuery) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(true, params, false);
        queryWrapper.like(true, "title", pageQuery.getKey());
        queryWrapper.orderBy(true, !pageQuery.isReverse(), pageQuery.getOrderBy());
        String[] sqlSelect = {"id", "title", "quality", "part_number", "part_unit"};
        queryWrapper.select(sqlSelect);
        IPage<Map<String, Object>> page = videoService.pageMaps(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize()), queryWrapper);
        for (Map<String, Object> item : page.getRecords()) {
            String tags = item.get("quality").toString();
            item.replace("quality", tags.split(","));
        }
        return new BaseResponse(page);
    }

    @ApiOperation(value = "修改视频信息")
    @PutMapping("/admin/video")
    public Object updateVideo(@RequestBody Video video){
        if (video.getId().equals(IdGenerator.videoIdGenerator(video.getMediaId(), video.getOrderInMedia()))) {
            if (videoService.updateById(video)) {
                return new BaseResponse(HttpServletResponse.SC_OK, "修改成功！");
            } else {
                return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "修改失败！");
            }
        } else {
            if (!safeIdGenerator(video)) {
                return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "与已有视频产生冲突！");
            } else {
                if (videoService.save(video)) {
                    return new BaseResponse(HttpServletResponse.SC_OK, "修改成功！");
                } else {
                    return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "修改失败！");
                }
            }
        }
    }

    @ApiOperation(value = "批量修改视频信息")
    @PutMapping("/admin/video/batch")
    public Object updateBatch(@RequestBody List<Video> list) {
       if (videoService.updateBatchById(list)) {
           return new BaseResponse(HttpServletResponse.SC_OK, "批量修改成功！");
       } else {
           return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "批量修改失败！");
       }
    }

    @ApiOperation(value = "删除视频信息")
    @DeleteMapping("/admin/video/{id}")
    public Object deleteVideo(@PathVariable("id") String id){
        if(videoService.removeById(id)){
            return new BaseResponse(HttpServletResponse.SC_OK, "删除成功！");
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "删除失败！");
        }
    }

    @ApiOperation(value = "批量删除视频信息")
    @DeleteMapping("/admin/video")
    public Object deleteVideo(@RequestParam Map<Integer, String> params) {
        if (videoService.removeByIds(new ArrayList<>(params.values()))) {
            return new BaseResponse(HttpServletResponse.SC_OK, "批量删除成功！");
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "批量删除失败！");
        }
    }
}
