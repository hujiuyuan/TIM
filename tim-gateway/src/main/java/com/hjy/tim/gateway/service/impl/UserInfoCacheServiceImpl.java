package com.hjy.tim.gateway.service.impl;

import com.hjy.tim.gateway.constant.Constant;
import com.hjy.tim.common.pojo.TIMUserInfo;
import com.hjy.tim.gateway.service.UserInfoCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @since JDK 1.8
 */
@Service
public class UserInfoCacheServiceImpl implements UserInfoCacheService {

    /**
     * todo 本地缓存，为了防止内存撑爆，后期可换为 LRU。
     */
    private final static Map<Long, TIMUserInfo> USER_INFO_MAP = new ConcurrentHashMap<>(64);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public TIMUserInfo loadUserInfoByUserId(Long userId) {

        //优先从本地缓存获取
        TIMUserInfo timUserInfo = USER_INFO_MAP.get(userId);
        if (timUserInfo != null) {
            return timUserInfo;
        }

        //load redis
        String sendUserName = redisTemplate.opsForValue().get(Constant.ACCOUNT_PREFIX + userId);
        if (sendUserName != null) {
            timUserInfo = new TIMUserInfo(userId, sendUserName);
            USER_INFO_MAP.put(userId, timUserInfo);
        }

        return timUserInfo;
    }

    @Override
    public boolean saveAndCheckUserLoginStatus(Long userId) throws Exception {

        Long add = redisTemplate.opsForSet().add(Constant.LOGIN_STATUS_PREFIX, userId.toString());
        if (add == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void removeLoginStatus(Long userId) throws Exception {
        redisTemplate.opsForSet().remove(Constant.LOGIN_STATUS_PREFIX, userId.toString());
    }

    @Override
    public Set<TIMUserInfo> onlineUser() {
        Set<TIMUserInfo> set = null;
        Set<String> members = redisTemplate.opsForSet().members(Constant.LOGIN_STATUS_PREFIX);
        for (String member : members) {
            if (set == null) {
                set = new HashSet<>(64);
            }
            TIMUserInfo timUserInfo = loadUserInfoByUserId(Long.valueOf(member));
            set.add(timUserInfo);
        }

        return set;
    }

}
