package com.fzcoder.opensource.animeisland.service;

import com.fzcoder.opensource.animeisland.entity.Episode;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IEpisodeService extends IService<Episode> {
    boolean saveEpisode(Episode episode);
    boolean saveEpisodes(List<Episode> episodes);
    int getCountByBangumiId(String bangumiId);
    boolean changeOrderInBangumi(List<Episode> episodes);
    boolean deleteEpisodeById(String id);
    boolean deleteEpisodesByIds(List<String> ids);
}
