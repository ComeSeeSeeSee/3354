package com.fastPuter.website.service.impl;

import com.fastPuter.website.common.ServiceResultEnum;
import com.fastPuter.website.controller.vo.FastPuterIndexCarouselVO;
import com.fastPuter.website.dao.CarouselMapper;
import com.fastPuter.website.entity.Carousel;
import com.fastPuter.website.service.FastPuterCarouselService;
import com.fastPuter.website.util.BeanUtil;
import com.fastPuter.website.util.PageQueryUtil;
import com.fastPuter.website.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FastPuterCarouselServiceImpl implements FastPuterCarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public PageResult getCarouselPage(PageQueryUtil pageUtil) {
        List<Carousel> carousels = carouselMapper.findCarouselList(pageUtil);
        int total = carouselMapper.getTotalCarousels(pageUtil);
        PageResult pageResult = new PageResult(carousels, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveCarousel(Carousel carousel) {
        if (carouselMapper.insertSelective(carousel) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateCarousel(Carousel carousel) {
        Carousel temp = carouselMapper.selectByPrimaryKey(carousel.getCarouselId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        temp.setCarouselRank(carousel.getCarouselRank());
        temp.setRedirectUrl(carousel.getRedirectUrl());
        temp.setCarouselUrl(carousel.getCarouselUrl());
        temp.setUpdateTime(new Date());
        if (carouselMapper.updateByPrimaryKeySelective(temp) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public Carousel getCarouselById(Integer id) {
        return carouselMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        return carouselMapper.deleteBatch(ids) > 0;
    }

    @Override
    public List<FastPuterIndexCarouselVO> getCarouselsForIndex(int number) {
        List<FastPuterIndexCarouselVO> fastPuterIndexCarouselVOS = new ArrayList<>(number);
        List<Carousel> carousels = carouselMapper.findCarouselsByNum(number);
        if (!CollectionUtils.isEmpty(carousels)) {
            fastPuterIndexCarouselVOS = BeanUtil.copyList(carousels, FastPuterIndexCarouselVO.class);
        }
        return fastPuterIndexCarouselVOS;
    }
}
