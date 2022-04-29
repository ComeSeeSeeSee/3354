package com.fastPuter.website.controller.mall;

import com.fastPuter.website.common.Constants;
import com.fastPuter.website.common.IndexConfigTypeEnum;
import com.fastPuter.website.controller.vo.FastPuterIndexCarouselVO;
import com.fastPuter.website.controller.vo.FastPuterIndexCategoryVO;
import com.fastPuter.website.controller.vo.FastPuterIndexConfigGoodsVO;
import com.fastPuter.website.service.FastPuterCarouselService;
import com.fastPuter.website.service.FastPuterCategoryService;
import com.fastPuter.website.service.FastPuterIndexConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    private FastPuterCarouselService fastPuterCarouselService;

    @Resource
    private FastPuterIndexConfigService fastPuterIndexConfigService;

    @Resource
    private FastPuterCategoryService fastPuterCategoryService;

    @GetMapping({"/index", "/", "/index.html"})
    public String indexPage(HttpServletRequest request) {
        List<FastPuterIndexCategoryVO> categories = fastPuterCategoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            return "error/error_5xx";
        }
        List<FastPuterIndexCarouselVO> carousels = fastPuterCarouselService.getCarouselsForIndex(Constants.INDEX_CAROUSEL_NUMBER);
        List<FastPuterIndexConfigGoodsVO> hotGoodses = fastPuterIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), Constants.INDEX_GOODS_HOT_NUMBER);
        List<FastPuterIndexConfigGoodsVO> newGoodses = fastPuterIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), Constants.INDEX_GOODS_NEW_NUMBER);
        List<FastPuterIndexConfigGoodsVO> recommendGoodses = fastPuterIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(), Constants.INDEX_GOODS_RECOMMOND_NUMBER);
        request.setAttribute("categories", categories);
        request.setAttribute("carousels", carousels);
        request.setAttribute("hotGoodses", hotGoodses);
        request.setAttribute("newGoodses", newGoodses);
        request.setAttribute("recommendGoodses", recommendGoodses);
        return "mall/index";
    }
}
