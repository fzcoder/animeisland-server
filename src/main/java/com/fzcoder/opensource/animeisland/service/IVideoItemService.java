package com.fzcoder.opensource.animeisland.service;

import com.fzcoder.opensource.animeisland.dto.VideoItemForm;
import com.fzcoder.opensource.animeisland.entity.VideoItem;
import com.fzcoder.opensource.animeisland.vo.VideoItemVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IVideoItemService extends IService<VideoItem> {
    VideoItemVO getVoById(String id);
    VideoItemForm getForm(String id);
    IPage<VideoItemVO> voPage(IPage<VideoItemVO> page, Wrapper<VideoItemVO> queryWrapper);
    List<VideoItemForm> parseJsonMultipleForm(HttpServletRequest request);
}
