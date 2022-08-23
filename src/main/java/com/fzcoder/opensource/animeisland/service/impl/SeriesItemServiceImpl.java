package com.fzcoder.opensource.animeisland.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzcoder.opensource.animeisland.entity.SeriesItem;
import com.fzcoder.opensource.animeisland.mapper.SeriesItemMapper;
import com.fzcoder.opensource.animeisland.service.ISeriesItemService;
import com.fzcoder.opensource.animeisland.util.IdGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/8/20 1:18
 */
@Service("video_SeriesItemServiceImpl")
public class SeriesItemServiceImpl extends ServiceImpl<SeriesItemMapper, SeriesItem> implements ISeriesItemService {
    private boolean beforeRemoveItem(Integer itemOrder) {
        UpdateWrapper<SeriesItem> wrapper = new UpdateWrapper<>();
        wrapper.gt("order_in_series", itemOrder);
        wrapper.setSql("`order_in_series` = `order_in_series`-1");
        return baseMapper.update(null, wrapper) >= 0;
    }

    @Override
    public boolean saveSeriesItem(SeriesItem entity) {
        int currentTotal = getCountBySeriesId(entity.getSeriesId());
        LocalDateTime currentDateTime = LocalDateTime.now();
        entity.setId(IdGenerator.generateUUID());
        entity.setOrderInSeries(currentTotal + 1); // 序列从1开始计算
        entity.setCreateTime(currentDateTime);
        entity.setLastModifyTime(currentDateTime);
        return save(entity);
    }

    @Override
    public List<SeriesItem> getListByBangumiId(String bangumiId) {
        QueryWrapper<SeriesItem> wrapper = new QueryWrapper<>();
        wrapper.inSql("series_id", "select `series_id` from `tb_video_series_item` where bangumi_id='"+bangumiId+"'");
        wrapper.orderByAsc("order_in_series");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public int getCountBySeriesId(String seriesId) {
        QueryWrapper<SeriesItem> wrapper = new QueryWrapper<>();
        wrapper.eq("series_id", seriesId);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public boolean changeOrderInSeries(List<SeriesItem> seriesItems) {
        for (SeriesItem seriesItem : seriesItems) {
            UpdateWrapper<SeriesItem> wrapper = new UpdateWrapper<>();
            wrapper.set("order_in_series", seriesItem.getOrderInSeries());
            wrapper.set("last_modify_time", LocalDateTime.now());
            wrapper.eq("id", seriesItem.getId());
            if (!update(wrapper)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean deleteSeriesItemById(String id) {
        SeriesItem removeItem = baseMapper.selectById(id);
        return beforeRemoveItem(removeItem.getOrderInSeries()) && removeById(id);
    }

    @Override
    public boolean deleteSeriesItemsByIds(List<String> ids) {
        // 逆序查询要删除的系列项
        QueryWrapper<SeriesItem> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        wrapper.orderByDesc("order_in_series");
        List<SeriesItem> seriesItems = baseMapper.selectList(wrapper);
        // 批量删除
        for (SeriesItem seriesItem : seriesItems) {
            if (!deleteSeriesItemById(seriesItem.getId())) {
                return false;
            }
        }
        return true;
    }
}
