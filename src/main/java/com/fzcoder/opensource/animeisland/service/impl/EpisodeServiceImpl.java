package com.fzcoder.opensource.animeisland.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fzcoder.opensource.animeisland.entity.Episode;
import com.fzcoder.opensource.animeisland.mapper.EpisodeMapper;
import com.fzcoder.opensource.animeisland.service.IEpisodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzcoder.opensource.animeisland.util.IdGenerator;
import com.fzcoder.opensource.animeisland.util.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("CloudXVideo_EpisodeServiceImpl")
public class EpisodeServiceImpl extends ServiceImpl<EpisodeMapper, Episode> implements IEpisodeService {
    private boolean beforeRemoveById(Integer index) {
        UpdateWrapper<Episode> wrapper = new UpdateWrapper<>();
        wrapper.gt("order_in_bangumi", index);
        wrapper.setSql("`order_in_bangumi`=`order_in_bangumi`-1");
        return update(wrapper);
    }
    @Override
    public boolean saveEpisode(Episode episode) {
        if (episode.getBangumiId() == null || Strings.isEqualEmpty(episode.getBangumiId())) {
            return false;
        }
        int currentIndex = getCountByBangumiId(episode.getBangumiId()) + 1;
        LocalDateTime currentDateTime = LocalDateTime.now();
        episode.setId(IdGenerator.generateEpisodeId());
        episode.setOrderInBangumi(currentIndex);
        episode.setCreateTime(currentDateTime);
        episode.setLastModifyTime(currentDateTime);
        return save(episode);
    }

    @Override
    public boolean saveEpisodes(List<Episode> episodes) {
        for (Episode episode : episodes) {
            saveEpisode(episode);
        }
        return true;
    }

    @Override
    public int getCountByBangumiId(String bangumiId) {
        QueryWrapper<Episode> wrapper = new QueryWrapper<>();
        wrapper.eq("bangumi_id", bangumiId);
        return count(wrapper);
    }

    @Override
    public boolean changeOrderInBangumi(List<Episode> episodes) {
        for (Episode episode : episodes) {
            UpdateWrapper<Episode> wrapper = new UpdateWrapper<>();
            wrapper.set("order_in_bangumi", episode.getOrderInBangumi());
            wrapper.set("last_modify_time", LocalDateTime.now());
            wrapper.eq("id", episode.getId());
            update(wrapper);
        }
        return true;
    }

    @Override
    public boolean deleteEpisodeById(String id) {
        return beforeRemoveById(getById(id).getOrderInBangumi()) && removeById(id);
    }

    @Override
    public boolean deleteEpisodesByIds(List<String> ids) {
        QueryWrapper<Episode> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        wrapper.orderByDesc("order_in_bangumi");
        List<Episode> episodes = list(wrapper);
        for (Episode episode : episodes) {
            deleteEpisodeById(episode.getId());
        }
        return true;
    }
}
