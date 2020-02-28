package com.mt.blog.service.Impl;

import com.mt.blog.mapper.CategoryMapper;
import com.mt.blog.mapper.LabelMapper;
import com.mt.blog.pojo.Category;
import com.mt.blog.pojo.Label;
import com.mt.blog.service.LabelService;
import com.mt.blog.utils.Constant;
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
public class LabelServiceImpl implements LabelService {
    private static final Logger LOG = LoggerFactory.getLogger(LabelServiceImpl.class);
    @Autowired
    LabelMapper labelMapper;
    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 查询全部标签信息
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Cacheable(cacheNames = Constant.RedisCode.LABEL_REDIS_CODE_KEY,key = "'label'")
    @Override
    public ServiceResult<LinkedList<Label>> selLabel() {
        return ServiceResultHelper.genResultWithDataBaseSuccess(labelMapper.selLabel());
    }

    /**
     * 根据分类编号查询标签信息
     *
     * @param categoryId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ServiceResult<LinkedList<Label>> selLabelByCategoryId(String categoryId) {
        // 如果信息不为空则存入
        if (StringUtils.isEmpty(categoryId)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        return ServiceResultHelper.genResultWithSuccess(labelMapper.selLabelByCategoryId(categoryId));
    }

    /**
     * 新增标签信息
     *
     * @param label 标签信息
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = Constant.RedisCode.LABEL_REDIS_CODE_KEY,key = "'label'")
    @Override
    public ServiceResult<Boolean> insLabel(Label label) {
        // 如果信息不为空则存入
        if (StringUtils.isEmpty(label.getLabelName())) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        // 根据当前选中的分类id查找是否存在该信息
        Category category = categoryMapper.selCategoryById(label.getCategory().getId());
        if (StringUtils.isEmpty(category)) {
            //分类信息不存在
            return ServiceResultHelper.genResultWithDataBaseFailed();
        }
        // 插入标签
        if (labelMapper.insLabel(label) > 0) {
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }


    /**
     * 删除选择标签以及标签文章关联表信息
     *
     * @param ids 删除标签集合ids
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = Constant.RedisCode.LABEL_REDIS_CODE_KEY,key = "'label'")
    @Override
    public ServiceResult<Boolean> delLabel(String[] ids) {
        // 如果ids集合不为空
        if (StringUtils.isEmpty(ids)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        if (labelMapper.delLabel(ids) > 0) {
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

    /**
     * 更新标签信息
     *
     * @param label 标签集合
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = Constant.RedisCode.LABEL_REDIS_CODE_KEY,key = "'label'")
    @Override
    public ServiceResult<Boolean> updLabel(Label label) {
        // 更新信息
        if (StringUtils.isEmpty(label.getId())) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        if (labelMapper.updLabel(label) > 0) {
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }
}