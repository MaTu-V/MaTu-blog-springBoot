package com.mt.blog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**------------------------string操作----------------------------**/
    /**
     * 删除key
     */
    public RedisUtil delete(String key) {
        redisTemplate.delete(key);
        return this;
    }

    /**
     * 批量删除key
     */
    public RedisUtil delete(Collection<String> keys) {
        redisTemplate.delete(keys);
        return this;
    }

    /**
     * 是否存在key
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置指定key的生存时间（单位：秒）
     */
    public boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 返回指定key的剩余生存时间
     *
     * @param key
     * @return
     */
    public long ttl(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * key名称修改
     */
    public RedisUtil rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
        return this;
    }

    /**
     * 返回当前key-value的类型
     */
    public DataType type(String key) {
        return redisTemplate.type(key);
    }

    /**
     * 设置指定的key-value
     */
    public RedisUtil set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return this;
    }

    /**
     * 设置指定的key-value time (秒)
     */
    public RedisUtil set(String key, String value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 设置指定key的生成周期key-value (value type Object)
     */
    public RedisUtil set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        return this;
    }

    /**
     * 设置指定key的生成周期key-value (value type Object) time  (秒)
     */
    public RedisUtil set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 获取指定key-value内容
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 更新key-value的值
     */
    public Object getAndSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 批量获取
     */
    public List<Object> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * key不存在时设置key值
     */
    public Object setIfAbsent(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 获取字符串的长度
     */
    public Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 追加到末尾
     */
    public Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }
    /**------------------------hash操作----------------------------**/

}
