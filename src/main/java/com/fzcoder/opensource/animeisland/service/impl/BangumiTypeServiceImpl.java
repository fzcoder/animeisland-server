package com.fzcoder.opensource.animeisland.service.impl;

import com.fzcoder.opensource.animeisland.entity.BangumiType;
import com.fzcoder.opensource.animeisland.mapper.BangumiTypeMapper;
import com.fzcoder.opensource.animeisland.service.IBangumiTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/7/25 14:54
 */
@Service("video_BangumiTypeServiceImpl")
public class BangumiTypeServiceImpl extends ServiceImpl<BangumiTypeMapper, BangumiType> implements IBangumiTypeService {
}
