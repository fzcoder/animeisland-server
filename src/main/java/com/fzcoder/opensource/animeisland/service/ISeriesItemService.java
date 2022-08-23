package com.fzcoder.opensource.animeisland.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzcoder.opensource.animeisland.entity.SeriesItem;

import java.util.List;

/**
 * @author Zhen Fang
 * @version 1.0
 * @date 2022/8/20 1:18
 */
public interface ISeriesItemService extends IService<SeriesItem> {
    boolean saveSeriesItem(SeriesItem entity);
    List<SeriesItem> getListByBangumiId(String bangumiId);
    int getCountBySeriesId(String seriesId);
    boolean changeOrderInSeries(List<SeriesItem> seriesItems);
    boolean deleteSeriesItemById(String id);
    boolean deleteSeriesItemsByIds(List<String> ids);
}
