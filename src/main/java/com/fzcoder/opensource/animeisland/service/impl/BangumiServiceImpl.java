package com.fzcoder.opensource.animeisland.service.impl;

import com.fzcoder.opensource.animeisland.entity.Bangumi;
import com.fzcoder.opensource.animeisland.mapper.BangumiMapper;
import com.fzcoder.opensource.animeisland.service.IBangumiService;
import com.fzcoder.opensource.animeisland.vo.BangumiVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("CloudXBangumi_BangumiServiceImpl")
public class BangumiServiceImpl extends ServiceImpl<BangumiMapper, Bangumi> implements IBangumiService {
    @Override
    public BangumiVO getVoById(String id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    public IPage<BangumiVO> voPage(IPage<BangumiVO> page, Wrapper<BangumiVO> queryWrapper) {
        return baseMapper.voPage(page, queryWrapper);
    }
}
