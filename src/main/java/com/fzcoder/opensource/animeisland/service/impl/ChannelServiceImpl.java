package com.fzcoder.opensource.animeisland.service.impl;

import com.fzcoder.opensource.animeisland.entity.Channel;
import com.fzcoder.opensource.animeisland.mapper.ChannelMapper;
import com.fzcoder.opensource.animeisland.service.IChannelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/7/24 21:25
 */
@Service("video_ChannelServiceImpl")
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements IChannelService {
    @Override
    public boolean isUniqueAccessPathValid(String path, String uid) {
        QueryWrapper<Channel> qw = new QueryWrapper<>();
        qw.eq("uid", uid);
        qw.eq("unique_access_path", path);
        return this.baseMapper.selectCount(qw) == 0;
    }

    @Override
    public boolean isFormValid(Channel channel) {
        return isUniqueAccessPathValid(channel.getUniqueAccessPath(), channel.getUid());
    }
}
