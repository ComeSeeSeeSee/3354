package com.fastPuter.website.service.impl;

import com.fastPuter.website.common.ServiceResultEnum;
import com.fastPuter.website.controller.vo.FastPuterIndexConfigGoodsVO;
import com.fastPuter.website.dao.IndexConfigMapper;
import com.fastPuter.website.dao.FastPuterGoodsMapper;
import com.fastPuter.website.entity.FastPuterItems;
import com.fastPuter.website.entity.IndexConfig;
import com.fastPuter.website.service.FastPuterIndexConfigService;
import com.fastPuter.website.util.BeanUtil;
import com.fastPuter.website.util.PageQueryUtil;
import com.fastPuter.website.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FastPuterIndexConfigServiceImpl implements FastPuterIndexConfigService {

    @Autowired
    private IndexConfigMapper indexConfigMapper;

    @Autowired
    private FastPuterGoodsMapper goodsMapper;

    @Override
    public PageResult getConfigsPage(PageQueryUtil pageUtil) {
        List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigList(pageUtil);
        int total = indexConfigMapper.getTotalIndexConfigs(pageUtil);
        PageResult pageResult = new PageResult(indexConfigs, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveIndexConfig(IndexConfig indexConfig) {
        if (indexConfigMapper.insertSelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateIndexConfig(IndexConfig indexConfig) {
        IndexConfig temp = indexConfigMapper.selectByPrimaryKey(indexConfig.getConfigId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        if (indexConfigMapper.updateByPrimaryKeySelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public IndexConfig getIndexConfigById(Long id) {
        return null;
    }

    @Override
    public List<FastPuterIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number) {
        List<FastPuterIndexConfigGoodsVO> fastPuterIndexConfigGoodsVOS = new ArrayList<>(number);
        List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigsByTypeAndNum(configType, number);
        if (!CollectionUtils.isEmpty(indexConfigs)) {
            List<Long> goodsIds = indexConfigs.stream().map(IndexConfig::getGoodsId).collect(Collectors.toList());
            List<FastPuterItems> fastPuterGoods = goodsMapper.selectByPrimaryKeys(goodsIds);
            fastPuterIndexConfigGoodsVOS = BeanUtil.copyList(fastPuterGoods, FastPuterIndexConfigGoodsVO.class);
            for (FastPuterIndexConfigGoodsVO fastPuterIndexConfigGoodsVO : fastPuterIndexConfigGoodsVOS) {
                String goodsName = fastPuterIndexConfigGoodsVO.getGoodsName();
                String goodsIntro = fastPuterIndexConfigGoodsVO.getGoodsIntro();
                if (goodsName.length() > 30) {
                    goodsName = goodsName.substring(0, 30) + "...";
                    fastPuterIndexConfigGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 22) {
                    goodsIntro = goodsIntro.substring(0, 22) + "...";
                    fastPuterIndexConfigGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        return fastPuterIndexConfigGoodsVOS;
    }

    @Override
    public Boolean deleteBatch(Long[] ids) {
        if (ids.length < 1) {
            return false;
        }
        return indexConfigMapper.deleteBatch(ids) > 0;
    }
}
