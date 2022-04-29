package com.fastPuter.website.service.impl;

import com.fastPuter.website.common.ServiceResultEnum;
import com.fastPuter.website.controller.vo.FastPuterSearchGoodsVO;
import com.fastPuter.website.dao.FastPuterGoodsMapper;
import com.fastPuter.website.entity.FastPuterItems;
import com.fastPuter.website.service.FastPuterGoodsService;
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
public class FastPuterGoodsServiceImpl implements FastPuterGoodsService {

    @Autowired
    private FastPuterGoodsMapper goodsMapper;

    @Override
    public PageResult getFastPuterGoodsPage(PageQueryUtil pageUtil) {
        List<FastPuterItems> goodsList = goodsMapper.findFastPuterGoodsList(pageUtil);
        int total = goodsMapper.getTotalFastPuterGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveFastPuterGoods(FastPuterItems goods) {
        if (goodsMapper.insertSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public void batchSaveFastPuterGoods(List<FastPuterItems> fastPuterItemsList) {
        if (!CollectionUtils.isEmpty(fastPuterItemsList)) {
            goodsMapper.batchInsert(fastPuterItemsList);
        }
    }

    @Override
    public String updateFastPuterGoods(FastPuterItems goods) {
        FastPuterItems temp = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        goods.setUpdateTime(new Date());
        if (goodsMapper.updateByPrimaryKeySelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public FastPuterItems getFastPuterGoodsById(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }
    
    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

    @Override
    public PageResult searchFastPuterGoods(PageQueryUtil pageUtil) {
        List<FastPuterItems> goodsList = goodsMapper.findFastPuterGoodsListBySearch(pageUtil);
        int total = goodsMapper.getTotalFastPuterGoodsBySearch(pageUtil);
        List<FastPuterSearchGoodsVO> fastPuterSearchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            fastPuterSearchGoodsVOS = BeanUtil.copyList(goodsList, FastPuterSearchGoodsVO.class);
            for (FastPuterSearchGoodsVO fastPuterSearchGoodsVO : fastPuterSearchGoodsVOS) {
                String goodsName = fastPuterSearchGoodsVO.getGoodsName();
                String goodsIntro = fastPuterSearchGoodsVO.getGoodsIntro();
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    fastPuterSearchGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    fastPuterSearchGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(fastPuterSearchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
