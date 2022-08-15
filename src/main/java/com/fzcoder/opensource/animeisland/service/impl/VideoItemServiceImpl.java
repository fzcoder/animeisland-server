package com.fzcoder.opensource.animeisland.service.impl;

import com.fzcoder.opensource.animeisland.dto.VideoItemForm;
import com.fzcoder.opensource.animeisland.entity.VideoItem;
import com.fzcoder.opensource.animeisland.mapper.VideoItemMapper;
import com.fzcoder.opensource.animeisland.service.IVideoItemService;
import com.fzcoder.opensource.animeisland.vo.VideoItemVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service("CloudXVideo_VideoItemServiceImpl")
public class VideoItemServiceImpl extends ServiceImpl<VideoItemMapper, VideoItem> implements IVideoItemService {
    @Override
    public VideoItemVO getVoById(String id) {
        return this.baseMapper.selectVoById(id);
    }

    @Override
    public VideoItemForm getForm(String id) {
        return this.baseMapper.getForm(id);
    }

    @Override
    public IPage<VideoItemVO> voPage(IPage<VideoItemVO> page, Wrapper<VideoItemVO> queryWrapper) {
        return this.baseMapper.voPage(page, queryWrapper);
    }

    @Override
    public List<VideoItemForm> parseJsonMultipleForm(HttpServletRequest request) {
        log.info("[upload]-->Start to upload file");
        MultipartFile multipartFile = ((MultipartHttpServletRequest) request).getFile("file");
        if (multipartFile == null) {
            log.error("[upload]-->Not found the binary file");
            return new ArrayList<>();
        } else {
            try {
                String jsonData = new String(multipartFile.getBytes(), StandardCharsets.UTF_8);
                log.debug("jsonData: " + jsonData);
                // 解析JSON字符串
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(jsonData, new TypeReference<List<VideoItemForm>>() {});
            } catch (Exception e) {
                log.error("[upload]-->There has something wrong with the upload", e);
            } finally {
                log.info("[upload]-->End of upload");
            }
            return new ArrayList<>();
        }
    }
}
