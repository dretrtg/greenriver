package com.gr.service;

import com.github.pagehelper.PageInfo;
import com.gr.common.ServerResponse;
import com.gr.pojo.Gabage;
import com.gr.vo.GabageDetailVo;

/**
 * Created by 18334 on 2019/6/11.
 */
public interface IGabageService {

    ServerResponse saveOrUpdateGabage(Gabage gabage);

    ServerResponse<String> setSaleStatus(Integer gabageId, Integer status);

    ServerResponse<GabageDetailVo> manageGabageDetail(Integer gabageId);

    ServerResponse<PageInfo> getGabageList(int pageNum, int pageSize);

    ServerResponse<PageInfo> getGabageSimList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchGabage(String gabageName, Integer gabageId, int pageNum, int pageSize);

    ServerResponse<GabageDetailVo> getGabageDetail(Integer gabageId);

    ServerResponse<PageInfo> getGabageByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);
}
