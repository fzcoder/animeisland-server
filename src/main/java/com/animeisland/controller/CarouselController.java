package com.animeisland.controller;

import com.animeisland.bean.BaseResponse;
import com.animeisland.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    @GetMapping("/carousel")
    public Object getList() {
        return new BaseResponse(carouselService.list());
    }
}
