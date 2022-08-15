package com.fzcoder.opensource.animeisland.mapper;

import com.fzcoder.opensource.animeisland.entity.BangumiTag;
import com.fzcoder.opensource.animeisland.entity.BangumiTagItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/7/25 14:52
 */
public interface BangumiTagMapper extends BaseMapper<BangumiTag> {
    int insertBangumiTagItem(@Param("tagItem")BangumiTagItem tagItem);
    List<BangumiTag> selectTagsByBangumiId(@Param("bangumiId") String bangumiId);
    int removeBangumiTagItems(@Param("bangumiId") String bangumiId);
}
