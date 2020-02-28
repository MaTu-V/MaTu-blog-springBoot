package com.mt.blog.mapper;

import com.mt.blog.pojo.User;
import com.mt.blog.pojo.UserBack;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface UserMapper {

    /**
     * 根据id查找用户信息
     *
     * @param id 用户id
     * @return
     */
    public User selUserInfoById(String id);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return
     */
    public int updUserInfo(User user);

    /**
     * 添加邮箱
     * @param email 邮箱
     * @param userId 用户id
     * @return
     */
    public int updUserEmail(String email,String userId);
    /**
     * 关注用户 文章发表 + 1
     *
     * @param BlogStatus
     * @param Id
     * @param followId
     * @param userId
     * @return
     */
    public int updUserInfoFollow(Integer BlogStatus, String Id, String followId, String userId);

    /**
     * 获取当前用户的关注人
     *
     * @param userId 用户id
     * @return
     */
    public LinkedList<User> getUserFollow(String userId);

    /**
     * 获取当前用户的粉丝
     *
     * @param userId 用户id
     * @return
     */
    public LinkedList<User> getUserFan(String userId);

    /**
     * 查找是否已经关注
     *
     * @param followId
     * @param userId
     */
    public int isFollowExists(String followId, String userId);

}
