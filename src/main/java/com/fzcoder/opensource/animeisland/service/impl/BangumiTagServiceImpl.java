package com.fzcoder.opensource.animeisland.service.impl;

import com.fzcoder.opensource.animeisland.entity.BangumiTag;
import com.fzcoder.opensource.animeisland.entity.BangumiTagItem;
import com.fzcoder.opensource.animeisland.mapper.BangumiTagMapper;
import com.fzcoder.opensource.animeisland.service.IBangumiTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzcoder.opensource.animeisland.util.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/7/25 14:57
 */
@Service("video_BangumiTagServiceImpl")
public class BangumiTagServiceImpl extends ServiceImpl<BangumiTagMapper, BangumiTag> implements IBangumiTagService {
    @Override
    public boolean saveBangumiTagItem(BangumiTagItem tagItem) {
        return this.baseMapper.insertBangumiTagItem(tagItem) == 1;
    }

    @Override
    public boolean saveBangumiTagItems(String bangumiId, List<String> tagIds) {
        this.baseMapper.removeBangumiTagItems(bangumiId);
        for (String tagId: tagIds) {
            this.baseMapper.insertBangumiTagItem(new BangumiTagItem(
                    IdGenerator.generateUUID(), bangumiId, tagId
            ));
        }
        return true;
    }

    @Override
    public List<BangumiTag> getTagListByBangumi(String bangumiId) {
        return this.baseMapper.selectTagsByBangumiId(bangumiId);
    }
}
