package com.fzcoder.opensource.animeisland.service;

import com.fzcoder.opensource.animeisland.entity.BangumiTag;
import com.fzcoder.opensource.animeisland.entity.BangumiTagItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/7/25 14:56
 */
public interface IBangumiTagService extends IService<BangumiTag> {
    boolean saveBangumiTagItem(BangumiTagItem tagItem);
    boolean saveBangumiTagItems(String bangumiId, List<String> tagIds);
    List<BangumiTag> getTagListByBangumi(String bangumiId);
}
