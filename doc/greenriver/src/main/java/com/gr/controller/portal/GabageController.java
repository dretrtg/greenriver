package com.gr.controller.portal;

import com.github.pagehelper.PageInfo;
import com.gr.common.ServerResponse;
import com.gr.service.IGabageService;
import com.gr.vo.GabageDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 18334 on 2019/6/11.
 */

@Controller
@RequestMapping("/gabage/")
public class GabageController {

    @Autowired
    private IGabageService iGabageService;

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<GabageDetailVo> detail(Integer gabageId){
        return iGabageService.getGabageDetail(gabageId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false)String keyword,
                                         @RequestParam(value = "categoryId",required = false)Integer categoryId,
                                         @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
        return iGabageService.getGabageByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }
}
