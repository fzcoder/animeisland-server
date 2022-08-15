package com.fzcoder.opensource.animeisland.service;

import com.fzcoder.opensource.animeisland.entity.Settings;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/7/25 22:38
 */
public interface IVideoSettingsService extends IService<Settings> {
    Settings getVideoSettingsByUserId(String userId);
}
