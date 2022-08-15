package com.fzcoder.opensource.animeisland.service;

import com.fzcoder.opensource.animeisland.entity.VideoSource;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/8/7 16:21
 */
public interface IVideoSourceService extends IService<VideoSource> {
    VideoSource getWithSignatureUrlById(String id);
}
