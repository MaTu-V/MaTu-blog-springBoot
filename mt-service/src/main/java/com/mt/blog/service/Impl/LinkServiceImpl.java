package com.mt.blog.service.Impl;

import com.mt.blog.mapper.LinkMapper;
import com.mt.blog.pojo.Link;
import com.mt.blog.service.LinkService;
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
import java.util.List;
import java.util.UUID;

@Service
public class LinkServiceImpl implements LinkService {
    private static final Logger LOG = LoggerFactory.getLogger(LinkServiceImpl.class);
    @Autowired
    LinkMapper linkMapper;

    /**
     * 查询全部友链信息
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Cacheable(cacheNames = Constant.RedisCode.LINK_REDIS_CODE_KEY,key = "'link'")
    @Override
    public ServiceResult<LinkedList<Link>> selLink() {
        return ServiceResultHelper.genResultWithDataBaseSuccess(linkMapper.selLink());
    }

    /**
     * 添加友链信息
     *
     * @param link
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = Constant.RedisCode.LINK_REDIS_CODE_KEY,key = "'link'")
    @Override
    public ServiceResult<Boolean> insLink(Link link) {
        // 如果信息不为空则存入
        if (StringUtils.isEmpty(link.getWebName())) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        // 检测当前数据是否存在
        if (linkMapper.selLinkByName(link) > 0) {
            return ServiceResultHelper.genResultWithExistsFailed();
        }
        // 不存在则插入
        // 生成随机id
        link.setId(UUID.randomUUID().toString());
        if (linkMapper.insLink(link) > 0) {
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

    /**
     * 删除友链信息
     *
     * @param ids
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = Constant.RedisCode.LINK_REDIS_CODE_KEY,key = "'link'")
    @Override
    public ServiceResult<Boolean> delLink(String[] ids) {
        // 如果ids集合不为空
        if (StringUtils.isEmpty(ids)) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        if (linkMapper.delLink(ids) > 0) {
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }


    /**
     * 更新友链信息
     *
     * @param link
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = Constant.RedisCode.LINK_REDIS_CODE_KEY,key = "'link'")
    @Override
    public ServiceResult<Boolean> updLink(Link link) {
        // 更新信息
        if (StringUtils.isEmpty(link.getId())) {
            return ServiceResultHelper.genResultWithParamFailed();
        }
        if (linkMapper.updLink(link) > 0) {
            return ServiceResultHelper.genResultWithDataBaseSuccess();
        }
        return ServiceResultHelper.genResultWithDataBaseFailed();
    }

}
