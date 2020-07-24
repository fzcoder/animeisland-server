package com.animeisland.controller;

import com.animeisland.bean.BaseResponse;
import com.animeisland.bean.PageQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.animeisland.entity.Media;
import com.animeisland.entity.Video;
import com.animeisland.service.MediaService;
import com.animeisland.service.VideoService;
import com.animeisland.utils.IdGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Api(tags = "媒体模块接口")
@RequestMapping("/api")
@RestController
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @Autowired
    private VideoService videoService;

    // 设置查询字段
    private static final String[] SQLSELECT_LIST = {"id", "name_zh", "name_orgin", "category_name", "type_name", "total", "release_time", "introduction", "tags", "status", "cover_url"};

    /**
     * 安全的id生成器，确保id在数据库中的唯一性
     * @param date 规格化日期
     * @return id 生成的Id
     */
    private Integer safeIdGenerator(String date) {
        // 最大容量
        int max = 1000;
        // id统计器
        Set<Integer> set = new HashSet<>();
        // 正则表达式
        String regex = "-";
        // id主键
        Integer id;
        // 生成条件构造器
        QueryWrapper<Media> queryWrapper = new QueryWrapper<>();
        // 判断生成的id是否重复并采取措施确保id全库唯一
        do {
            // 开始随机生成
            id = IdGenerator.baseGenerator(date, regex, max);
            // 将id存入集合中
            set.add(id);
            // 设置查询条件
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            queryWrapper.allEq(true, map, false);
            // 当集合中的元素等于最大容量时将id位数扩大1位(利用集合的不可重复性)
            if (set.size() >= max) {
                set.clear();
                max *= 10;
            }
        } while (mediaService.count(queryWrapper) != 0);
        return id;
    }

    @ApiOperation(value = "添加番剧")
    @PostMapping("/admin/media")
    public Object addMedia(@RequestBody Media entity) {
        // 设置id
        entity.setId(safeIdGenerator(entity.getReleaseTime()));
        // 存入数据库
        if (mediaService.save(entity)) {
            return new BaseResponse(HttpServletResponse.SC_OK, "添加成功！");
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "添加失败！");
        }
    }

    @ApiOperation(value = "批量添加番剧")
    @PostMapping("/admin/media/batch")
    public Object addMedia(@RequestBody List<Media> entityList) {
        if (entityList.isEmpty()) {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "上传番剧不能为空！");
        } else {
            final int batchSize = 1;
            for (Media item : entityList) {
                item.setId(safeIdGenerator(item.getReleaseTime()));
            }
            if (mediaService.saveBatch(entityList, batchSize)) {
                return new BaseResponse(HttpServletResponse.SC_OK, "批量添加成功！");
            } else {
                return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "批量添加失败！");
            }
        }
    }

    @ApiOperation(value = "获取media信息")
    @GetMapping("/media/{id}")
    public Object getMediaInfo(@PathVariable("id") Integer id) {
        // 封装查询条件
        QueryWrapper<Media> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        // 查询数据
        Map<String, Object> media = mediaService.getMap(queryWrapper);
        String tags = media.get("tags").toString();
        media.replace("tags", tags.split(","));
        return new BaseResponse(media);
    }

    @ApiOperation(value = "获取番剧信息（管理系统）")
    @GetMapping("/admin/media/{id}")
    public Object getMedia(@PathVariable("id") Integer id) {
        return new BaseResponse(mediaService.getById(id));
    }

    @GetMapping("/admin/media/{id}/download")
    public void downloadMediaInfo(@PathVariable("id") Serializable id, HttpServletResponse response) {
        String fileName = "media.json";
        File file = new File(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            FileOutputStream os = new FileOutputStream(file, false);
            OutputStreamWriter writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            Media media = mediaService.getById(id);
            writer.append(objectMapper.writeValueAsString(media));
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

    @ApiOperation(value = "获取番剧列表")
    @GetMapping("/media")
    public Object getMediaList(@RequestParam Map<String, Object> params) {
        QueryWrapper<Media> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(true, params, false);
        String[] sqlSelect =  {"id", "name_in_series", "screenshot_url", "cover_url"};
        queryWrapper.select(sqlSelect);
        return new BaseResponse(mediaService.listMaps(queryWrapper));
    }

    @ApiOperation(value = "获取番剧列表（分页）")
    @PostMapping("/media")
    public Object getMediaList(@RequestParam Map<String, Object> params, @RequestBody PageQuery query) {
        // 封装查询条件
        QueryWrapper<Media> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(true, "name_zh", query.getKey());
        // 1.此处用来判断筛选条件是否为all(表示无条件)，若存在则将其排除
        // 注：这里使用更为简洁的lambda表达式(Java8支持)，也可以使用Iterator进行Map的遍历
        params.entrySet().removeIf(entry -> entry.getValue().toString().equals("all"));
//            Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
//            while (it.hasNext()) {
//                Map.Entry<String, Object> entry = it.next();
//                if (entry.getValue().toString().equals("all")) {
//                    it.remove();
//                }
//            }
        // 2.判断Eq条件中是否含有tag，如果有，则将其放入like条件中
        if (params.containsKey("tag")) {
            queryWrapper.like(true, "tags", params.get("tag"));
            params.remove("tag");
        }
        queryWrapper.allEq(true, params, false);
        queryWrapper.select(SQLSELECT_LIST);
        queryWrapper.orderBy(true, !query.isReverse(), query.getOrderBy());
        // 分页查询
        IPage<Map<String, Object>> page = mediaService.pageMaps(new Page<>(query.getPageNum(), query.getPageSize()), queryWrapper);
        for (Map<String, Object> item : page.getRecords()) {
            String tags = item.get("tags").toString();
            item.replace("tags", tags.split(","));
        }
        // 返回结果
        return new BaseResponse(page);
    }

    @ApiOperation(value = "修改番剧")
    @PutMapping("/admin/media")
    public Object updateMedia(@RequestBody Media media) {
        if (mediaService.updateById(media)) {
            return new BaseResponse(HttpServletResponse.SC_OK, "修改成功！");
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "修改失败！");
        }
    }

    private Boolean removeVideoByMediaId(Serializable id) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("media_id", id);
        return videoService.remove(queryWrapper);
    }

    @ApiOperation(value = "删除番剧")
    @DeleteMapping("/admin/media/{id}")
    public Object deleteMedia(@PathVariable("id") Integer id) {
        if (mediaService.removeById(id)) {
            if (removeVideoByMediaId(id)) {
                return new BaseResponse(HttpServletResponse.SC_OK, "删除成功！");
            } else {
                return new BaseResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "相关视频删除失败！");
            }
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "番剧删除失败！");
        }
    }

    @ApiOperation(value = "批量删除番剧")
    @DeleteMapping("/admin/media")
    public Object deleteMedia(@RequestParam Map<Integer, Serializable> params) {
        if (mediaService.removeByIds(new ArrayList<>(params.values()))) {
            for (Serializable id : params.values()) {
                if (!removeVideoByMediaId(id)) {
                    return new BaseResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "相关视频删除失败！");
                }
            }
            return new BaseResponse(HttpServletResponse.SC_OK, "删除成功！");
        } else {
            return new BaseResponse(HttpServletResponse.SC_BAD_REQUEST, "番剧删除失败！");
        }
    }
}
