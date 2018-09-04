/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.utils.storage;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xphi
 * @version: 1.0 2018/9/4
 */
@Service("storage")
public class StoreImMemImpl implements Store {
    private Map<String, Map<String, String>> dataStorage;

    public StoreImMemImpl() {
        dataStorage = new HashMap<>();
    }

    @Override
    public Map<String, String> getStore(String storeId) {
        return dataStorage.get(storeId);
    }

    @Override
    public String get(String storeId, String key) {
        Map<String, String> data = dataStorage.get(storeId);
        if (data == null)
            return null;
        return data.get(key);
    }

    @Override
    public Boolean set(String storeId, String key, String value) {
        Map<String, String> data = dataStorage.get(storeId);
        if (data == null) {
            data = new HashMap<>();
            data.put(key, value);
            dataStorage.put(storeId, data);
            return true;
        }
        data.put(key, value);
        dataStorage.put(storeId, data);
        return true;    }
}
