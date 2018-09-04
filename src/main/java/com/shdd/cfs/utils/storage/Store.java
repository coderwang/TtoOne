/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.utils.storage;

import java.util.Map;

/**
 * @author: xphi
 * @version: 1.0
 */
public interface Store {
    /**
     * 获取一组存储
     *
     * @param storeId 存储标识
     * @return 获取的存储
     */
    Map<String, String> getStore(String storeId);

    /**
     * 获取存储内容
     *
     * @param storeId 存储标识
     * @param key       内容键
     * @return 存储内容
     */
    String get(String storeId, String key);

    /**
     * 设置存储内容
     *
     * @param storeId 存储标识
     * @param key       存储键
     * @param value     存储内容
     * @return 是否成功
     */
    Boolean set(String storeId, String key, String value);
}
