package com.fastPuter.website.service;

import com.fastPuter.website.controller.vo.FastPuterIndexCategoryVO;
import com.fastPuter.website.controller.vo.SearchPageCategoryVO;
import com.fastPuter.website.entity.GoodsCategory;
import com.fastPuter.website.util.PageQueryUtil;
import com.fastPuter.website.util.PageResult;

import java.util.List;

public interface FastPuterCategoryService {

    PageResult getCategorisPage(PageQueryUtil pageUtil);

    String saveCategory(GoodsCategory goodsCategory);

    String updateGoodsCategory(GoodsCategory goodsCategory);

    GoodsCategory getGoodsCategoryById(Long id);

    Boolean deleteBatch(Integer[] ids);

    List<FastPuterIndexCategoryVO> getCategoriesForIndex();

    SearchPageCategoryVO getCategoriesForSearch(Long categoryId);

    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel);
}
