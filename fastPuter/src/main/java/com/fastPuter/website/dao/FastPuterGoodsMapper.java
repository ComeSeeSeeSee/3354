package com.fastPuter.website.dao;

import com.fastPuter.website.entity.FastPuterItems;
import com.fastPuter.website.entity.StockNumDTO;
import com.fastPuter.website.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FastPuterGoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(FastPuterItems record);

    int insertSelective(FastPuterItems record);

    FastPuterItems selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(FastPuterItems record);

    int updateByPrimaryKeyWithBLOBs(FastPuterItems record);

    int updateByPrimaryKey(FastPuterItems record);

    List<FastPuterItems> findFastPuterGoodsList(PageQueryUtil pageUtil);

    int getTotalFastPuterGoods(PageQueryUtil pageUtil);

    List<FastPuterItems> selectByPrimaryKeys(List<Long> goodsIds);

    List<FastPuterItems> findFastPuterGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalFastPuterGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("fastPuterGoodsList") List<FastPuterItems> fastPuterItemsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

}