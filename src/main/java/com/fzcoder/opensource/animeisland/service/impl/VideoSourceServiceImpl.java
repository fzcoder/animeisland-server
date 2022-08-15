package com.fzcoder.opensource.animeisland.service.impl;

import com.fzcoder.opensource.animeisland.entity.VideoSource;
import com.fzcoder.opensource.animeisland.mapper.VideoSourceMapper;
import com.fzcoder.opensource.animeisland.service.IVideoSourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/8/7 16:21
 */
@Service("video_VideoSourceServiceImpl")
public class VideoSourceServiceImpl extends ServiceImpl<VideoSourceMapper, VideoSource> implements IVideoSourceService {
    @Override
    public VideoSource getWithSignatureUrlById(String id) {
        VideoSource source = baseMapper.selectById(id);
        return source;
    }
}
