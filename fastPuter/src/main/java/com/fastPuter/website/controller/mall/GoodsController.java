package com.fastPuter.website.controller.mall;

import com.fastPuter.website.common.Constants;
import com.fastPuter.website.common.FastPuterException;
import com.fastPuter.website.common.ServiceResultEnum;
import com.fastPuter.website.controller.vo.FastPuterGoodsDetailVO;
import com.fastPuter.website.controller.vo.SearchPageCategoryVO;
import com.fastPuter.website.entity.FastPuterItems;
import com.fastPuter.website.service.FastPuterCategoryService;
import com.fastPuter.website.service.FastPuterGoodsService;
import com.fastPuter.website.util.BeanUtil;
import com.fastPuter.website.util.PageQueryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class GoodsController {

    @Resource
    private FastPuterGoodsService fastPuterGoodsService;
    @Resource
    private FastPuterCategoryService fastPuterCategoryService;

    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);

        if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = fastPuterCategoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }

        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";

        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);

        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);

        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", fastPuterGoodsService.searchFastPuterGoods(pageUtil));
        return "mall/search";
    }

    @GetMapping("/goods/detail/{goodsId}")
    public String detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        if (goodsId < 1) {
            return "error/error_5xx";
        }
        FastPuterItems goods = fastPuterGoodsService.getFastPuterGoodsById(goodsId);
        if (goods == null) {
            FastPuterException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            FastPuterException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        FastPuterGoodsDetailVO goodsDetailVO = new FastPuterGoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        request.setAttribute("goodsDetail", goodsDetailVO);
        return "mall/detail";
    }

}
