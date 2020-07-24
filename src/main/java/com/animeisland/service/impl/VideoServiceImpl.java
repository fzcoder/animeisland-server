package com.animeisland.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.animeisland.entity.Video;
import com.animeisland.mapper.VideoMapper;
import com.animeisland.service.VideoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author frank fang
 * @since 2020-03-01
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
}
