package com.fzcoder.opensource.animeisland.service.impl;

import com.fzcoder.opensource.animeisland.entity.Episode;
import com.fzcoder.opensource.animeisland.mapper.EpisodeMapper;
import com.fzcoder.opensource.animeisland.service.IEpisodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("CloudXVideo_EpisodeServiceImpl")
public class EpisodeServiceImpl extends ServiceImpl<EpisodeMapper, Episode> implements IEpisodeService {
    @Override
    public List<Episode> parseJsonForms(HttpServletRequest request) {
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
                return mapper.readValue(jsonData, new TypeReference<List<Episode>>() {});
            } catch (Exception e) {
                log.error("[upload]-->There has something wrong with the upload", e);
            } finally {
                log.info("[upload]-->End of upload");
            }
            return new ArrayList<>();
        }
    }
}
