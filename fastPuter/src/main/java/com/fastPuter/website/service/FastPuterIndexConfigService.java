package com.fastPuter.website.service;

import com.fastPuter.website.controller.vo.FastPuterIndexConfigGoodsVO;
import com.fastPuter.website.entity.IndexConfig;
import com.fastPuter.website.util.PageQueryUtil;
import com.fastPuter.website.util.PageResult;

import java.util.List;

public interface FastPuterIndexConfigService {

    PageResult getConfigsPage(PageQueryUtil pageUtil);

    String saveIndexConfig(IndexConfig indexConfig);

    String updateIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Long id);


    List<FastPuterIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number);

    Boolean deleteBatch(Long[] ids);
}
