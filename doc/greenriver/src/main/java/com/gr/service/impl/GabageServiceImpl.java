package com.gr.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.gr.common.Const;
import com.gr.common.ResponseCode;
import com.gr.common.ServerResponse;
import com.gr.dao.CategoryMapper;
import com.gr.dao.GabageMapper;
import com.gr.pojo.Category;
import com.gr.pojo.Gabage;
import com.gr.service.ICategoryService;
import com.gr.service.IGabageService;
import com.gr.util.DateTimeUtil;
import com.gr.util.PropertiesUtil;
import com.gr.vo.GabageDetailVo;
import com.gr.vo.GabageListVo;
import com.gr.vo.GabageSimListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18334 on 2019/6/11.
 */

@Service("iGabageService")
public class GabageServiceImpl implements IGabageService{

    @Autowired
    private GabageMapper gabageMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

    @Override
    public ServerResponse saveOrUpdateGabage(Gabage gabage){
        if(gabage != null){
            if(StringUtils.isNotBlank(gabage.getSubImages())) {
                String[] subImageArray = gabage.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    gabage.setMainImage(subImageArray[0]);
                }
            }

            if(gabage.getId() != null){
                int rowCount = gabageMapper.updateByPrimaryKey(gabage);//primarykey和primarykeySelective区别，前者将空字段换为null，后者只更新不为空的字段
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("更新产品成功");
                }
                return ServerResponse.createBySuccess("更新产品失败");//为什么也是success
            }else{
                int rowCount = gabageMapper.insert(gabage);
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("新增产品成功");
                }
                return ServerResponse.createBySuccess("新增产品失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer gabageId, Integer status){
        if(gabageId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Gabage gabage = new Gabage();
        gabage.setId(gabageId);
        gabage.setStatus(status);
        int rowCount = gabageMapper.updateByPrimaryKeySelective(gabage);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("修改成功");
        }
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse<GabageDetailVo> manageGabageDetail(Integer gabageId){
        if(gabageId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Gabage gabage = gabageMapper.selectByPrimaryKey(gabageId);
        if(gabage == null){
            return ServerResponse.createByErrorMessage("产品已下架或删除");
        }
        GabageDetailVo gabageDetailVo = assembleGabageDetailVo(gabage);
        return ServerResponse.createBySuccess(gabageDetailVo);
    }

    @Override
    public ServerResponse<PageInfo> getGabageList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);//方法的源码
        List<Gabage> gabageList = gabageMapper.selectList();

        List<GabageListVo> gabageListVoList = Lists.newArrayList();
        for(Gabage gabageItem : gabageList){
            GabageListVo gabageListVo = assemblegabageListVo(gabageItem);
            gabageListVoList.add(gabageListVo);
        }
        PageInfo pageResult = new PageInfo(gabageList);//pageInfo作用
        pageResult.setList(gabageListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<PageInfo> getGabageSimList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);//方法的源码
        List<Gabage> gabageList = gabageMapper.selectList();

        List<GabageSimListVo> gabageSimListVoList = Lists.newArrayList();
        for(Gabage gabageItem : gabageList){
            GabageSimListVo gabageSimListVo = assembleGabageSimListVo(gabageItem);
            gabageSimListVoList.add(gabageSimListVo);
        }
        PageInfo pageResult = new PageInfo(gabageList);//pageInfo作用
        pageResult.setList(gabageSimListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<PageInfo> searchGabage(String gabageName, Integer gabageId, int pageNum, int pageSize){//为什么写这个方法
        PageHelper.startPage(pageNum, pageSize);
        if(StringUtils.isNotBlank(gabageName)){
            gabageName = new StringBuilder().append("%").append(gabageName).append("%").toString();
        }
        List<Gabage> gabageList = gabageMapper.selectByNameAndGabageId(gabageName, gabageId);
        List<GabageListVo> gabageListVoList = Lists.newArrayList();
        for(Gabage gabageItem : gabageList){
            GabageListVo gabageListVo = assemblegabageListVo(gabageItem);
            gabageListVoList.add(gabageListVo);
        }
        PageInfo pageResult = new PageInfo(gabageList);//什么意思
        pageResult.setList(gabageListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<PageInfo> getGabageByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy){
        if(StringUtils.isBlank(keyword) && categoryId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList = new ArrayList<Integer>();

        if(categoryId != null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if(category == null && StringUtils.isBlank(keyword)){
                PageHelper.startPage(pageNum, pageSize);
                List<GabageListVo> gabageListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(gabageListVoList);
                return ServerResponse.createBySuccess(pageInfo);//为什么只用到pageInfo
            }
            categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
        }
        if(StringUtils.isNotBlank(keyword)){
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNum, pageSize);
        if(StringUtils.isNotBlank(orderBy)){
            if(Const.GabageListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        List<Gabage> gabageList = gabageMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword,categoryIdList.size()==0?null:categoryIdList);

        List<GabageListVo> gabageListVoList = Lists.newArrayList();
        for(Gabage gabageItem : gabageList){
            GabageListVo gabageListVo = assemblegabageListVo(gabageItem);
            gabageListVoList.add(gabageListVo);
        }
        PageInfo pageResult = new PageInfo(gabageList);//可以像上边一样，把gabageListVoList直接放进去
        pageResult.setList(gabageListVoList);
        return ServerResponse.createBySuccess(pageResult);

    }

    @Override
    public ServerResponse<GabageDetailVo> getGabageDetail(Integer gabageId){
        if(gabageId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());//为什么这块时这样
        }
        Gabage gabage = gabageMapper.selectByPrimaryKey(gabageId);//查询的是多个ID的集合，用primary
        if(gabage == null){
            return ServerResponse.createByErrorMessage("产品未找到");
        }
        if(gabage.getStatus() != Const.GabageStatusEnum.ON_SALE.getCode()){
            return ServerResponse.createByErrorMessage("商品已下架或删除");
        }
        GabageDetailVo gabageDetailVo = assembleGabageDetailVo(gabage);
        return ServerResponse.createBySuccess(gabageDetailVo);
    }

    private GabageListVo assemblegabageListVo(Gabage gabage){
        GabageListVo gabageListVo = new GabageListVo();
        gabageListVo.setId(gabage.getId());
        gabageListVo.setName(gabage.getName());
        gabageListVo.setCategoryId(gabage.getCategoryId());
        gabageListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        gabageListVo.setMainImage(gabage.getMainImage());
        gabageListVo.setPrice(gabage.getPrice());
        gabageListVo.setSubtitle(gabage.getSubtitle());
        gabageListVo.setStatus(gabage.getStatus());
        return gabageListVo;
    }
    private GabageSimListVo assembleGabageSimListVo(Gabage gabage){
        GabageSimListVo simGabageListVo = new GabageSimListVo();
        simGabageListVo.setName(gabage.getName());
        simGabageListVo.setStock(gabage.getStock());
        return simGabageListVo;
    }
    private GabageDetailVo assembleGabageDetailVo(Gabage gabage){//Vo到底是做什么的-----承载中转的对象
        GabageDetailVo gabageDetailVo = new GabageDetailVo();
        gabageDetailVo.setId(gabage.getId());
        gabageDetailVo.setSubtitle(gabage.getSubtitle());
        gabageDetailVo.setPrice(gabage.getPrice());
        gabageDetailVo.setMainImage(gabage.getMainImage());
        gabageDetailVo.setSubImages(gabage.getSubImages());
        gabageDetailVo.setCategoryId(gabage.getCategoryId());
        gabageDetailVo.setDetail(gabage.getDetail());
        gabageDetailVo.setName(gabage.getName());
        gabageDetailVo.setStatus(gabage.getStatus());
        gabageDetailVo.setStock(gabage.getStock());

        gabageDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.grvolunteer.com/"));

        Category category = categoryMapper.selectByPrimaryKey(gabage.getCategoryId());
        if(category == null){
            gabageDetailVo.setParentCategoryId(0);//默认根节点
        }else{
            gabageDetailVo.setParentCategoryId(category.getParentId());
        }

        gabageDetailVo.setCreateTime(DateTimeUtil.dateToStr(gabage.getCreateTime()));
        gabageDetailVo.setUpdateTime(DateTimeUtil.dateToStr(gabage.getUpdateTime()));
        return gabageDetailVo;
    }


}
