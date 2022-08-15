package com.fzcoder.opensource.animeisland.mapper;

import com.fzcoder.opensource.animeisland.dto.VideoItemForm;
import com.fzcoder.opensource.animeisland.entity.VideoItem;
import com.fzcoder.opensource.animeisland.vo.VideoItemVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoItemMapper extends BaseMapper<VideoItem> {
    VideoItemVO selectVoById(String id);
    VideoItemForm getForm(String id);
    IPage<VideoItemVO> voPage(IPage<VideoItemVO> page, @Param(Constants.WRAPPER) Wrapper<VideoItemVO> queryWrapper);
}
