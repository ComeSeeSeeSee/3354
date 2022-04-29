package com.fastPuter.website.service;

import com.fastPuter.website.entity.FastPuterItems;
import com.fastPuter.website.util.PageQueryUtil;
import com.fastPuter.website.util.PageResult;

import java.util.List;

public interface FastPuterGoodsService {

    PageResult getFastPuterGoodsPage(PageQueryUtil pageUtil);

    String saveFastPuterGoods(FastPuterItems goods);

    void batchSaveFastPuterGoods(List<FastPuterItems> fastPuterItemsList);

    String updateFastPuterGoods(FastPuterItems goods);

    FastPuterItems getFastPuterGoodsById(Long id);

    Boolean batchUpdateSellStatus(Long[] ids,int sellStatus);

    PageResult searchFastPuterGoods(PageQueryUtil pageUtil);
}
