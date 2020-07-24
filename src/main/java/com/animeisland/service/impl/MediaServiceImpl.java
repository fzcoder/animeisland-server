package com.animeisland.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.animeisland.entity.Media;
import com.animeisland.mapper.MediaMapper;
import com.animeisland.service.MediaService;
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
public class MediaServiceImpl extends ServiceImpl<MediaMapper, Media> implements MediaService {
}
