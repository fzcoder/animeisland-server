package com.fzcoder.opensource.animeisland.mapper;

import com.fzcoder.opensource.animeisland.entity.Bangumi;
import com.fzcoder.opensource.animeisland.vo.BangumiVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

public interface BangumiMapper extends BaseMapper<Bangumi> {
    BangumiVO selectVoById(String id);
    IPage<BangumiVO> voPage(IPage<BangumiVO> page, @Param(Constants.WRAPPER) Wrapper<BangumiVO> queryWrapper);
}
