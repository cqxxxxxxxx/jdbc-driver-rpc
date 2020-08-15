package com.cqx.jdbc.rpc.result;

import java.util.HashMap;
import java.util.Map;

/**
 * todo 做缓存
 */
public class Columns {

    private Map<Integer, String> columnIndexNameMap = new HashMap<>();

    public void addColumn(Integer index, String name) {
        columnIndexNameMap.put(index, name);
    }

    public String getName(Integer index) {
        return columnIndexNameMap.get(index);
    }

    public Integer size() {
        return columnIndexNameMap.size();
    }
}
