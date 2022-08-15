package com.fzcoder.opensource.animeisland.service.impl;

import com.fzcoder.opensource.animeisland.entity.Settings;
import com.fzcoder.opensource.animeisland.mapper.VideoSettingsMapper;
import com.fzcoder.opensource.animeisland.service.IVideoSettingsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzcoder.opensource.animeisland.util.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/7/25 22:39
 */
@Slf4j
@Service("video_VideoSettingsServiceImpl")
public class VideoSettingsServiceImpl extends ServiceImpl<VideoSettingsMapper, Settings> implements IVideoSettingsService {
    @Override
    public Settings getVideoSettingsByUserId(String userId) {
        QueryWrapper<Settings> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        if (baseMapper.selectCount(qw) == 0) {
            Settings settings = new Settings();
            settings.setId(IdGenerator.generateUUID());
            settings.setUserId(userId);
            settings.setWebsiteHost("");
            settings.setWebsiteBrand("MyVideo");
            if (baseMapper.insert(settings)!= 1) {
                log.error("[Exception] Failed to insert blog settings");
                return null;
            }
            return settings;
        }
        return baseMapper.selectOne(qw);
    }
}
