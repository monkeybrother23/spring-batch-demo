package com.albert.springbatch.util;

import java.util.Map;

public interface BatchDao {
    Integer execute(String sql, Object[] params);

    Integer execute(String sql, Map<String, Object> paramMap);
}
