package com.fzcoder.opensource.animeisland.mapper;

import com.fzcoder.opensource.animeisland.entity.VideoSource;
import com.fzcoder.opensource.animeisland.vo.VideoSourceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/8/7 16:20
 */
public interface VideoSourceMapper extends BaseMapper<VideoSource> {
    List<VideoSource> selectByVideoId(@Param("videoId") String videoId);
    List<VideoSourceVO> selectVoByVideoId (@Param("videoId")String videoId);
}
