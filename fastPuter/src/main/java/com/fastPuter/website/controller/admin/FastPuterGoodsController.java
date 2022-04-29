package com.fastPuter.website.controller.admin;

import com.fastPuter.website.common.Constants;
import com.fastPuter.website.common.FastPuterCategoryLevelEnum;
import com.fastPuter.website.common.ServiceResultEnum;
import com.fastPuter.website.entity.FastPuterItems;
import com.fastPuter.website.entity.GoodsCategory;
import com.fastPuter.website.service.FastPuterCategoryService;
import com.fastPuter.website.service.FastPuterGoodsService;
import com.fastPuter.website.util.PageQueryUtil;
import com.fastPuter.website.util.Result;
import com.fastPuter.website.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/admin")
public class FastPuterGoodsController {

    @Resource
    private FastPuterGoodsService fastPuterGoodsService;
    @Resource
    private FastPuterCategoryService fastPuterCategoryService;

    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "fastputer_goods");
        return "admin/fastputer_goods";
    }

    @GetMapping("/goods/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        List<GoodsCategory> firstLevelCategories = fastPuterCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), FastPuterCategoryLevelEnum.LEVEL_ONE.getLevel());
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            List<GoodsCategory> secondLevelCategories = fastPuterCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), FastPuterCategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                List<GoodsCategory> thirdLevelCategories = fastPuterCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), FastPuterCategoryLevelEnum.LEVEL_THREE.getLevel());
                request.setAttribute("firstLevelCategories", firstLevelCategories);
                request.setAttribute("secondLevelCategories", secondLevelCategories);
                request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                request.setAttribute("path", "goods-edit");
                return "admin/fastputer_goods_edit";
            }
        }
        return "error/error_5xx";
    }

    @GetMapping("/goods/edit/{goodsId}")
    public String edit(HttpServletRequest request, @PathVariable("goodsId") Long goodsId) {
        request.setAttribute("path", "edit");
        FastPuterItems fastPuterItems = fastPuterGoodsService.getFastPuterGoodsById(goodsId);
        if (fastPuterItems == null) {
            return "error/error_400";
        }
        if (fastPuterItems.getGoodsCategoryId() > 0) {
            if (fastPuterItems.getGoodsCategoryId() != null || fastPuterItems.getGoodsCategoryId() > 0) {
                GoodsCategory currentGoodsCategory = fastPuterCategoryService.getGoodsCategoryById(fastPuterItems.getGoodsCategoryId());
                if (currentGoodsCategory != null && currentGoodsCategory.getCategoryLevel() == FastPuterCategoryLevelEnum.LEVEL_THREE.getLevel()) {
                    List<GoodsCategory> firstLevelCategories = fastPuterCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), FastPuterCategoryLevelEnum.LEVEL_ONE.getLevel());
                    List<GoodsCategory> thirdLevelCategories = fastPuterCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(currentGoodsCategory.getParentId()), FastPuterCategoryLevelEnum.LEVEL_THREE.getLevel());
                    GoodsCategory secondCategory = fastPuterCategoryService.getGoodsCategoryById(currentGoodsCategory.getParentId());
                    if (secondCategory != null) {
                        List<GoodsCategory> secondLevelCategories = fastPuterCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondCategory.getParentId()), FastPuterCategoryLevelEnum.LEVEL_TWO.getLevel());
                        GoodsCategory firestCategory = fastPuterCategoryService.getGoodsCategoryById(secondCategory.getParentId());
                        if (firestCategory != null) {
                            request.setAttribute("firstLevelCategories", firstLevelCategories);
                            request.setAttribute("secondLevelCategories", secondLevelCategories);
                            request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                            request.setAttribute("firstLevelCategoryId", firestCategory.getCategoryId());
                            request.setAttribute("secondLevelCategoryId", secondCategory.getCategoryId());
                            request.setAttribute("thirdLevelCategoryId", currentGoodsCategory.getCategoryId());
                        }
                    }
                }
            }
        }
        if (fastPuterItems.getGoodsCategoryId() == 0) {
            List<GoodsCategory> firstLevelCategories = fastPuterCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), FastPuterCategoryLevelEnum.LEVEL_ONE.getLevel());
            if (!CollectionUtils.isEmpty(firstLevelCategories)) {
                List<GoodsCategory> secondLevelCategories = fastPuterCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), FastPuterCategoryLevelEnum.LEVEL_TWO.getLevel());
                if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                    List<GoodsCategory> thirdLevelCategories = fastPuterCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), FastPuterCategoryLevelEnum.LEVEL_THREE.getLevel());
                    request.setAttribute("firstLevelCategories", firstLevelCategories);
                    request.setAttribute("secondLevelCategories", secondLevelCategories);
                    request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                }
            }
        }
        request.setAttribute("goods", fastPuterItems);
        request.setAttribute("path", "goods-edit");
        return "admin/fastputer_goods_edit";
    }

    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("Parameters exception！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(fastPuterGoodsService.getFastPuterGoodsPage(pageUtil));
    }

    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody FastPuterItems fastPuterItems) {
        if (StringUtils.isEmpty(fastPuterItems.getGoodsName())
                || StringUtils.isEmpty(fastPuterItems.getGoodsIntro())
                || StringUtils.isEmpty(fastPuterItems.getTag())
                || Objects.isNull(fastPuterItems.getOriginalPrice())
                || Objects.isNull(fastPuterItems.getGoodsCategoryId())
                || Objects.isNull(fastPuterItems.getSellingPrice())
                || Objects.isNull(fastPuterItems.getStockNum())
                || Objects.isNull(fastPuterItems.getGoodsSellStatus())
                || StringUtils.isEmpty(fastPuterItems.getGoodsCoverImg())
                || StringUtils.isEmpty(fastPuterItems.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("Parameters exception！");
        }
        String result = fastPuterGoodsService.saveFastPuterGoods(fastPuterItems);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody FastPuterItems fastPuterItems) {
        if (Objects.isNull(fastPuterItems.getGoodsId())
                || StringUtils.isEmpty(fastPuterItems.getGoodsName())
                || StringUtils.isEmpty(fastPuterItems.getGoodsIntro())
                || StringUtils.isEmpty(fastPuterItems.getTag())
                || Objects.isNull(fastPuterItems.getOriginalPrice())
                || Objects.isNull(fastPuterItems.getSellingPrice())
                || Objects.isNull(fastPuterItems.getGoodsCategoryId())
                || Objects.isNull(fastPuterItems.getStockNum())
                || Objects.isNull(fastPuterItems.getGoodsSellStatus())
                || StringUtils.isEmpty(fastPuterItems.getGoodsCoverImg())
                || StringUtils.isEmpty(fastPuterItems.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("Parameters exception！");
        }
        String result = fastPuterGoodsService.updateFastPuterGoods(fastPuterItems);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @GetMapping("/goods/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        FastPuterItems goods = fastPuterGoodsService.getFastPuterGoodsById(id);
        if (goods == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(goods);
    }

    @RequestMapping(value = "/goods/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("Parameters exception！");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return ResultGenerator.genFailResult("Status exception！！");
        }
        if (fastPuterGoodsService.batchUpdateSellStatus(ids, sellStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("Fail to modify");
        }
    }

}