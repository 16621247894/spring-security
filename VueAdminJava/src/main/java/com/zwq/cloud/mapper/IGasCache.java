package com.zwq.cloud.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface IGasCache {
    public String get(final String key);

    public String set(final String key, final String value);

    public String set(final String key, final String value, final Integer seconds);

    public Long del(final String key);

    public Long expire(final String key, final Integer seconds);

    public Long incr(final String key);

    public Long ttl(final String key);

    public Set<String> smembers(String key);

    public Long sadd(String key, String value, final Integer seconds);

    // zset相关
    Set<String> zsetReverseRangeByScore(String key);

    Long zsetRemove(String key, String val);

    Double zsetScore(String key, String val);

    void zsetAdd(String key, String val, int i);

    void zsetIncrementScore(String key, String val, int i);
}
