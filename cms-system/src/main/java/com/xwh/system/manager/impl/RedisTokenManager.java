package com.xwh.system.manager.impl;

import com.xwh.core.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

/**
 * 使用Redis存储Token
 *
 *
 */
@Component
@RequiredArgsConstructor
public class RedisTokenManager extends AbstractTokenManager {

    final RedisUtils redisUtils;
    /**
     * Redis中Key的前缀
     */
	private static final String REDIS_KEY_PREFIX = "jwt:AUTHORIZATION:KEY:";

    /**
     * Redis中Token的前缀
     */
	private static final String REDIS_TOKEN_PREFIX = "jwt:AUTHORIZATION:TOKEN:";

    @Override
    protected void delSingleRelationshipByKey(String key) {
        String token = getToken(key);
        if (token != null) {
            delete(formatKey(key), formatToken(token));
        }
    }

    @Override
    public void delRelationshipByToken(String token) {
        if (singleTokenWithUser) {
            String key = getKey(token);
            delete(formatKey(key), formatToken(token));
        } else {
            delete(formatToken(token));
        }
    }

    @Override
    protected void createSingleRelationship(String key, String token) {
        String oldToken = get(formatKey(key));
        if (oldToken != null) {
            delete(formatToken(oldToken));
        }
        set(formatToken(token), key, tokenExpireSeconds);
        set(formatKey(key), token, tokenExpireSeconds);
    }

    @Override
    protected void createMultipleRelationship(String key, String token) {
        set(formatToken(token), key, tokenExpireSeconds);
    }

    @Override
    protected String getKeyByToken(String token) {
        return get(formatToken(token));
    }

    @Override
    protected void flushExpireAfterOperation(String key, String token) {
        if (singleTokenWithUser) {
            expire(formatKey(key), tokenExpireSeconds);
        }
        expire(formatToken(token), tokenExpireSeconds);
    }

    private String get(String key) {
		try {
			Object rt = redisUtils.get(key);
			if (ObjectUtils.isNotEmpty(rt)) {
				return rt.toString();
			} else {
				return null;
			}
		} catch (Exception e) {
        	logger.error(e.getMessage());
        }
        return  null;
    }

    private void set(String key, String value, int expireSeconds) {
        redisUtils.set(key, value, expireSeconds);
    }

    private void expire(String key, int seconds) {
        redisUtils.expire(key, seconds);
    }

    private void delete(String... keys) {
        redisUtils.del(keys);
    }

    private String getToken(String key) {
        return get(formatKey(key));
    }

    private String formatKey(String key) {
        return REDIS_KEY_PREFIX.concat(key);
    }

    private String formatToken(String token) {
        return REDIS_TOKEN_PREFIX.concat(token);
    }
}
