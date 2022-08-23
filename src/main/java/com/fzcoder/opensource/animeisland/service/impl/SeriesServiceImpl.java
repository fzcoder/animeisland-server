package com.fzcoder.opensource.animeisland.service.impl;

import com.fzcoder.opensource.animeisland.entity.Series;
import com.fzcoder.opensource.animeisland.mapper.SeriesMapper;
import com.fzcoder.opensource.animeisland.service.ISeriesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("video_SeriesServiceImpl")
public class SeriesServiceImpl extends ServiceImpl<SeriesMapper, Series> implements ISeriesService {
}
