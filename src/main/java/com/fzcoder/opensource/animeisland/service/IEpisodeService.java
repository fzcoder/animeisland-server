package com.fzcoder.opensource.animeisland.service;

import com.fzcoder.opensource.animeisland.entity.Episode;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IEpisodeService extends IService<Episode> {
    List<Episode> parseJsonForms(HttpServletRequest request);
}
