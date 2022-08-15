package com.fzcoder.opensource.animeisland.service;

import com.fzcoder.opensource.animeisland.entity.Channel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/7/24 21:23
 */
public interface IChannelService extends IService<Channel> {
    boolean isUniqueAccessPathValid(String path, String uid);
    boolean isFormValid(Channel channel);
}
