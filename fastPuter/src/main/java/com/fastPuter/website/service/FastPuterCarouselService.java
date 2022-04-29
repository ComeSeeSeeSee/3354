package com.fastPuter.website.service;

import com.fastPuter.website.controller.vo.FastPuterIndexCarouselVO;
import com.fastPuter.website.entity.Carousel;
import com.fastPuter.website.util.PageQueryUtil;
import com.fastPuter.website.util.PageResult;

import java.util.List;

public interface FastPuterCarouselService {

    PageResult getCarouselPage(PageQueryUtil pageUtil);

    String saveCarousel(Carousel carousel);

    String updateCarousel(Carousel carousel);

    Carousel getCarouselById(Integer id);

    Boolean deleteBatch(Integer[] ids);

    List<FastPuterIndexCarouselVO> getCarouselsForIndex(int number);
}
