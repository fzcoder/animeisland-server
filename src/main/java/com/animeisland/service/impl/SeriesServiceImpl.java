package com.animeisland.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.animeisland.entity.Series;
import com.animeisland.mapper.SeriesMapper;
import com.animeisland.service.SeriesService;
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
public class SeriesServiceImpl extends ServiceImpl<SeriesMapper, Series> implements SeriesService {
}
