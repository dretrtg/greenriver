package com.gr.dao;

import com.gr.pojo.Gabage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GabageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Gabage record);

    int insertSelective(Gabage record);

    Gabage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Gabage record);

    int updateByPrimaryKey(Gabage record);

    List<Gabage> selectList();

    List<Gabage> selectByNameAndGabageId(@Param("gabageName")String gabageName, @Param("gabageId") Integer gabageId);

    List<Gabage> selectByNameAndCategoryIds(@Param("gabageName")String gabageName,@Param("categoryIdList")List<Integer> categoryIdList);
}