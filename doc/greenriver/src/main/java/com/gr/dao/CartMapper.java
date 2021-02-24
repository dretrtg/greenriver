package com.gr.dao;

import com.gr.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectCartByUserIdGabageId(@Param("userId") Integer userId, @Param("gabageId")Integer gabageId);

    List<Cart> selectCartByUserId(Integer userId);

    int selectCartGabageCheckedStatusByUserId(Integer userId);

    int deleteByUserIdGabageIds(@Param("userId") Integer userId,@Param("gabageIdList")List<String> gabageIdList);


    int checkedOrUncheckedGabage(@Param("userId") Integer userId,@Param("gabageId")Integer gabageId,@Param("checked") Integer checked);

    int selectCartGabageCount(@Param("userId") Integer userId);


    List<Cart> selectCheckedCartByUserId(Integer userId);
}