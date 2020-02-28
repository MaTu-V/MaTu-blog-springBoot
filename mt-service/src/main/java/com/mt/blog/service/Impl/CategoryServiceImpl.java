package com.mt.blog.service.Impl;

import com.mt.blog.mapper.CategoryMapper;
import com.mt.blog.pojo.Category;
import com.mt.blog.service.CategoryService;
import com.mt.blog.utils.Constant;
import com.mt.blog.utils.RedisUtil;
import com.mt.blog.utils.ServiceResult;
import com.mt.blog.utils.ServiceResultHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.LinkedList;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 查询全部分类信息
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Cacheable(cacheNames = Constant.RedisCode.CATEGORY_REDIS_CODE_KEY,key = "'category'")
    @Override
    public ServiceResult<LinkedList<Category>> selCategory() {
        // 数据库读取分类信息
        return ServiceResultHelper.genResultWithDataBaseSuccess(categoryMapper.selCategory());
    }

    /**
     * 添加分类信息
     *
     * @param category
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = Constant.RedisCode.CATEGORY_REDIS_CODE_KEY,key = "'category'")
    @Override
    public ServiceResult<Boolean> insCategory(Category category) {
        // 如果分类信息不为空则存入
        if (StringUtils.isEmpty(category.getCategoryName())) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        // 检测当前数据是否存在
        if (categoryMapper.selCategoryByName(category) > 0) {
            return ServiceResultHelper.genResultWithExistsFailed();
        }
        // 不存在则插入
        if (categoryMapper.insCategory(category) > 0) {
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

    /**
     * 删除分类信息
     *
     * @param ids
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = Constant.RedisCode.CATEGORY_REDIS_CODE_KEY,key = "'category'")
    @Override
    public ServiceResult<Boolean> delCategory(String[] ids) {
        // 如果ids集合不为空
        if (StringUtils.isEmpty(ids)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        if (categoryMapper.delCategory(ids) > 0) {
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

    /**
     * 更新分类信息
     *
     * @param category
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = Constant.RedisCode.CATEGORY_REDIS_CODE_KEY,key = "'category'")
    @Override
    public ServiceResult<Boolean> updCategory(Category category) {
        // 更新分类信息
        if (StringUtils.isEmpty(category.getCategoryName()) && StringUtils.isEmpty(category.getId())) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        if (categoryMapper.updCategory(category) > 0) {
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

}