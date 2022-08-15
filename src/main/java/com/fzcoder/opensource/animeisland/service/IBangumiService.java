package com.fzcoder.opensource.animeisland.service;

import com.fzcoder.opensource.animeisland.entity.Bangumi;
import com.fzcoder.opensource.animeisland.vo.BangumiVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IBangumiService extends IService<Bangumi> {
    BangumiVO getVoById(String id);
    IPage<BangumiVO> voPage(IPage<BangumiVO> page, Wrapper<BangumiVO> queryWrapper);
}
